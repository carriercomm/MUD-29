package hierarchy;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import parsers.tokens.Action;

public class SliceMap
{
	private Map<Integer, Slice> slices = new HashMap<Integer, Slice>();
	//private Pc character;
	
	public SliceMap(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject slicemap = (JSONObject) parser.parse(new FileReader("res/" + fileName));
		
		
		
		Iterator<?> iter = ((JSONArray) slicemap.get("Slices")).iterator();
		while(iter.hasNext())
		{
			Slice s = new Slice((String) ((JSONObject) iter.next()).get("FileName"));	// how would we do this with streams?
			slices.put(s.getKey(), s);
		}
	}
	
	public void getLocationDescription(int sliceKey, String locationKey)
	{
		System.out.println(slices.get(sliceKey).getLocation(locationKey).getDescription());
	}
	
	public String interact(Action action, String dirObject)
	{
		return null;
	}
}
