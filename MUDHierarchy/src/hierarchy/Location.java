package hierarchy;

import java.util.ArrayList;

public abstract class Location
{
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Npc> npcs = new ArrayList<Npc>();
	private ArrayList<Portal> portals = new ArrayList<Portal>();

	private String description;
	private int ID;
	
	public Location(String descr, ArrayList<Item> item, ArrayList<Npc> npc, ArrayList<Portal> portal, short id)
	{
		this.setDescription("\n" + descr + "\n");
		this.setItems(item);
		this.setNpcs(npc);
		this.setPortals(portal);
		this.setID(id);
	}
	
	public String getDescription()	// Generates a description of the room containing all items, npcs, and portals
	{
		String descr = "";
		descr += this.description;
		
		descr += "\nThere are " + items.size() + " items in this room.\n";
		
		for(Item i : items)
		{
			descr += (i.getDescription() + "\n");
		}
		
		descr += "\nThere are " + npcs.size() + " NPCs in this room.\n";
		
		for(Npc n : npcs)
		{
			descr += (n.getDescription() + "\n");
		}
		
		descr += "\nThere are " + portals.size() + " exits in this room.\n";
		
		for(Portal p : portals)
		{
			descr += (p.getDescription() + "\n");
		}
		
		return descr;
	}
	
	private void setDescription(String description)
	{
		this.description = description;
	}
	
	public int getID()
	{
		return ID;
	}
	
	private void setID(int id)
	{
		this.ID = id;
	}
	
	private void setItems(ArrayList<Item> items)
	{
		this.items = items;
	}
	
	private void setNpcs(ArrayList<Npc> npcs)
	{
		this.npcs = npcs;
	}
	
	private void setPortals(ArrayList<Portal> portals)
	{
		this.portals = portals;
	}
	
	public boolean hasPortal(Portal portal)
	{
		for(Portal p : portals)
		{
			if(p.equals(portal))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean hasItem(Item item)
	{
		for(Item i : items)
		{
			if(i.equals(item))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean hasNpc(Npc npc)
	{
		for(Npc n : npcs)
		{
			if(n.equals(npc))
			{
				return true;
			}
		}
		return false;
	}
	
	public String Interact(String[] args)
	{
		for(Item i : items)
		{
			if(args[0].equals(i.getType()))
			{
				return i.Interact(args);
			}
		}
		for(Npc n : npcs)
		{
			if(args[0].equals(n.getRace()))
			{
				return n.Interact(args);
			}
		}
		for(Portal p : portals)
		{
			if(args[0].equals(p.getType()))
			{
				return p.Interact(args);
			}
		}
		return "What are you trying to interact with?";
	}
	
}
