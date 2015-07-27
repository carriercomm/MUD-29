package game;

import game.hierarchy.Root;
import game.parsers.Parser;

public class Game
{
	OutputManager o;
	InputManager i;

	SaveGame gamesaver = new SaveGame();
	AiRunner runner = new AiRunner();
	
	Root root;
	Parser parser;
	
	String path;
	
	public Game(OutputManager o, InputManager i, String path)
	{
		this.o = o;
		this.i = i;
		this.path = path;
	}
	
	public void run()
	{
		try
		{
			parser = new Parser(root, o);
			
			o.write(root.getCharacterLocation().getDescription());
			while(!i.getExit())
			{
				if(parser.parse(i.read()));
				{
					runner.run(root, o);
				}
			}

		}
		catch(Exception e)
		{
			if(root == null)
				o.write("!!!!!Game wasn't initialized!!!!!\n");
			
			o.write("!!!!!Parser failed!!!!!\n");
			e.printStackTrace(o.getPrintWriter());
			o.getPrintWriter().flush();
		}
	}
	
	public boolean loadGame()
	{
		try
		{
			this.root = new Root(path + "slicemap.json", o);
			return true;
		}
		catch(Exception e)
		{
			o.write("Invalid Saved Game");
			return false;
		}
	}
	
	public boolean newGame()
	{
		try
		{
			this.root = new Root("res/slicemap.json", o);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace(o.getPrintWriter());
			return false;
		}
	}
	
	public boolean saveGame()
	{
		try
		{
			gamesaver.save(root, path);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace(o.getPrintWriter());
			return false;
		}
	}
	
	public void close()
	{	
		this.gamesaver.close();
		this.gamesaver = null;
		
		this.parser.close();
		this.parser = null;
		
		this.root.close();
		this.root = null;
		
		this.i = null;
		this.o = null;
	}
	
}
