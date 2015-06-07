package hierarchy;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Location
{
	private Map<String,Object> items  = new HashMap<String, Object>();
	private Map<String,Object> npcs  = new HashMap<String, Object>();
	private Map<String,Object> portals  = new HashMap<String, Object>();

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
		
		Iterator<?> iter = itemArray.iterator();
		while(iter.hasNext())
		{
			Item i = new Item((String) ((JSONObject) iter.next()).get("FileName"));
			items.put(i.getKey(), i);
		}
		iter = npcArray.iterator();
		while(iter.hasNext())
		{
			Npc n = new Npc((String) ((JSONObject) iter.next()).get("FileName"));
			npcs.put(n.getKey(), n);
		}
		iter = portalArray.iterator();
		while(iter.hasNext())
		{
			Portal p = new Portal((String) ((JSONObject) iter.next()).get("FileName"));
			portals.put(p.getKey(), p);
		}
	}
	
	public String getDescription()	// Generates a description of the room containing all items, npcs, and portals
	{
		String descr = "";
		descr += this.description;
		
		descr += "\nThere are " + items.size() + " items in this room.\n";
		
		Iterator<?> iter = items.entrySet().iterator();
		while(iter.hasNext())
		{
			Map.Entry<?,?> pair = (Map.Entry<?,?>)iter.next();
			descr += ((Item) pair.getValue()).getDescription();
		}
		
		descr += "\nThere are " + npcs.size() + " NPCs in this room.\n";
		iter = npcs.entrySet().iterator();
		while(iter.hasNext())
		{
			Map.Entry<?,?> pair = (Map.Entry<?,?>)iter.next();
			descr += ((Npc) pair.getValue()).getDescription();
		}
		
		descr += "\nThere are " + portals.size() + " exits in this room.\n";
		iter = portals.entrySet().iterator();
		while(iter.hasNext())
		{
			Map.Entry<?,?> pair = (Map.Entry<?,?>)iter.next();
			descr += ((Portal) pair.getValue()).getDescription();
		}
		
		return descr;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public boolean hasPortal(String key)
	{
		if(portals.containsKey(key))
		{
			return true;
		}
		return false;
	}
	
	public boolean hasItem(Item item)
	{
		if(items.containsKey(key))
		{
			return true;
		}
		return false;
	}
	
	public boolean hasNpc(Npc npc)
	{
		if(npcs.containsKey(key))
		{
			return true;
		}
		return false;
	}
	
	//public String Interact(String[] args)
	
}
