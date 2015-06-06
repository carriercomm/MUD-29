package hierarchy;

import hierarchy.subsystems.AI;
import hierarchy.subsystems.NpcStats;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class Npc
{
	private String[] tokens = {"",""};	//TODO: fill in this
	private AI ai;
	private NpcStats stats;
	
	private String className;
	private String raceName;
	
	public Npc(String className, String raceName, int level)
	{
		this.className = className;
		this.raceName = raceName;
		
		try{
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader("res\");
			JSONArray NpcArray = new JSONArray(reader);
			JSONObject obj = new JSONObject();
			obj.
		}
		
	}

	public String getDescription()
	{
		return this.stats.getDescription();
	}
	
	public String getClassName()
	{
		return this.stats.getClassName();
	}
	
	public String getRace()
	{
		return this.stats.getRaceName();
	}
	
	public String Interact(String[] args)
	{
		return null;
	}
}
