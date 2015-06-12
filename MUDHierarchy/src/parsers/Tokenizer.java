package parsers;

import java.util.ArrayList;

// Tokenizes text -- Extracts chunks of text separated by spaces or tabs
// also allows comments in text by ignoring everything in a line followed by the # sign
public class Tokenizer
{
	private ArrayList<String> tokens = new ArrayList<String>();
	
	private int currentToken = 0;
	private boolean preserveCase;
	
	public Tokenizer(boolean preserveCase)
	{
		this.preserveCase = preserveCase;	//preserve text case
	}
	
	public void addTokens(String input)
	{
		generateTokens(sanatize(input));
	}
	
	private String sanatize(String input)	// converts input to trimmed lower case with white spaces converged
	{
		if(preserveCase)
			return input.trim().replaceAll("\\s+", " ");
		else
			return input.toLowerCase().trim().replaceAll("\\s+", " ");
	}
	
	private void generateTokens(String input)	// creates an array list of tokens extracted from the user input
	{	
		int size = input.length() - input.replace(" ", "").length() + 1;	// gets the number of words in the input
		
		int previousPos = 0;
		int position = input.indexOf(" ", 0);
		
		for(int i = 0; i < size; i++)
		{
			String temp = input.substring(previousPos, position);

			tokens.add(temp);	// grabs all of the words with spaces separating them
			previousPos = position + 1;
			
			if(i != (size - 2))
				position = input.indexOf(" ", previousPos);	// if this isn't the second to last word
			else
				position = input.length();	// if it is the second to last word
		}
		tokens.add("."); // terminator token
	}
	
	public String get()
	{
		return tokens.get(currentToken);
	}
	
	public void next()	// grab one token
	{
		if (hasNext())
			currentToken++;
	}
	
	public String[] getAll()	//returns tokens list
	{
		return (String[]) tokens.toArray();
	}
	
	public boolean hasNext()
	{
		if((currentToken + 1) < tokens.size())
			return true;
		return false;
	}
	
	public void previous()	//back track
	{
		if((currentToken - 1) >= 0)
			currentToken--;
	}
	
	public void resetToken()	//reset current token
	{
		currentToken = 0;
	}
	
	public void resetAll()
	{
		tokens = new ArrayList<String>();	//reset all
		currentToken = 0;
	}
}
