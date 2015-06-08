package networking.testio;

import networking.testio.ClientConnection;

import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.*;

public class ServerIO
{
	private List<ClientConnection> connections = Collections.synchronizedList(new ArrayList<ClientConnection>());
	private List<byte[]> messageQueue = Collections.synchronizedList(new ArrayList<byte[]>());
	private ConnectionHandler connectionhandler = new ConnectionHandler();
	private MessageHandler messagehandler = new MessageHandler();
	
	public ServerIO()
	{
		new Thread(connectionhandler).start();	// Initializes the connection handler and the message handler
		new Thread(messagehandler).start();
	}
	
	public byte[] getNextMessage()	// Used by the Server to get the next message in the Message Queue
	{
		byte[] msg = null;
		synchronized(messageQueue)
		{
			if(!messageQueue.isEmpty())
			{
				msg = messageQueue.get(0);
				messageQueue.remove(0);
				return msg;
			}
		}
		return msg;
	}
	
	public int getMessageID(byte[] msg)	// Retrieves a message's ID. Used by the read next method to verify a message
	{
		ByteBuffer bbuf = ByteBuffer.wrap(msg);
		return bbuf.getShort();
	}
	
	public void writeToAll(byte[] msg)	// Writes the specified bytes to all the Client Connections
	{
		synchronized(connections)
		{
			for(ClientConnection con : connections)
			{
				con.write(msg);
			}
		}
	}
	
	public void writeByID(byte[] msg, int id)	// Writes a byte array to a specified Client Connection
	{
		synchronized(connections)
		{
			for(ClientConnection con : connections)
			{
				if(con.getConnectionID() == id)
				{
					con.write(msg);
				}
			}
		}
	}
	
	public boolean playerLimit()	// Used by the Connection Handler to check if the server is full
	{
		if (connections.size() >= 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void kickLaggers()	// Used by the Server to boot any lagging Clients
	{
		synchronized(connections)
		{
			Iterator<ClientConnection> itr = connections.iterator();
			while(itr.hasNext())
			{
				ClientConnection con = itr.next();
				if(con.getDisconnectTime() >= 5)
				{
					con.close();
					itr.remove();
				}
			}
		}
	}
	
	public void closeByID(int id)	// Used by the Server to close a specified connection
	{
		synchronized(connections)
		{
			Iterator<ClientConnection> itr = connections.iterator();
			while(itr.hasNext())
			{
				ClientConnection con = itr.next();
				if(con.getConnectionID() == id)
				{
					con.close();
					connections.remove(con);
				}
			}
		}
	}
	
	public void closeAll()	// Used by the Server to close all client connections
	{
		synchronized(connections)
		{
			for(ClientConnection con : connections)
			{
				con.close();
			}
		}
		connections.clear();
	}
	
	private void addConnection(ClientConnection newconnection)	// Adds a client to the Client Connection list
	{															// Used by the Connection Handler
		synchronized(connections)
		{
			connections.add(newconnection);
		}
	}
	
	private void addMessage(byte[] message)	// Used by Message Handler to add messages to the Queue
	{
		synchronized(messageQueue)
		{
			messageQueue.add(message);
		}
	}
	
	private byte[] readNext()	// Used by Message Handler to read messages from Client Connections
	{
		synchronized(connections)
		{
			for(ClientConnection con : connections)
			{
				byte[] msg = con.read();
				if(msg != null && con.getConnectionID() == getMessageID(msg));
				{
					return msg;
				}
			}
		}
		return null;
	}
	
	public void cleanUp()	// Destroys all resources used by ServerIO
	{
		messagehandler.close();
		connectionhandler.close();
		closeAll();
	}
	
	private class ConnectionHandler implements Runnable	// Connection Handler (its a class so that it can be in a separate thread)
	{
		private static final int LISTENING_PORT = 4444;
		private ServerSocket serverSocket = null;
	    private Socket clientSocket = null;
	    private short currentID = 0;
	    private boolean running = true;
		
	    public void run()
	    {
	        try
	        {
	            serverSocket = new ServerSocket(LISTENING_PORT);
	            
	            while (running)
	            {
	            	if(!playerLimit())
	            	{
		            	clientSocket = serverSocket.accept();	// Blocks socket until a connection is made
		                ClientConnection connection = new ClientConnection(getID(), clientSocket);
		                addConnection(connection);	// Adds new client connection to the connection list
	            	}
	            }
	        }
	        catch (IOException e)
	        {
	        	e.printStackTrace();
	        }
	        
	        closeServerSocket();
	    }
	    
	    private short getID()	// Retrieves an ID for the new Connection
	    {
	    	short newID = currentID;	// There is a better way of doing this
	    	currentID++;				// IDS are a unique short, only assigned once while the software runs
	    	return newID;				// this means there is a limited set of ~65000 IDs before overflow
	    }
	    
		public void close()
		{
			running = false;
	    	return;
	    }
	    
	    private void closeServerSocket()
	    {
	    	if(serverSocket != null)
	    	{
		    	try
		    	{
		    		serverSocket.close();
		    	}
		    	catch(IOException e)
		    	{
		    		e.printStackTrace();
		    	}
	    	}
	    }
	    
	}
	
	private class MessageHandler implements Runnable	// Class that gathers messages from client connections
	{
		private byte[] msg = null;
		private boolean running = true;
		
		public void run()
		{
			while(running)
			{
				msg = readNext();
				if(msg != null)
				{
					addMessage(msg);
				}
				
				try	// allows other methods to use the Connections List while it sleeps
				{
					Thread.sleep(0,(int)((Math.random()*9)+1) );	// between 1 and 10 nanoseconds 
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
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