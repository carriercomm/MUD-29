package game.hierarchy;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import game.hierarchy.items.Item;
import game.hierarchy.subsystems.ItemBuilder;

public class Location extends RootObject
{
	private ArrayList<Item> items  = new ArrayList<Item>();
	private ArrayList<Npc> npcs  = new ArrayList<Npc>();
	private ArrayList<Portal> portals  = new ArrayList<Portal>();

	private String description;
	private String key;
	private String name;
	
	@SuppressWarnings({ "unchecked"})
	public Location(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject JsonFile = new JSONObject((JSONObject) parser.parse(new FileReader("res/locations/" + fileName)));
		
		this.description 	= (String) JsonFile.get("Description");
		this.key 			= (String) JsonFile.get("Key");
		this.name 			= (String) JsonFile.get("Name");
		
		items 	= (ArrayList<Item>) 	((JSONArray)JsonFile.get("Items"))	.stream().map(i -> ItemBuilder.getItem(	(String)((JSONObject) i).get("FileName")))		.collect(Collectors.toList());
		npcs  	= (ArrayList<Npc>) 		((JSONArray)JsonFile.get("Npcs"))	.stream().map(n -> new Npc(			(String)((JSONObject) n).get("FileName")))			.collect(Collectors.toList());
		portals = (ArrayList<Portal>) 	((JSONArray)JsonFile.get("Portals")).stream().map(p -> new Portal(		(String)((JSONObject) p).get("FileName")))			.collect(Collectors.toList());
	}
	
	public String getDescription()	// Generates a description of the room containing all items, npcs, and portals
	{
		String descr = "";
		descr += this.name + "\n";
		descr += this.description + "\n";
		
		descr += "\nThere are " + npcs.size() + " npcs in this room.\n";
		descr += npcs.stream().map(n -> n.getDescription(1)).filter(s -> s != null).reduce((s,t) -> s +"\t"+  t).orElse("");
		
		descr += "\nThere are " + items.size() + " items in this room.\n";
		descr += items.stream().map(i -> i.getDescription(1)).filter(s -> s != null).reduce((s,t) -> s +"\t"+  t).orElse("");
		
		descr += "\nThere are " + portals.size() + " exit(s) in this room.\n";
		descr += portals.stream().map(p -> p.getDescription(1)).filter(s -> s != null).reduce((s,t) -> s +"\t"+  t).orElse("");
		
		descr += "\n==============================================================================================\n";
		
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
	
	public ArrayList<Item> getItems()
	{
		return items;
	}
	
	public ArrayList<Npc> getNpcs()
	{
		return npcs;
	}
	
	public boolean hasPortal(Portal p)
	{
		return portals.stream().anyMatch(t -> t.equals(p));
	}
	
	public boolean hasItem(Item i)
	{
		return items.stream().anyMatch(t -> t.equals(i));
	}
	
	public boolean hasNpc(Npc n)
	{
		return portals.stream().anyMatch(t -> t.equals(n));
	}
	
	public String interact()
	{
		// TODO: get rid of this
		return null;
	}

	public void addItem(Item item)
	{
		items.add(item);
	}

	public Item getItem(String dirObject)
	{
		// TODO: finish auto generated method
		return null;
	}

	public void removeItem(Item item)
	{
		// TODO Auto-generated method stub
		
	}

	public Portal getPortal(String target)
	{
		for(Portal p : portals)
		{
			if(p.getName().toLowerCase().contains(target.toLowerCase()))
			{
				return p;
			}
		}
		return null;
	}

	public RootObject getTarget(String target)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Npc getNpc(String target)
	{
		for(Npc n : this.npcs)
		{
			if(n.getName().toLowerCase().contains(target.toLowerCase()))
				return n;
		}
		return null;
	}

	public ArrayList<Npc> getHostileNpcs()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
