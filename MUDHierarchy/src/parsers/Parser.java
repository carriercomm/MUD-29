package parsers;
import java.util.ArrayList;

public class Parser
{
	
	String[] verb = {"open","close","pick","eat","drink","use","turn","push","pull","open", "go", "pickup", "walk", "head", "attack", "stab", "fight", "swing", "grab", "say", "talk", "look", "throw", "gothrough", "put", "get"};
	String[] subject = {"i","me","my"};
	String[] dirObject = {"dog", "door", "sword", "table", "inventory", "chest", "key", "him", "her", "androgynous elf", "anthropomorphic legum", "arthritic ankheg", "room", "ring", "potato"};
	String[] adjective = {"green", "blue", "red", "yellow", "shiny", "magical", "adjectivy", "north", "south", "east", "west"};
	String[] adverb = {"fiercely", "swiftly", "deftly", "expertly", "quickly", "clumsily", "accidentally", "purposfully", "north", "south", "east", "west", "through"};
	String[] conjunction = {"and", "then"};
	String[] misc = {"the", "a", "to", "at", "on", "onto", "in", "into", ""};
	String[] terminator = {"."};	// Would it be better to store these in external text files then grab them at run time?
									// Probably. TODO: ^That^
	ArrayList<String> tokens;
	String input;
	String token;
	int currentToken = 0;
	
	private void sanatize()	// converts input to trimmed lower case with white spaces converged
	{
		input = input.toLowerCase().trim().replaceAll("\\s+", " ");
	}
	
	private void generateTokens()	// creates an array list of tokens extracted from the user input
	{	
		int size = input.length() - input.replace(" ", "").length() + 1;	// gets the number of words in the input
		tokens = new ArrayList<String>(size);
		
		int previousPos = 0;
		int position = input.indexOf(" ", 0);
		for(int i = 0; i < size; i++)
		{
			tokens.add(input.substring(previousPos, position));	// grabs all of the words with spaces separating them
			previousPos = position + 1;
			
			if(i != (size - 2))
			{
				position = input.indexOf(" ", previousPos);
			}
			else
			{
				position = input.length();
			}
		}
		
		tokens.add(".");	// add the termination token to the end of the list
		
		token = tokens.get(0);	// set initial value of the token
	}
	
	private void next()	// increments currentToken and retrieves the next token
	{
		if((currentToken + 1) < tokens.size())
		{
			currentToken++;
			token = tokens.get(currentToken);
			System.out.println("++token is now: " + token);
		}
	}
	
	private void previous()	// decrements currentToken and retrieves previous token
	{
		if((currentToken - 1) > 0)
		{
			currentToken--;
			token = tokens.get(currentToken);
			System.out.println("--token is now: " + token);
		}
	}
	
	private void reset()	// resets current token and token counter
	{
		currentToken = 0;
		token = tokens.get(currentToken);
	}
	
	private boolean accept(String[] t)	// looks for the token to match a category
	{
		for(int i = 0; i < t.length; i++)
		{
			if(t[i].equals(token))
			{
				System.out.println("valid. " + token + " = " + t[i]);
				next();
				return true;
			}
		}
		return false;
	}
	
	private boolean accept(String t)	// looks for the token to match a specific word
	{
		if(token.equals(t))
		{
			next();
			return true;
		}
		return false;
	}
	
	private boolean expect(String[] t)	// looks for the token to match a category
	{									// acts as a shell for the accept(String[] t) method
		if(accept(t))					// not really needed, but it helps understand the grammars
		{
			return true;
		}
		return false;
	}
	
	private boolean expect(String t)	// looks for the token to match a specific word
	{									// acts as a shell for the accept(String t) method
		if(accept(t))					// not really needed, but it helps understand the grammars
		{
			return true;
		}
		return false;
	}
	
	private boolean block()	// organizes grammar structures
	{
		if(statement())
		{
			return true;
		}
		if(command())
		{
			return true;
		}
		Error("Invalid command format.");
		return false;
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
						previous();
					}
					previous();
				}
				previous();
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
					previous();
				}
				if(accept(terminator))	// e.g. "go north"
				{
					return true;
				}
				previous();
			}
			if(accept(adjective))
			{
				if(accept(dirObject))
				{
					if(accept(terminator))
					{
						return true;	// e.g. "go to the north door"
					}
					previous();
				}
				previous();
			}
			previous();
		}
		if(accept(dirObject))	// e.g. "inventory", "ankheg" -- gives info about dirObject
		{
			if(accept(terminator))
			{
				return true;
			}
			//previous();
		}
		reset();
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
						previous();
					}
					previous();
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
							previous();
						}
						previous();
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
		reset();
		return false;
	}
	
	private void skipMisc()	// Skip all of the misc words in the way
	{
		while(accept(misc))
		{
			expect(misc);
		}
	}
	
	public void parse(String input)	// cleans and converts the input, then checks if the user input is valid
	{
		this.input = input;
		sanatize();
		generateTokens();
		if(block())
		{
			System.out.println("Success!!");	
		}
	}
	
	private void Error(String error)	// method to print errors
	{
		System.out.println(error);
		//System.exit(1);
	}
	
	
}
