import java.util.Scanner;

import parsers.*;

public class ParseTester
{

	public static void main(String[] args)
	{
		try
		{
			Scanner reader = new Scanner(System.in);
			Parser parser = new Parser("library.json");
			String input = "";
			
			do
			{
				input = reader.nextLine();
				parser.parse(input);
			}
			while(!input.equals("exit"));
			
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
