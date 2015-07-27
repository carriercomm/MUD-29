package game.hierarchy;

import game.OutputManager;
import game.hierarchy.utilities.CharacterCreator;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Root
{
	private Map<Integer, Slice> sliceMap = new HashMap<Integer, Slice>();
	private Pc character;
	
	OutputManager o;
	
	public Root(String nameAndPath, OutputManager o) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject slicemap = (JSONObject) parser.parse(new FileReader(nameAndPath));
		
		this.o = o;
		
		Iterator<?> iter = ((JSONArray) slicemap.get("Slices")).iterator();
		while(iter.hasNext())
		{
			Slice s = new Slice((String) ((JSONObject) iter.next()).get("FileName"));	// how would we do this with streams?
			sliceMap.put(s.getKey(), s);
		}
		
		if(((String)slicemap.get("Character")).equals(""))
		{
			CharacterCreator c = new CharacterCreator(o);
			character = c.create();
		}
		else
		{
			character = new Pc((String)slicemap.get("Character"));
		}
	}
	
	public Pc getCharacter()
	{
		return character;
	}
	
	public Location getLocation(int sliceKey, String locationKey)
	{
		return sliceMap.get(sliceKey).getLocation(locationKey);
	}

	public Location getCharacterLocation()
	{
		return getLocation(character.getSliceKey(), character.getLocationKey());
	}
	
	public void close()
	{
		this.character = null;
		this.sliceMap.clear();
		this.sliceMap = null;
		this.o = null;
	}
}
