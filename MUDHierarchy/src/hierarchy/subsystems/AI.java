package hierarchy.subsystems;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AI
{
	private ArrayList<?> hostileTo;
	private ArrayList<?> neutralTo;
	private ArrayList<?> friendlyTo;
	
	private int alertness;
	private int mobility;
	private int aggressiveness;
	
	public AI(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject AiOutline = new JSONObject((JSONObject) parser.parse(new FileReader("res/ais/" + fileName)));
		
		this.alertness = (int)(long) AiOutline.get("Alertness");
		this.mobility = (int)(long) AiOutline.get("Mobility");
		this.aggressiveness = (int)(long) AiOutline.get("Aggressiveness");
		this.hostileTo = (ArrayList<?>) AiOutline.get("HostileTo");
		this.neutralTo = (ArrayList<?>) AiOutline.get("NeutralTo");
		this.friendlyTo = (ArrayList<?>) AiOutline.get("FriendlyTo");
		
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
	
	public ArrayList<?> getHostileTo()
	{
		return hostileTo;
	}
	
	public ArrayList<?> getNeutralTo()
	{
		return neutralTo;
	}
	
	public ArrayList<?> getFriendlyTo()
	{
		return friendlyTo;
	}
	
	public String print()
	{
		return "alertness: " + alertness + ", mobility: " + mobility + ", aggressiveness: " + aggressiveness + "\nhostileTo: " + hostileTo.toString() + "\nneutralTo: " + neutralTo.toString() + "\nfriendlyTo: " + friendlyTo.toString();
	}
}
