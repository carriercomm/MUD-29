package hierarchy;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Location
{
	private ArrayList<Item> items  = new ArrayList<Item>();
	private ArrayList<Npc> npcs  = new ArrayList<Npc>();
	private ArrayList<Portal> portals  = new ArrayList<Portal>();

	private String description;
	private String key;
	private String name;
	
	public Location(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject location = new JSONObject((JSONObject) parser.parse(new FileReader("res/locations/" + fileName)));
		
		this.description = (String) location.get("Description");
		this.key = (String) location.get("Key");
		this.name = (String) location.get("Name");
		JSONArray itemArray = (JSONArray) location.get("Items");
		JSONArray npcArray = (JSONArray) location.get("Npcs");
		JSONArray portalArray = (JSONArray) location.get("Portals");
		
		for(Object o : itemArray)
			items.add(new Item((String)((JSONObject) o).get("FileName")));
		for(Object o : npcArray)
			npcs.add(new Npc((String)((JSONObject) o).get("FileName")));
		for(Object o : portalArray)
			portals.add(new Portal((String)((JSONObject) o).get("FileName")));
	}
	
	public String getDescription()	// Generates a description of the room containing all items, npcs, and portals
	{
		String descr = "";
		descr += this.name + "\n";
		descr += this.description + "\n";
		
		descr += "\nThere are " + items.size() + " items in this room.\n";
		for(Item i : items)
			descr += i.getDescription();
		
		descr += "\nThere are " + npcs.size() + " npcs in this room.\n";
		for(Npc n : npcs)
			descr += n.getDescription();
		
		descr += "\nThere are " + portals.size() + " exit(s) in this room.\n";
		for(Portal p : portals)
			descr += p.getDescription();
		
		descr += "==============================================================================================";
		
		return descr;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean hasPortal(Portal p)
	{
		for(Portal portal : portals)
		{
			if(p.equals(portal))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean hasItem(Item i)
	{
		for(Item item : items)
		{
			if(i.equals(item))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean hasNpc(Npc n)
	{
		for(Npc npc : npcs)
		{
			if(n.equals(npc))
			{
				return true;
			}
		}
		return false;
	}
	
	//public String Interact(String[] args)
	
}
