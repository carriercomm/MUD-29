package hierarchy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Item
{
	private String description;
	private String name;
	private String key;
	private Map<String, Item> subItems = new HashMap<String, Item>();
	private Map<String, String> interactionToken = new HashMap<String, String>();
	
	public Item(String fileName) throws FileNotFoundException, IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject JsonFile = (JSONObject)(parser.parse(new FileReader("res/items/"+fileName)));
		this.description=	(String)	JsonFile.get("Description");
		this.name=			(String)	JsonFile.get("Name");
		this.key= 			(String)	JsonFile.get("Key");
		JSONArray jsonArray = (JSONArray)JsonFile.get("SubItems");
		Iterator<?> iter = jsonArray.iterator();
		
		while(iter.hasNext())
		{
			Item tempItem = new Item((String)((JSONObject)(iter.next())).get("FileName"));
			subItems.put(tempItem.getKey(), tempItem);
		}
	}
	
	public String getKey()
	{
		return key;
	}
	
	public String getDescription()
	{
		return description;
	}
}
	