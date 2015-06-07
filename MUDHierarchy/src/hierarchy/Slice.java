package hierarchy;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class Slice
{
	private Map<String, Location> locations = new HashMap<String, Location>();
	private int key;
	
	public Slice(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject slice = (JSONObject) parser.parse(new FileReader("res/locations/slices/" + fileName));
		
		this.key = (int) (long) slice.get("Key");
		
		Iterator<?> iter = ((JSONArray) slice.get("Locations")).iterator();
		while(iter.hasNext())
		{
			Location l = new Location((String) ((JSONObject) iter.next()).get("FileName"));
			locations.put(l.getKey(), l);
		}
		
	}
	
	public int getKey()
	{
		return key;
	}
}
