package game.hierarchy.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import game.InputManager;
import game.OutputManager;
import game.hierarchy.Npc;
import game.hierarchy.Root;
import game.parsers.tokens.Action;

public class Conversation
{	
	private int statement = 0;	// yay, no locks or sync blocks!
	private JSONArray conversation;
	private OutputManager o;
	
	public boolean talk(Action a, String ability, String target, Root root, InputManager i, OutputManager o)
	{
		Npc npc = root.getCharacterLocation().getNpc(target);
		this.o = o;
		
		if(npc != null && ability == null && npc.getIsInteractable())
		{
			this.conversation = npc.getConversation(root.getCharacter());
			this.next(0);
			
			while(statement >= 0)	// loop until the conversation end it reached
			{
				String input = i.read();
				if(input != null)
				{
					try
					{
						int selection = Integer.parseInt(input);
						if(selection >= 0 && selection <= ((JSONArray)( (JSONObject) conversation.get(this.statement) ).get("Responses")).size() )
						{
							this.statement = this.getResponseValue(this.statement, selection - 1);
							this.next(this.statement);
						}
						else
						{
							o.write("Invalid input. Enter the number next to the response you wish to select.\n");
						}
					}
					catch(Exception e)
					{
						o.write("Invalid input. Enter the number next to the response you wish to select.\n");
						//e.printStackTrace(o.getPrintWriter());
					}
				}
			}
			
			this.statement = 0;	// reset the statement
			return true;
		}
		else if(npc != null && ability == null && !npc.getIsInteractable())
		{
			o.write("You can not interact with" + npc.getName() + ".\n");
		}
		else if(npc == null)
		{
			o.write("Invalid command. " + target + " is not a valid target.\n");
		}
		else if(ability != null)
		{
			o.write("Invalid command. 'Talk' does not require an ability.\n");
		}
		
		return false;
	}
	
	private void next(int statement)	// prints the current conversation statement and responses
	{
		if(statement >= 0 && statement <= (conversation.size() -1))
		{
			int counter = 1;
			o.write( "\n" + ((String) ( (JSONObject) conversation.get(statement) ).get("Statement")) + "\n");
			for(Object obj : ((JSONArray)((JSONObject)(conversation).get(statement)).get("Responses")).toArray() )
			{
				o.write( counter + "> " + (String)((JSONObject) obj).get("Response") + "\n");
				counter++;
			}
		}
	}
	
	private int getResponseValue(int stat, int resp)	// returns the ID value of the response selected from the current statement
	{
		return Integer.parseInt(( (String)((JSONObject)((JSONArray)(((JSONObject)(conversation.get(stat))).get("Responses"))).get(resp)).get("ID") ));
	}
}
