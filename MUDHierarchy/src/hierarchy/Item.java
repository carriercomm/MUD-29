package hierarchy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Item
{
	private String description;
	private String name;
	private ArrayList<Item> subItems = new ArrayList<Item>();
	//private Map<String, String> interactionToken = new HashMap<String, String>();
	
	public Item(String fileName) throws FileNotFoundException, IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject JsonFile = (JSONObject)(parser.parse(new FileReader("res/items/"+fileName)));
		
		this.description =	(String)	JsonFile.get("Description");
		this.name =			(String)	JsonFile.get("Name");
		
		JSONArray jsonArray = (JSONArray)JsonFile.get("SubItems");
		Iterator<?> iter = jsonArray.iterator();
		
		while(iter.hasNext())
		{
			Item tempItem = new Item((String)((JSONObject)(iter.next())).get("FileName"));
			subItems.add(tempItem);
		}
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getName()
	{
		return name;
	}
}
	