package parsers;
import hierarchy.Root;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import parsers.tokens.Action;

public class Parser
{
	
	ArrayList<String> verb, subject, dirObject, adjective, adverb, conjunction, misc, terminator;
	ArrayList<JSONObject> verbObjs;	//	used to keep track of the actions associated with different verbs
	ArrayList<Action> verbValues;	//
	
	Tokenizer tokenizer = new Tokenizer(false);
	Interpreter parserbridge;
	
	String input;
	
	String verbWord, dirObjectWord, adverbWord, adjectiveWord;
	
	@SuppressWarnings("unchecked")
	public Parser(String fileName, Root slicemap) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject lib = new JSONObject((JSONObject) parser.parse(new FileReader("res/" + fileName)));
		
		verbObjs = (ArrayList<JSONObject>)((JSONArray)lib.get("verb")).stream().collect(Collectors.toList());
		verbValues = (ArrayList<Action>) (verbObjs.stream().map(j -> Action.valueOf((String)j.get("Value")))).collect(Collectors.toCollection(ArrayList<Action>::new));
		//System.out.println(verbValues.toString());
		
		parserbridge = new Interpreter(slicemap, verbValues);
		
		verb 		= (ArrayList<String>) (verbObjs.stream().map(j -> (String)j.get("Word"))).collect(Collectors.toCollection(ArrayList<String>::new));
		subject 	= (ArrayList<String>) ((JSONArray)lib.get("subject")).stream().collect(Collectors.toList());
		dirObject 	= (ArrayList<String>) ((JSONArray)lib.get("dirObject")).stream().collect(Collectors.toList());
		adjective 	= (ArrayList<String>) ((JSONArray)lib.get("adjective")).stream().collect(Collectors.toList());
		adverb 		= (ArrayList<String>) ((JSONArray)lib.get("adverb")).stream().collect(Collectors.toList());
		conjunction = (ArrayList<String>) ((JSONArray)lib.get("conjunction")).stream().collect(Collectors.toList());
		misc 		= (ArrayList<String>) ((JSONArray)lib.get("misc")).stream().collect(Collectors.toList());
		terminator 	= (ArrayList<String>) ((JSONArray)lib.get("terminator")).stream().collect(Collectors.toList());
	}
	
	private boolean accept(ArrayList<String> t)	// looks for the token to match a category
	{
		String token = tokenizer.get();
		boolean match = t.stream().anyMatch((s) -> s.equals(token));
		if(t.equals(adjective) && match)
		{
			System.out.println("adjective: " + token);
		}
		if(t.equals(verb) && match)
		{
			System.out.println("verb: " + token);
		}
		if(t.equals(dirObject) && match)
		{
			System.out.println("dirObject: " + token);
		}
		if(match)
		{
			tokenizer.next();
			return true;
		}
		return false;
	}

	private boolean accept(String t)	// looks for the token to match a specific word
	{
		boolean match = tokenizer.get().equals(t);
		if(match)
		{
			tokenizer.next();
			return true;
		}
		return false;
			
	}
	
	private boolean expect(ArrayList<String> t)	// looks for the token to match a category
	{									// acts as a shell for the accept(String[] t) method
		if(accept(t))					// not really needed, but it helps understand the grammars
			return true;
		return false;
	}
	
	private boolean expect(String t)	// looks for the token to match a specific word
	{									// acts as a shell for the accept(String t) method
		if(accept(t))					// not really needed, but it helps understand the grammars
			return true;
		return false;
	}
	
	private boolean block()	// organizes grammar structures
	{
		if(statement())
			return true;
		if(command())
			return true;
		
		Error("Invalid command format.");
		return false;
	}
	
	private void skipMisc()	// Skip all of the misc words in the way
	{
		while(accept(misc))
		{
			expect(misc);
		}
	}
	
	private boolean command()	// grammar definitions for commands
	{
		expect(adverb);
		if(accept(verb))
		{
			expect("up");	// workaround for 'pick up'
			skipMisc();

			if(accept(dirObject))	// e.g. "go to the door"
			{
				if(accept(terminator))
				{
					return true;
				}
				if(accept(misc))
				{
					if(accept(dirObject))	// e.g. "put ring on table", "put the ring on the table"
					{
						skipMisc();
						if(accept(terminator))
						{
							return true;
						}
						tokenizer.previous();
					}
					tokenizer.previous();
				}
				tokenizer.previous();
			}
			if(accept(adverb))
			{
				skipMisc();
				if(accept(dirObject))	// e.g. "go north to the door"
				{
					if(accept(terminator))
					{
						return true;
					}
					tokenizer.previous();
				}
				if(accept(terminator))	// e.g. "go north"
				{
					return true;
				}
				tokenizer.previous();
			}
			if(accept(adjective))
			{
				if(accept(dirObject))
				{
					if(accept(terminator))
					{
						return true;	// e.g. "go to the north door"
					}
					tokenizer.previous();
				}
				tokenizer.previous();
			}
			tokenizer.previous();
		}
		if(accept(dirObject))	// e.g. "inventory", "ankheg" -- gives info about dirObject
		{
			if(accept(terminator))
			{
				return true;
			}
			//previous();
		}
		tokenizer.resetToken();
		return false;
	}
	
	private boolean statement()	// Grammar definitions for statements
	{
		if(accept(subject))
		{
			if(accept(verb))
			{
				skipMisc();
				if(accept(adjective))
				{
					if(accept(dirObject))	// e.g. "I go to the north door"
					{
						if(accept(terminator))
						{
							return true;
						}
						tokenizer.previous();
					}
					tokenizer.previous();
				}
				if(accept(adverb))
				{
					skipMisc();
					if(accept(adjective))
					{
						if(accept(dirObject))	// e.g. "I go north to the north door"
						{
							if(accept(terminator))
							{
								return true;
							}
							tokenizer.previous();
						}
						tokenizer.previous();
					}
					if(accept(dirObject))	// e.g. "I go to the door"
					{
						if(accept(terminator))
						{
							return true;
						}
					}
					if(accept(terminator))	// e.g. "I go to the north"
					{
						return true;
					}
					//previous();
				}
			}
		}
		tokenizer.resetToken();
		return false;
	}
	
	public void parse(String input)
	{
		if(input != null)
		{
			this.input = input;
			tokenizer.resetAll();
			tokenizer.addTokens(input);
			if(block())
			{
				System.out.println("Success!!");	
			}
		}
	}
	
	private void Error(String error)	// method to print errors
	{
		System.out.println(error);
		//System.exit(1);
	}

	public void close()
	{
		
	}
	
	
}
