package hierarchy;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Item
{
	private String Description;
	private String Name;
	private String Key;
	private Map<String, Item>SubItems = new HashMap<String, Item>();
	private Map<String, String>InteractionToken = new HashMap<String, String>();
	public Item(String fileName) throws FileNotFoundException, IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject JsonFile = (JSONObject)(parser.parse(new FileReader("res/items/"+fileName)));
		this.Description=	(String)	JsonFile.get("Description");
		this.Name=			(String)	JsonFile.get("Name");
		this.Key= 			(String)	JsonFile.get("Key");
		JSONArray jsonArray = (JSONArray)JsonFile.get("SubItems");
		Iterator<?> iter = jsonArray.iterator();
		while(iter.hasNext())
		{
		Item tempItem = new Item((String)((JSONObject)(iter.next())).get("FileName"));
		SubItems.put(tempItem.getKey(), tempItem);
		}
	}
	public String getKey()
	{
		return Key;
	}
}
	