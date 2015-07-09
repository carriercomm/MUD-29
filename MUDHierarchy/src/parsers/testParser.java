package parsers;

import game.OutputManager;
import hierarchy.Root;

public class testParser
{
	@SuppressWarnings("unused")
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
		translate();
		interpreter.interpret(action, ability, target);
	}
	
	private void translate()
	{
		switch(tokenizer.getNumTokens())
		{
		case 1:
			action = "examine";
			target = tokenizer.get();
		break;
		case 2:
			action = tokenizer.get();
			tokenizer.next();
			target = tokenizer.get();
		break;
		case 3:
			action = tokenizer.get();
			tokenizer.next();
			ability = tokenizer.get();
			tokenizer.next();
			target = tokenizer.get();
		break;
		}
		action = tokenizer.get(0);
		target = tokenizer.get(tokenizer.getNumTokens() - 1);
		
		if(tokenizer.getNumTokens() > 2)
			ability = tokenizer.get(1);
	}

	public void close()
	{
		// TODO Auto-generated method stub
		
	}
}
