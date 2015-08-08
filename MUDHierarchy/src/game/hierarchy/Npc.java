package game.hierarchy;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import game.hierarchy.items.Container;
import game.hierarchy.items.Item;
import game.hierarchy.subsystems.AI;
import game.hierarchy.subsystems.ItemBuilder;
import game.hierarchy.subsystems.NpcClass;
import game.hierarchy.subsystems.NpcStats;

public class Npc extends Creature implements RootObject
{
	private AI ai;
	private JSONArray conversations;
	
	private String description;

	private boolean isInteractable;
	private boolean isAttackable;

	
	public Npc(String fileName, int level)
	{
		try
		{
			super.level = level;
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
	
	@SuppressWarnings("unchecked")
	private void superNpc(String fileName, boolean levelByFile) throws Exception
	{

		JSONParser parser = new JSONParser();
		JSONObject NpcOutline = (JSONObject) parser.parse(new FileReader("res/creatures/" + fileName));
			
		super.items 	= (ArrayList<Item>) 	((JSONArray)NpcOutline.get("Items"))	.stream().map(i -> ItemBuilder.getItem(	(String)((JSONObject) i).get("FileName"))).collect(Collectors.toList());
		
		super.baseStats = 	(JSONArray) NpcOutline.get("BaseStats");

		super.name 			= 	(String) NpcOutline.get("Name");
		super.className 		= 	(String) NpcOutline.get("Class");
		super.raceName 		= 	(String) NpcOutline.get("Race");
		super.gold			= 	(int)(long) NpcOutline.get("Gold");
		this.description 	=	(String) NpcOutline.get("Description");
		this.isInteractable = 	(boolean) NpcOutline.get("IsInteractable");
		this.isAttackable	= 	(boolean) NpcOutline.get("IsAttackable");
			
		if(isInteractable){
			String tempstring = (String) NpcOutline.get("Conversations");
			JSONParser tempparser = new JSONParser();
			JSONObject tempobj = (JSONObject) tempparser.parse(new FileReader("res/conversations/" + tempstring));
			conversations = (JSONArray) tempobj.get("Conversations");
			System.out.println( "res/conversations/" + ((String) NpcOutline.get("Conversations")) );
		}

		if(levelByFile)
			super.level = (int)(long) NpcOutline.get("Level");
		
		super.npcclass 	= new NpcClass (className + ".json");
		super.stats 		= new NpcStats(npcclass, baseStats, level);
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

	public String getDescription(int level)
	{
		String temp = "";
		for(int i = 0; i < level; i++)
			temp += "\t";
		final String tabs = temp;
		
		return tabs + description + "\n";
	}
	
	public String getDescription()
	{
		return description + "\n";
	}
	
	public boolean getIsInteractable()
	{
		return isInteractable;
	}
	
	public boolean getIsAttackable()
	{
		return isAttackable;
	}
	
	public String print()
	{
		return "name: " + super.name + ", class name" + super.className + ", race name: " + super.raceName + ", level: " + super.level + "\n" + "description: " + description + "\n" + ai.print() + "\n" + super.stats.print() + "\n";
	}

	public AI getAi()
	{
		return ai;
	}

	public JSONArray getConversation(Pc pc)
	{
		return (JSONArray) conversations.get(0);
	}
	

	public boolean hasMerchantInventory()
	{
		return super.items.stream()
							.filter(i -> i.getName().equals("MerchantInventory"))
							.findFirst()
							.isPresent();
	}

	public Container getMerchantInventory()
	{
		return (Container) super.items.stream()
									.filter(i -> i.getName().equals("MerchantInventory"))
									.findFirst()
									.get();
	}
	
	//TODO: do we need this?
	public String interact()	// uses isAttackable and isInteractable to resolve interactions & such?
	{
		return null;
	}
}
