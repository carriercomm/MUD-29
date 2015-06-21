package hierarchy;

import hierarchy.subsystems.ItemBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
	
	@SuppressWarnings({ "unchecked"})
	public Location(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject JsonFile = new JSONObject((JSONObject) parser.parse(new FileReader("res/locations/" + fileName)));
		
		this.description 	= (String) JsonFile.get("Description");
		this.key 			= (String) JsonFile.get("Key");
		this.name 			= (String) JsonFile.get("Name");
		
		items 	= (ArrayList<Item>) 	((JSONArray)JsonFile.get("Items"))	.stream().map(i -> new ItemBuilder(	(String)((JSONObject) i).get("FileName")).getItem()).collect(Collectors.toList());
		npcs  	= (ArrayList<Npc>) 		((JSONArray)JsonFile.get("Npcs"))	.stream().map(n -> new Npc(			(String)((JSONObject) n).get("FileName")))			.collect(Collectors.toList());
		portals = (ArrayList<Portal>) 	((JSONArray)JsonFile.get("Portals")).stream().map(p -> new Portal(		(String)((JSONObject) p).get("FileName")))			.collect(Collectors.toList());
	}
	
	public String getDescription()	// Generates a description of the room containing all items, npcs, and portals
	{
		String descr = "";
		descr += this.name + "\n";
		descr += this.description + "\n";
		
		descr += "\nThere are " + npcs.size() + " npcs in this room.\n";
		descr += "\t" + npcs.stream().map(Npc::getDescription).filter(s -> s != null).reduce((s,t) -> s +"\t"+  t).orElse("");
		
		descr += "\nThere are " + items.size() + " items in this room.\n";
		descr += items.stream().map(i -> i.getDescription(1)).filter(s -> s != null).reduce((s,t) -> s +"\t"+  t).orElse("");
		
		descr += "\nThere are " + portals.size() + " exit(s) in this room.\n";
		descr += "\t" + portals.stream().map(Portal::getDescription).filter(s -> s != null).reduce((s,t) -> s +"\t"+  t).orElse("");
		
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
	
	public ArrayList<Item> getItems()
	{
		return items;
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
		return null;
	}
	
}
