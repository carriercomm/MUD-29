package hierarchy;

import game.OutputManager;
import hierarchy.utilities.CharacterCreator;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import parsers.tokens.Action;

public class MapRoot
{
	private Map<Integer, Slice> sliceMap = new HashMap<Integer, Slice>();
	private Pc character;
	
	OutputManager o;
	
	public MapRoot(String fileName, OutputManager o) throws Exception
	{
		JSONParser parser = new JSONParser();
		JSONObject slicemap = (JSONObject) parser.parse(new FileReader("res/" + fileName));
		
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
	
	public void getLocationDescription(int sliceKey, String locationKey)
	{
		o.write(sliceMap.get(sliceKey).getLocation(locationKey).getDescription());
	}
	
	public String getCharacterName()
	{
		return character.getName();
	}
	
	public String interact(Action action, String dirObject)
	{
		return null;
	}

	public void getCharacterLocationDescription()
	{
		getLocationDescription(character.getSliceKey(), character.getLocationKey());	
	}
	
	public Pc getCharacter()
	{
		return character;
	}
	
	public Npc getCreature(String name)
	{
		return findCreature(name);
	}
	
	private Npc findCreature(String name)
	{
		return null;
	}

	public void close()
	{
		
	}
}
