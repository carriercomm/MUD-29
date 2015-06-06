package hierarchy;

import hierarchy.subsystems.AI;
import hierarchy.subsystems.NpcClass;
import hierarchy.subsystems.NpcStats;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

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
	private JSONArray baseStats;
	
	public Npc(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject NpcOutline = new JSONObject((JSONObject) parser.parse(new FileReader("res/creatures/" + fileName)));
		
		this.name = (String) NpcOutline.get("Name");
		this.className = (String) NpcOutline.get("Class");
		this.raceName = (String) NpcOutline.get("Race");
		this.level = (int) NpcOutline.get("Level");
		this.baseStats = (JSONArray) NpcOutline.get("BaseStats");
		this.description = (String) NpcOutline.get("Description");
		
		stats = new NpcStats(new NpcClass(className), baseStats, level);
		ai = new AI((String)NpcOutline.get("AiName"));
	}

	public String getDescription()
	{
		return description;
	}
	
	public String getClassName()
	{
		return className;
	}
	
	public String getRace()
	{
		return raceName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public String print()
	{
		return "name: " + name + ", class name" + className + ", race name: " + raceName + ", level: " + level + "\n" + "description: " + description + "\n" + ai.print() + "\n" + stats.print();
	}
	
	public String Interact(String[] args)
	{
		return null;
	}
}
