package hierarchy;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SliceMap
{
	private Map<Integer, Slice> slices = new HashMap<Integer, Slice>();
	
	public SliceMap(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject slicemap = (JSONObject) parser.parse(new FileReader("fileName"));
		
		Iterator<?> iter = ((JSONArray) slicemap.get("Slices")).iterator();
		while(iter.hasNext())
		{
			Slice s = new Slice((String) ((JSONObject) iter.next()).get("FileName"));
			slices.put(s.getKey(), s);
		}
		
	}
}
