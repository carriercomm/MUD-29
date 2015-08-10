package game.hierarchy;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import game.hierarchy.items.Equipable;
import game.hierarchy.items.Container;
import game.hierarchy.items.Item;
import game.hierarchy.subsystems.ItemBuilder;
import game.hierarchy.subsystems.NpcClass;
import game.hierarchy.subsystems.NpcStats;

public class Creature
{	
	protected NpcStats stats;
	protected NpcClass npcclass;
	
	protected ArrayList<Item> items = new ArrayList<Item>();
	
	private int	   sliceKey;
	private String locationKey;
	protected int gold;
	protected String name;
	protected String className;
	protected String raceName;
	protected int level;
	protected JSONArray baseStats;
	
	@SuppressWarnings("unchecked")
	public Creature(String fileName)
	{
		super();
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject cOutline = new JSONObject((JSONObject) parser.parse(new FileReader(fileName)));
			
			items 	= (ArrayList<Item>) 	((JSONArray)cOutline.get("Items"))	.stream().map(i -> ItemBuilder.getItem(	(String)((JSONObject) i).get("FileName"))).collect(Collectors.toList());
			
			baseStats 		= 	(JSONArray) cOutline.get("BaseStats");
			
			name 			= 	(String) cOutline.get("Name");
			className 		= 	(String) cOutline.get("Class");
			raceName 		= 	(String) cOutline.get("Race");
			level			= 	(int)(long) cOutline.get("Level");
			gold			= 	(int)(long) cOutline.get("Gold");
			
			sliceKey 		= 	(int)(long) cOutline.get("SliceKey");
			locationKey 	= 	(String) cOutline.get("LocationKey");
			
			npcclass 		= new NpcClass (className + ".json");
			stats 			= new NpcStats(npcclass, baseStats, level);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isDead()
	{
		if(stats.getCurrHp() <= 0)
		{
			return true;
		}
		return false;
	}
	
	public int getSliceKey()
	{
		return sliceKey;
	}
	
	public String getLocationKey()
	{
		return locationKey;
	}
	
	public void move(int sliceKey, String locationKey)
	{
		this.sliceKey = sliceKey;
		this.locationKey = locationKey; 
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
	
	public NpcStats getStats()
	{
		return stats;
	}
	
	public void addItem(Item item)
	{
		items.add(item);
	}
	
	public boolean addItemToContainer(Item item, String name)
	{
		Container c;
		for(Item i : items)
		{
			if(i.getType().equals("Container") && i.getName().equals(name))
			{
				c = ((Container) i);
				c.addItem(item);
				return true;
			}
		}
		return false;
	}
	
	public Item getItem(int index)
	{
		return items.get(index);
	}
	
	public boolean removeItem(Item item)
	{
		for(Item i : items)
		{
			if(i.equals(item))
			{
				items.remove(i);
				return true;
			}
			else if(i.getType().equals("Container"))
			{
				if(((Container)i).removeItem(item))
					return true;
			}
		}
		return false;
	}
	
	public ArrayList<Item> getInventory()
	{
		return items;
	}
	
	public int getAc()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Equipable getEquippedWeapon()
	{
		return new Equipable("res/items/generics/DeleteThisSoon.json");
	}
	
	public void setGold(int gold)
	{
		this.gold = gold;
	}
	
	public int getGold()
	{
		return gold;
	}
}
