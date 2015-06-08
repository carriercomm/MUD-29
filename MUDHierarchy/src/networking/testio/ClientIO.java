package networking.testio;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;

public class ClientIO
{
	
	private MessageHandler messagehandler = new MessageHandler();
	private List<byte[]> messageQueue = Collections.synchronizedList(new ArrayList<byte[]>());
	
	private DataOutputStream out = null;	// Write to socket
	private DataInputStream in = null;		// Read from socket
	private Socket clientSocket = null;		// Socket
	
	private String host = "127.0.0.1";	// Server IP
	private int port = 4444;			// Server Port
	private short id = -1;				// client ID
	
	public ClientIO()
	{			
		newSocket();
		newOutputStream();
		newInputStream();
		
		new Thread(messagehandler).start();	// Start Message Handler
		getID();
	}
	
	public void write(byte b[])
	{
		try
		{
			out.writeInt(b.length + 2);			// Message begins with message size
			out.writeShort(id);					// Next is Client ID
			out.write(b);						// Finally the message itself
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}	
	
	public byte[] getNext()	// Retrieves the next message in the Queue
	{
		byte[] msg = null;
		synchronized(messageQueue)
		{
			if(!messageQueue.isEmpty())
			{
				msg = messageQueue.get(0);
				messageQueue.remove(0);
			}
		}
		return msg;
	}
	
	private byte[] readNext()	// Used by the Message Handler to retrieve messages from the input stream
	{
		byte[] b = null;
		try
		{
			b = new byte[in.readInt()];
			in.readFully(b);
		}
		catch(IOException e)
		{
			System.out.println("Sockets Closed");
		}
		return b;
	}
	
	private void getID()	// Used in the initialization to retrieve the ID assigned to the client
	{
		byte[] b = null;
		while(b == null)
		{
			b = getNext();
		}
		ByteBuffer bbuf = ByteBuffer.wrap(b);
		id = bbuf.getShort();
		System.out.println("ID: " + id);
	}
	
	private boolean newSocket()	// Initializes the client's socket connection
	{
		try
		{
			clientSocket = new Socket(host,port);
		}
		catch(UnknownHostException e)
		{
			System.err.println("Can't find: " + host);
			return false;
		}
		catch(IOException e)
		{
			System.err.println("Couldn't get I/O for the connection to: " + port);
			return false;
		}
		return true;
	}
	
	private boolean newInputStream()	// Initializes the Client input into the socket
	{
		try
		{
			in = new DataInputStream(clientSocket.getInputStream());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean newOutputStream()	// Initializes output from the socket
	{
		try
		{
			out = new DataOutputStream(clientSocket.getOutputStream());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void cleanUp()	// Destroys all resources used by ClientIO
	{
		messagehandler.close();
		try
		{
			closeInputStream();
			closeOutputStream();
			closeSocket();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Sockets Closed");
		}
		messagehandler = null;
	}
	
	private void closeInputStream() throws IOException	// Passes exceptions to the cleanUp() method
	{
		in.close();
	}
	
	private void closeOutputStream() throws IOException	// Passes exceptions to the cleanUp() method
	{
		out.close();
	}
	
	private void closeSocket() throws IOException	// Passes exceptions to the cleanUp() method
	{
		if(!clientSocket.isClosed())
		{
			clientSocket.close();
		}
	}
	
	private class MessageHandler implements Runnable	// Message Handler sub class; 
	{
		private byte[] msg = null;
		private boolean running = true;
		
		public void run()
		{
			while(running)
			{
				msg = readNext();	// Takes messages from the input stream and adds them to the Message Queue
				if(msg != null)
				{
					synchronized(messageQueue)
					{
						messageQueue.add(msg);
					}
				}
			}
			return;
		}
		
		public void close()
		{
			running = false;
		}
	}
}
