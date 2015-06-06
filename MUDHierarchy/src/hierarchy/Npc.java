package hierarchy;

import hierarchy.subsystems.AI;
import hierarchy.subsystems.NpcClass;
import hierarchy.subsystems.NpcStats;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class Npc
{
	private String[] tokens = {"",""};	//TODO: fill in this
	private AI ai;
	private NpcStats stats;
	
	private String name;
	private String description;
	private String className;
	private String raceName;
	private int level;
	private int[] baseStats;
	
	public Npc(String fileName) throws IOException
	{
		JSONParser parser = new JSONParser();
		JSONObject NpcOutline = new JSONObject((JSONObject) parser.parse(new FileReader("res/creatures/" + fileName)));
		
		this.name = (String) NpcOutline.get("Name");
		this.className = (String) NpcOutline.get("Class");
		this.raceName = (String) NpcOutline.get("Race");
		this.level = (int) NpcOutline.get("Level");
		this.baseStats = (int[]) NpcOutline.get("BaseStats");
		this.description = (String) NpcOutline.get("Description");
		
		stats = new NpcStats(new NpcClass(className), baseStats, level);
		ai = new AI(NpcOutline.get("AiName"));
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
