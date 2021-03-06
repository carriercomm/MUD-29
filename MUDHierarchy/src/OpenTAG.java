import game.Game;
import game.InputManager;
import game.OutputManager;

public class OpenTAG
{
	public static void main(String[] args)
	{
		try
		{
			OutputManager o = new OutputManager();
			InputManager i = new InputManager(o);
			Game g = new Game(o,i,"C:/Users/Colin/Documents/GitHub/MUD/MUDHierarchy/res");
			
			g.newGame();
			g.run();
			
			g.close();
			i.close();
			o.close();
			g = null;
			i = null;
			o= null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
} 
