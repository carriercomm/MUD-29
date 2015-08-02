package game;

import java.util.LinkedList;
import java.util.Scanner;

public class InputManager implements Runnable
{
	private boolean exitKeyPressed = false;
	
	private Scanner reader = new Scanner(System.in);
	private LinkedList<String> queue = new LinkedList<String>();
	private String input;
	
	OutputManager o;
	
	public InputManager(OutputManager o)
	{
		this.o = o;

		Thread thread = new Thread(this);	// without this, the input manager runs in the main thread and stops everything else
		thread.start();
	}
	
	@Override
	public void run()
	{
		do
		{
			readInput();
			writeInput();
		}
		while(!exitKeyPressed);
	}
	
	private void readInput()
	{
		input = reader.nextLine();
	}
	
	private void writeInput()
	{
		synchronized(queue)
		{
			queue.add(input);
		}
		
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			// could be blocking, but it shouldn't be
			o.write("!!!!!Input Thread Sleep Failed!!!!!");
		}
	}
	
	public String read()
	{
		synchronized(queue)
		{
			if(queue.size() > 0)
				return queue.removeFirst();
			return null;
		}
	}
	
	public boolean getExit()
	{
		return exitKeyPressed;
	}
	
	protected boolean setExit(boolean exit)
	{
		return this.exitKeyPressed = exit;
	}
	
	public void close()
	{
		this.reader.close();
		this.reader = null;
		this.queue.clear();
		this.queue = null;
	}
}
