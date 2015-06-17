package hierarchy;

import hierarchy.subsystems.Ability;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public abstract class Item
{
	private String name;
	private String description;	
	private boolean isHidden;
	private double weight;
	
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	
	@SuppressWarnings("unchecked")
	public Item(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject JsonFile = (JSONObject)(parser.parse(new FileReader("res/items/"+fileName)));
		
		this.description = (String)	 JsonFile.get("Description");
		this.name 		 = (String)	 JsonFile.get("Name");
		this.isHidden 	 = (Boolean) JsonFile.get("IsHidden");
		this.weight		 = (Double)  JsonFile.get("Weight");
		
		abilities = (ArrayList<Ability>) ((JSONArray)JsonFile.get("Abilities")).stream().map(a -> new Ability((String) a)).collect(Collectors.toList());
	}
	
	public String getDescription()
	{
		return description +"\n"+ abilities.get(0).toString();
	}
	
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
}
	