package hierarchy;

import hierarchy.subsystems.Ability;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import parsers.tokens.Action;

public abstract class Item
{
	protected String name;
	protected String description;	
	protected boolean isHidden;
	protected double weight;
	
	protected ArrayList<Ability> abilities = new ArrayList<Ability>();
	protected JSONObject JsonFile;
	
	@SuppressWarnings("unchecked")
	public Item(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JsonFile = (JSONObject)(parser.parse(new FileReader("res/items/"+fileName)));
		
		this.description = (String)	 JsonFile.get("Description");
		this.name 		 = (String)	 JsonFile.get("Name");
		this.isHidden 	 = (Boolean) JsonFile.get("IsHidden");
		this.weight		 = (Double)  JsonFile.get("Weight");
		
		abilities = (ArrayList<Ability>) ((JSONArray)JsonFile.get("Abilities")).stream().map(a -> new Ability((String) a)).collect(Collectors.toList());
	}
	
	public abstract String getDescription(int level);

	
	public String getName()
	{
		return name;
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
	
}
	