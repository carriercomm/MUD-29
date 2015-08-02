package game;

import javax.swing.SwingUtilities;

public class Gui
{	
	public Gui()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
	        public void run()
	        {
	            new Console("Test GUI");
	        }
	    });
	}
}
