package parsers;
import java.util.Scanner;

public class ParseTester
{

	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		Parser parser = new Parser();
		String input = "";
		
		do
		{
			input = reader.nextLine();
			parser.parse(input);
		}
		while(!input.equals("exit"));
		
		reader.close();
	}

}
