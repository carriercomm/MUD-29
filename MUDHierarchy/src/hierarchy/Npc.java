package hierarchy;

import hierarchy.subsystems.AI;
import hierarchy.subsystems.NpcClass;
import hierarchy.subsystems.NpcStats;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Random;

public class Npc
{
	private String[] tokens = {"",""};	//TODO: fill in this
	private AI ai;
	private NpcStats stats;
	private NpcClass npcclass;
	
	private String name;
	private String description;
	private String className;
	private String raceName;
	private int level;
	private JSONArray baseStats;
	
	public Npc(String fileName, int level) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject NpcOutline = new JSONObject((JSONObject) parser.parse(new FileReader("res/creatures/" + fileName)));
		
		this.level = level;
		this.name = (String) NpcOutline.get("Name");
		this.className = (String) NpcOutline.get("Class");
		this.raceName = (String) NpcOutline.get("Race");
		this.baseStats = (JSONArray) NpcOutline.get("BaseStats");
		this.description = (String) NpcOutline.get("Description");
		
<<<<<<< HEAD
		npcclass = new NpcClass (className);
		stats = new NpcStats(npcclass, baseStats, level);
=======
		stats = new NpcStats(new NpcClass(className + ".json"), baseStats, level);
>>>>>>> origin/master
		ai = new AI((String)NpcOutline.get("Ai"));
	}
	
	public void levelUp()
	{
		Random random = new Random();
		stats.setLevel(stats.getLevel() + 1);
		stats.setHP(stats.getHP() + random.nextInt(npcclass.getHPpLevel()) + 1);
		stats.setCurrHp(stats.getCurrHp());
		stats.setMana(stats.getMana() + random.nextInt(npcclass.getMPpLevel()) + 1);
		stats.setCurrMana(stats.getMana());
		stats.setBAB(npcclass.getBAB(stats.getLevel()));
		stats.setFortSave(npcclass.getFortSave(stats.getLevel()));
		stats.setRefSave(npcclass.getRefxSave(stats.getLevel()));
		stats.setWillSave(npcclass.getWillSave(stats.getLevel()));
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
		return "name: " + name + ", className: " + className + ", raceName: " + raceName + ", level: " + level + "\n" + "description: " + description + "\n" + ai.print() + "\n" + stats.print();
	}
	
	public String Interact(String[] args)
	{
		return null;
	}
}
