package game.hierarchy;

import java.io.FileReader;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import game.hierarchy.items.Container;
import game.hierarchy.subsystems.AI;

public class Npc extends Creature implements RootObject
{
	private AI ai;
	private JSONArray conversations;
	
	private String description;

	private boolean isInteractable;
	private boolean isAttackable;

	
	public Npc(String fileName, int level)
	{
		super(fileName);
		super.level = level;
		superNpc(fileName, false);
	}
	
	public Npc(String fileName)
	{
		super(fileName);
		superNpc(fileName, true);
	}
	
	private void superNpc(String fileName, boolean levelByFile)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject NpcOutline = (JSONObject) parser.parse(new FileReader(fileName));
	
			this.description 	=	(String) NpcOutline.get("Description");
			this.isInteractable = 	(boolean) NpcOutline.get("IsInteractable");
			this.isAttackable	= 	(boolean) NpcOutline.get("IsAttackable");
				
			if(isInteractable){
				String tempstring = (String) NpcOutline.get("Conversations");
				JSONParser tempparser = new JSONParser();
				JSONObject tempobj = (JSONObject) tempparser.parse(new FileReader(tempstring));
				conversations = (JSONArray) tempobj.get("Conversations");
			}
	
			if(levelByFile)
				super.level = (int)(long) NpcOutline.get("Level");
			
			this.ai 		= new AI((String)NpcOutline.get("Ai"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
