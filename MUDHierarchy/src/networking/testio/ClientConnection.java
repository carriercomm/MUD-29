package networking.testio;

import java.io.*;
import java.net.*;
//import java.nio.*;
import java.util.*;

//@SuppressWarnings("unused")
public class ClientConnection
{
	private short id = 0;
	private Socket clientSocket = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	private byte disconnectTime = 0;
	private Timer disconnectTimer = new Timer();
	
	
	public ClientConnection(short newid, Socket newclientSocket)
	{
		this.id = newid;
		this.clientSocket = newclientSocket;
		
		disconnectTimer.scheduleAtFixedRate(new TimerTask(){	// A timer that ticks every second
			@Override public void run(){disconnectTime++;}},1000,1000);	// Used to count the disconnect time
		
		try
		{
			this.in = new DataInputStream(clientSocket.getInputStream());
			this.out = new DataOutputStream(clientSocket.getOutputStream());
			
			byte[] b = {0,0,0,2,(byte)(newid >> 8),(byte) newid};	// outputs the Client ID; [0,0,0,2] is an integer representing the message size is bytes
			out.write(b);											// [(byte)(newid >> 8)] is the first byte of the Client ID
																	// and [(byte) newid] is the second byte of the ID
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void write(byte b[])	// Writes to the Client Socket
	{
		try
		{
			out.writeInt(b.length);	// Message begins with message size
			out.write(b);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public byte[] read()	// Reads a message from the Socket Input
	{
		byte[] b = null;
		try
		{
			b = new byte[in.readInt()];	// First sets the Message Buffer size
			in.readFully(b);			// Then reads the Message fully into the buffer
			disconnectTime = 0;
		}
		catch(IOException e)
		{
			System.out.println("Couldn't get input from " + id + ", " + disconnectTime);
			//e.printStackTrace();
		}
		return b;
	}
	
	public int getConnectionID()	// Returns unique Connection ID
	{
		return id;
	}
	
	public int getDisconnectTime()	// Returns Disconnect Time
	{
		return disconnectTime;
	}
	
	public void close()	// Cleans up all open resources
	{
		try
		{
			closeOutputStream();
			closeInputStream();
			disconnectTimer.cancel();
			clientSocket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void closeInputStream() throws IOException	// Passes exceptions to the close() method
	{
		in.close();
	}
	
	private void closeOutputStream() throws IOException	// Passes exceptions to the close() method
	{
		out.close();
	}
}