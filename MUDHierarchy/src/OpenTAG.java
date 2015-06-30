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
			Game g = new Game(o,i,"res/");
			
			g.newGame();
			g.run();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
} 
