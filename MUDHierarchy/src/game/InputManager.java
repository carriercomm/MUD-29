package game;

import java.util.Scanner;
import java.util.Stack;

public class InputManager implements Runnable
{
	private boolean exitKeyPressed = false;
	
	private Scanner reader = new Scanner(System.in);
	private Stack<String> stack = new Stack<String>();
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
		synchronized(stack)
		{
			stack.push(input);
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
		synchronized(stack)
		{
			if(stack.size() > 0)
				return stack.pop();
			return null;
		}
	}
	
	public boolean getExit()
	{
		return exitKeyPressed;
	}
	
/*	private void setExit(boolean b)
	{
		exitKeyPressed = b;
	}*/
	
}
