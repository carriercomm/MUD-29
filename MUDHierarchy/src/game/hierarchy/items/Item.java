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
	public Item(String fileName)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JsonFile = (JSONObject)(parser.parse(new FileReader(fileName)));
			
			this.type		 = (String)	 JsonFile.get("Type");
			this.description = (String)	 JsonFile.get("Description");
			this.name 		 = (String)	 JsonFile.get("Name");
			this.isHidden 	 = (Boolean) JsonFile.get("IsHidden");
			this.weight		 = (double)  JsonFile.get("Weight");
			this.cost		 = (int)(long)	 JsonFile.get("Cost");
			
			abilities = (ArrayList<Ability>) ((JSONArray)JsonFile.get("Abilities")).stream().map(a -> new Ability((String) a)).collect(Collectors.toList());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Item(String filename, String description, String name) throws Exception
	{
		this(filename);
		this.description = description;
		this.name = name;
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
	