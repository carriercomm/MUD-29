package game.parsers;

import game.OutputManager;
import game.hierarchy.Root;

public class Parser
{						// --- does NOT check if the contents makes sense
	private OutputManager o;
	private Interpreter interpreter;
	
	private Tokenizer tokenizer = new Tokenizer(false);
	private String action = null, ability = null, target = null;
	
	public Parser(Root root, OutputManager o)
	{
		this.o = o;
		this.interpreter = new Interpreter(root, o);
	}
	
	public boolean parse(String input)
	{
		boolean success = false;
		
		if(input != null){
			tokenizer.addTokens(input);
			if(translate()){
				success = interpreter.interpret(action, ability, target);
			}else{
				o.write("Invalid command format.\n");
			}
			this.resetAll();
			tokenizer.resetAll();
		}
		return success;
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

	private void resetAll()
	{
		this.target = null;
		this.ability = null;
		this.action = null;
	}
	
	public void close()
	{
		this.tokenizer.close();
		this.tokenizer = null;
		this.interpreter.close();
		this.interpreter = null;
		this.o = null;
	}
}
