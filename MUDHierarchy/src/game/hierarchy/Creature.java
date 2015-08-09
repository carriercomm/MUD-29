package game.hierarchy;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import game.hierarchy.items.Equipable;
import game.hierarchy.items.Container;
import game.hierarchy.items.Item;
import game.hierarchy.subsystems.NpcClass;
import game.hierarchy.subsystems.NpcStats;

public class Creature
{	
	protected NpcStats stats;
	protected NpcClass npcclass;
	
	protected ArrayList<Item> items = new ArrayList<Item>();
	
	protected int gold;
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
		return new Equipable();
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
