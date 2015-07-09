package hierarchy;

import hierarchy.subsystems.AI;
import hierarchy.subsystems.NpcClass;
import hierarchy.subsystems.NpcStats;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Npc
{
	private AI ai;
	private NpcStats stats;
	private NpcClass npcclass;
	
	private ArrayList<Item> items;
	
	private String name;
	private String description;
	private String className;
	private String raceName;
	private int level;
	private boolean isInteractable;
	private boolean isAttackable;
	private JSONArray baseStats;
	
	public Npc(String fileName, int level)
	{
		try
		{
			this.level = level;
			superNpc(fileName, false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Npc(String fileName)
	{
		try
		{
			superNpc(fileName, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void superNpc(String fileName, boolean levelByFile) throws Exception
	{

		JSONParser parser = new JSONParser();
		JSONObject NpcOutline = new JSONObject((JSONObject) parser.parse(new FileReader("res/creatures/" + fileName)));
			
		this.baseStats = 	(JSONArray) NpcOutline.get("BaseStats");

		this.name 			= 	(String) NpcOutline.get("Name");
		this.className 		= 	(String) NpcOutline.get("Class");
		this.raceName 		= 	(String) NpcOutline.get("Race");
		this.description 	=	(String) NpcOutline.get("Description");
		this.isInteractable = 	(boolean) NpcOutline.get("IsInteractable");
		this.isAttackable	= 	(boolean) NpcOutline.get("IsAttackable");
			
		if(levelByFile)
			this.level = (int)(long) NpcOutline.get("Level");
		
		this.npcclass 	= new NpcClass (className + ".json");
		this.stats 		= new NpcStats(npcclass, baseStats, level);
		this.ai 		= new AI((String)NpcOutline.get("Ai"));
	}
	
/*	public void levelUp()		// needs to be abstracted to an external utility
	{
		Random random = new Random();
		stats.setLevel(stats.getLevel() + 1);
		this.level = stats.getLevel();
		
		if(npcclass.getMPpLevel() != 0)
		{
			stats.setMana(stats.getMana() + random.nextInt(npcclass.getMPpLevel()) + 1);
			stats.setCurrMana(stats.getMana());
		}
		
		stats.setHP(stats.getHP() + random.nextInt(npcclass.getHPpLevel()) + 1);
		stats.setCurrHp(stats.getHP());
		
		stats.setBAB(npcclass.getBAB(stats.getLevel()));
		stats.setFortSave(npcclass.getFortSave(stats.getLevel()));
		stats.setRefSave(npcclass.getRefxSave(stats.getLevel()));
		stats.setWillSave(npcclass.getWillSave(stats.getLevel()));
	}*/

	public String getDescription()
	{
		return description + "\n";
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
	
	public boolean getIsInteractable()
	{
		return isInteractable;
	}
	
	public boolean getIsAttackable()
	{
		return isAttackable;
	}
	
	public void addItem(Item item)
	{
		items.add(item);
	}
	
	public String print()
	{
		return "name: " + name + ", class name" + className + ", race name: " + raceName + ", level: " + level + "\n" + "description: " + description + "\n" + ai.print() + "\n" + stats.print() + "\n";
	}
	
	//TODO: do we need this?
	public String interact()	// uses isAttackable and isInteractable to resolve interactions & such?
	{
		return null;
	}
}
