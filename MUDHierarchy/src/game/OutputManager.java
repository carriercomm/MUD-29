package game;

import java.io.PrintWriter;

public class OutputManager
{
	private PrintWriter p = new PrintWriter(System.out);
	
	public void write(String s)
	{
		p.print(s);
		p.flush();
	}
	
	public PrintWriter getPrintWriter()
	{
		return p;
	}
	
	public void close()
	{
		p.flush();
		p.close();
		p = null;
	}
}
