package game;

import parsers.Parser;
import hierarchy.Root;

public class Game
{
	OutputManager o;
	InputManager i;

	SaveGame gamesaver = new SaveGame();
	
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
			parser = new Parser("library.json",root);
			
			while(!i.getExit())
			{
				parser.parse(i.read());
			}

		}
		catch(Exception e)
		{
			o.write("!!!!!Parser failed to initalize!!!!!\n");
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
		gamesaver.close();
		gamesaver = null;
		
		parser.close();
		parser = null;
		
		root.close();
		root = null;
		
		path = null;
		i = null;
		o = null;
	}
	
}
