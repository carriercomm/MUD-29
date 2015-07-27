package game;

import java.util.LinkedList;
import java.util.Scanner;

public class InputManager implements Runnable
{
	private boolean exitKeyPressed = false;
	
	private Scanner reader = new Scanner(System.in);
	private LinkedList<String> queue = new LinkedList<String>();
	private LinkedList<String> historyQueue = new LinkedList<String>();
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
		
		synchronized(historyQueue)
		{
			if(historyQueue.size() < 10)
				historyQueue.add(input);
			else
			{
				historyQueue.removeFirst();
				historyQueue.add(input);
			}
		}
		
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			// could be blocking, but it shouldn't be
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
	
	public String getPrevious()
	{
		synchronized(historyQueue)
		{
			return historyQueue.peek();
		}
	}
	
	public boolean getExit()
	{
		return exitKeyPressed;
	}
	
	public void close()
	{
		this.reader.close();
		this.reader = null;
		this.queue.clear();
		this.queue = null;
	}
	
	//private void setExit(boolean b)
	//{
	//	exitKeyPressed = b;				// need to add esc key press detection or something
	//}
	
}
