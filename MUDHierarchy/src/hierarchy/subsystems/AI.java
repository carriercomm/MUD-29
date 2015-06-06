package hierarchy.subsystems;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class AI
{
	private int alertness;
	private int mobility;
	private int aggressiveness;
	private ArrayList hostileTo;
	private ArrayList neutralTo;
	private ArrayList friendlyTo;
	
	public AI(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject AiOutline = new JSONObject((JSONObject) parser.parse(new FileReader("res/ai/" + fileName)));
		
		this.alertness = (int) AiOutline.get("Alertness");
		this.mobility = (int) AiOutline.get("Mobility");
		this.aggressiveness = (int) AiOutline.get("Aggressiveness");
		this.hostileTo = (ArrayList) AiOutline.get("HostileTo");
		this.neutralTo = (ArrayList) AiOutline.get("NeutralTo");
		this.friendlyTo = (ArrayList) AiOutline.get("FriendlyTo");
		
	}
	
	public int getAlertness()
	{
		return alertness;
	}
	
	public int getMobility()
	{
		return mobility;
	}
	
	public int Aggressiveness()
	{
		return aggressiveness;
	}
	
	public ArrayList getHostileTo()
	{
		return hostileTo;
	}
	
	public ArrayList getNeutralTo()
	{
		return neutralTo;
	}
	
	public ArrayList getFriendlyTo()
	{
		return friendlyTo;
	}
	
	public String print()
	{
		return "alertness: " + alertness + ", mobility: " + mobility + ", aggressiveness: " + aggressiveness + "\n" + hostileTo.toString() + "\n" + neutralTo.toString() + "\n" + "friendlyTo" + "\n";
	}
}
