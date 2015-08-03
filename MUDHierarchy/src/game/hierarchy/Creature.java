package game.hierarchy;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import game.hierarchy.items.Equipable;
import game.hierarchy.items.Item;
import game.hierarchy.subsystems.NpcClass;
import game.hierarchy.subsystems.NpcStats;

public class Creature
{	
	protected NpcStats stats;
	protected NpcClass npcclass;
	
	protected ArrayList<Item> items;
	
	protected String name;
	protected String className;
	protected String raceName;
	protected int level;
	protected JSONArray baseStats;
	
	public boolean isDead()
	{
		if(stats.getCurrHp() <= 0)
		{
			return true;
		}
		return false;
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
	
	public Item getItem(String dirObject)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public void removeItem(Item item)
	{
		// TODO Auto-generated method stub
		
	}
	
	public int getAc()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Equipable getEquippedWeapon()
	{
		return new Equipable();
	}
}
