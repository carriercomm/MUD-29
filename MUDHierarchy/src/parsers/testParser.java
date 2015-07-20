package parsers;

import game.OutputManager;
import hierarchy.Root;

public class testParser	// responsible for checking the format of a command
{						// --- does NOT check if the contents makes sense
	private OutputManager o;
	private Interpreter interpreter;
	
	private Tokenizer tokenizer = new Tokenizer(false);
	private String action = "", ability = "", target = "";
	
	public testParser(Root root, OutputManager o)
	{
		this.o = o;
		this.interpreter = new Interpreter(root, o);
	}
	
	public void parse(String input)
	{
		tokenizer.addTokens(input);
		if(translate())
			interpreter.interpret(action, ability, target);
		else
			o.write("Invalid command format.");
	}
	
	private boolean translate()
	{
		switch(tokenizer.getNumTokens())
		{
			case 1:
				action = tokenizer.get();
				return true;
			case 2:
				action = tokenizer.get();
				tokenizer.next();
				target = tokenizer.get();
				return true;
			case 3:
				action = tokenizer.get();
				tokenizer.next();
				ability = tokenizer.get();
				tokenizer.next();
				target = tokenizer.get();
				return true;
			case 4:
				action = tokenizer.get();	// the last two of four words can be concated to make the target name
				tokenizer.next();
				ability = tokenizer.get();
				tokenizer.next();
				target = tokenizer.get();
				tokenizer.next();
				target += tokenizer.get();
				return true;
			default:
				// and other format is invalid
				return false;
		}
	}

	public void close()
	{
		// TODO Auto-generated method stub
		
	}
}
