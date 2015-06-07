package parsers;

import java.util.ArrayList;
import java.io.*;

// Tokenizes text -- Extracts chunks of text separated by spaces or tabs
// also allows comments in text by ignoring everything in a line followed by the # sign
public class Tokenizer
{
	private ArrayList<String> tokens = new ArrayList<String>();
	private FileReader fr;
	private BufferedReader br;
	
	private int currentToken = 0;
	private boolean preserveCase;
	
	public Tokenizer(boolean preserveCase)
	{
		this.preserveCase = preserveCase;	//preserve text case
	}
	
	public void addString(String input)
	{
		generateTokens(sanatize(input));
	}
	
	public void addFile(String fileName) throws Exception	//throws FileNotFound
	{														//or FileIO
		fr = new FileReader(fileName);
		br = new BufferedReader(fr);
		
		String line = br.readLine();
		while(line != null)
		{
			addString(line);
			line = br.readLine();
		}
		
		br.close();
		fr.close();
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
			if(!temp.startsWith("#"))	// sorts out comments -- they fill all space in a line after a # sign
			{
				tokens.add(temp);	// grabs all of the words with spaces separating them
				previousPos = position + 1;
			
				if(i != (size - 2))
					position = input.indexOf(" ", previousPos);	// if this isn't the second to last word
				else
					position = input.length();	// if it is the second to last word
			}
			else
			{
				break;
			}
		}
	}
	
	public String getNext()	// grab one token
	{
		String token = tokens.get(currentToken);
		currentToken++;
		return token;
	}
	
	public String[] getNext(int numTokens)	//next n tokens
	{
		String[] temp = new String[numTokens];
		for(int i = 0; i < numTokens; i++)
		{
			temp[i] = tokens.get(currentToken);
			currentToken++;
		}
		return temp;
	}
	
	public String[] getAll()	//all tokens. not just from here current to end
	{
		String[] tempStrArray = (String[]) tokens.toArray();
		currentToken = tokens.size();
		return tempStrArray;
	}
	
	public boolean hasNext()
	{
		if(currentToken < tokens.size())
			return true;
		return false;
	}
	
	public boolean hasNext(int numTokens)
	{
		if((tokens.size() - currentToken) < numTokens)
			return false;
		return true;
	}
	
	public void previous()	//back track
	{
		if((currentToken - 1) >= 0)
			currentToken--;
	}
	
	public void previous(int numTokens)
	{
		if((currentToken - numTokens) >= 0)
			currentToken = (currentToken - numTokens);
	}
	
	public void resetToken()	//reset current token
	{
		currentToken = 0;
	}
	
	public void reset()
	{
		tokens = new ArrayList<String>();	//reset all
		currentToken = 0;
	}
}
