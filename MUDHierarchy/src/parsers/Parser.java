/*package parsers;
import game.OutputManager;
import hierarchy.Root;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import parsers.tokens.Action;

public class Parser	// TODO: abstract away the word extraction to a private class? could help modularize this more
{
	OutputManager o;
	
	Tokenizer tokenizer = new Tokenizer(false);	// tokenizer to extract words from text input
	Interpreter interpreter;	// interpreter to execute actions based on text input
	
	ArrayList<String> verb, subject, dirObject, adjective, adverb, conjunction, misc, terminator;	// lists of word types
	ArrayList<JSONObject> verbObjs;	//	list of verb objects containing the verbs and their action mappings
	ArrayList<Action> verbValues;	// 	extracted list of action mappings for the verbs
	
	String verbWord = "";		//	extracted words passed to the interpreter
	String dirObjectWord = "";	//
	Action action;				//	action from the verbValues list corresponding to the verb
	
	@SuppressWarnings("unchecked")
	public Parser(String fileName, Root root, OutputManager o) throws Exception
	{
		this.o = o;
		
		interpreter = new Interpreter(root);
		
		JSONParser parser = new JSONParser();
		JSONObject library = new JSONObject((JSONObject) parser.parse(new FileReader("res/" + fileName)));
		
		verbObjs = (ArrayList<JSONObject>)((JSONArray)library.get("verb")).stream().collect(Collectors.toList());
		verbValues = (ArrayList<Action>) (verbObjs.stream().map(j -> Action.valueOf((String)j.get("Value")))).collect(Collectors.toCollection(ArrayList<Action>::new));
		verb 		= (ArrayList<String>) (verbObjs.stream().map(j -> (String)j.get("Word"))).collect(Collectors.toCollection(ArrayList<String>::new));
		
		subject 	= (ArrayList<String>) ((JSONArray)library.get("subject")).stream().collect(Collectors.toList());
		dirObject 	= (ArrayList<String>) ((JSONArray)library.get("dirObject")).stream().collect(Collectors.toList());
		adjective 	= (ArrayList<String>) ((JSONArray)library.get("adjective")).stream().collect(Collectors.toList());
		adverb 		= (ArrayList<String>) ((JSONArray)library.get("adverb")).stream().collect(Collectors.toList());
		conjunction = (ArrayList<String>) ((JSONArray)library.get("conjunction")).stream().collect(Collectors.toList());
		misc 		= (ArrayList<String>) ((JSONArray)library.get("misc")).stream().collect(Collectors.toList());
		terminator 	= (ArrayList<String>) ((JSONArray)library.get("terminator")).stream().collect(Collectors.toList());
	}
	
	private boolean accept(ArrayList<String> t)
	{
		String token = tokenizer.get();	// get the next available token
		boolean match = t.stream().anyMatch((s) -> s.equals(token));	// check to see if the token is contained in the provided collection
		
		if(t.equals(adjective) && match)	// add the adjective to the dirObject being passed
		{
			dirObjectWord = token + " " + dirObjectWord; 
			o.write("Parser: adjective: " + token + "\n");
		}
		if(t.equals(adverb) && match)	//  add the adverb to the passed verb
		{
			verbWord = verbWord + token;
			o.write("Parser: adverb: " + token + "\n");
		}
		if(t.equals(dirObject) && match)	// add the dirObject being passed
		{
			dirObjectWord += token;
			o.write("Parser: dirObject: " + token + "\n");
		}
		if(t.equals(verb) && match)	// add the verb being passed
		{
			verbWord += token;
			o.write("Parser: verb: " + token + "\n");
		}

		if(match)	// if the token matched, increment the tokenizer and return true
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
			tokenizer.next();	// if the token matched, increment the tokenizer and return true
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
		if(statement() || command())
		{
			o.write("Parser: |" + verbWord + "|\n");
			
			if(verb.indexOf(verbWord) != -1)
				action = verbValues.get(verb.indexOf(verbWord));	// assign the proper action to pass to the interpreter
			else
				action = Action.examine;		// if no verb is given, it is assumed you want to examine the specified object
			
			return true;
		}
			
		o.write("Parser: Invalid command format.\n");
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
						this.previous();
					}
					this.previous();
				}
				this.previous();
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
					this.previous();
				}
				if(accept(terminator))	// e.g. "go north"
				{
					return true;
				}
				this.previous();
			}
			if(accept(adjective))
			{
				if(accept(dirObject))
				{
					if(accept(terminator))
					{
						return true;	// e.g. "go to the north door"
					}
					this.previous();
				}
				this.previous();
			}
			this.previous();
		}
		if(accept(dirObject))	// e.g. "inventory", "ankheg" -- gives info about dirObject
		{
			if(accept(terminator))
			{
				return true;
			}
			this.previous();
		}
		
		tokenizer.resetToken();
		this.resetWords();
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
						this.previous();
					}
					this.previous();
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
							this.previous();
						}
						this.previous();
					}
					if(accept(dirObject))	// e.g. "I go to the door"
					{
						if(accept(terminator))
						{
							return true;
						}
						this.previous();
					}
					if(accept(terminator))	// e.g. "I go to the north"
					{
						return true;
					}
					this.previous();
				}
				this.previous();
			}
		}
		
		tokenizer.resetToken();
		this.resetWords();
		return false;
	}
	
	public void parse(String input)	// parses input tokens
	{
		if(input != null)
		{
			this.resetWords();
			tokenizer.resetAll();
			tokenizer.addTokens(input);
			
			if(block())
			{
				o.write("Parser: Success!!\n");	
				interpreter.interpret(dirObjectWord, action);
			}
		}
	}
	
	private void previous()	// reverts to previous token, and resets parsed key words
	{
		tokenizer.previous();
		this.resetWords();
	}
	
	private void resetWords()	// resets key words and action
	{
		dirObjectWord = "";
		verbWord = "";
		//action = null;	// this gets written over each time it is passed to the interpreter -- no need to write over it every reset
	}

	public void close()	// frees resources used by the parser
	{
		verbWord = null;
		dirObjectWord = null;
		action = null;
		
		verb = null;
		subject = null;
		dirObject = null;
		adjective = null;
		adverb = null;
		conjunction = null;
		misc = null;
		terminator = null;
		verbObjs = null;
		verbValues = null;
		
		tokenizer.close();
		interpreter.close();
		
		tokenizer = null;
		interpreter= null;
		
		o = null;
	}
	
	
}
*/