package networking.applications;

import networking.testio.*;

import java.io.*;


public class BBTServer {

	public static void main(String[] args) throws IOException{
			
		ServerIO socketmanager = new ServerIO();
		
		boolean running = true;
		byte[] msg = null;
		
		while(running)
		{
			msg = socketmanager.getNextMessage();
			if(msg != null)
			{
				socketmanager.writeByID(msg, socketmanager.getMessageID(msg));
			}
			socketmanager.kickLaggers();
		}
		
		socketmanager.cleanUp();
		socketmanager = null;
		
	}
	
}
