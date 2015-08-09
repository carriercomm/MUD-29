package game.hierarchy.items;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import game.hierarchy.RootObject;
import game.hierarchy.subsystems.Ability;
import game.parsers.tokens.Action;

public abstract class Item implements RootObject
{
	protected String name;
	protected String type;
	protected String description;	
	protected boolean isHidden;
	protected double weight;
	protected int cost;
	
	protected ArrayList<Ability> abilities = new ArrayList<Ability>();
	protected JSONObject JsonFile;
	
	@SuppressWarnings("unchecked")
	public Item(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JsonFile = (JSONObject)(parser.parse(new FileReader("res/items/"+fileName)));
		
		this.type		 = (String)	 JsonFile.get("Type");
		this.description = (String)	 JsonFile.get("Description");
		this.name 		 = (String)	 JsonFile.get("Name");
		this.isHidden 	 = (Boolean) JsonFile.get("IsHidden");
		this.weight		 = (double)  JsonFile.get("Weight");
		this.cost		 = (int)(long)	 JsonFile.get("Cost");
		
		abilities = (ArrayList<Ability>) ((JSONArray)JsonFile.get("Abilities")).stream().map(a -> new Ability((String) a)).collect(Collectors.toList());
	}
	
	public Item()
	{
		
	}
	
	public abstract String getDescription(int level);

	
	public String getName()
	{
		return name;
	}
	
	public String getType()
	{
		return type;
	}
	
	public Boolean getIsHidden()
	{
		return isHidden;
	}
	
	public Double getWeight()
	{
		return weight;
	}
	
	public ArrayList<Ability> getAbilities()
	{
		return abilities;
	}
	
	public abstract String interact();

	public void interact(Action action)
	{
		// TODO Auto-generated method stub
		
	}

	public int getCost()
	{
		return cost;
	}
	
}
	