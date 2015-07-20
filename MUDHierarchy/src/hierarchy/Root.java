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
	
	public void getLocationDescription(int sliceKey, String locationKey)
	{
		o.write(sliceMap.get(sliceKey).getLocation(locationKey).getDescription());
	}
	
	public String getCharacterName()
	{
		return character.getName();
	}
	
	public void interact(String dirObject, Action action)
	{
		o.write("It worked\n");	// TODO: need to get rid of this
	}

	public void getCharacterLocationDescription()
	{
		getLocationDescription(character.getSliceKey(), character.getLocationKey());	
	}
	
	public Pc getCharacter()
	{
		return character;
	}
	
	public Npc getCreature(String name)	// search all locations in the player's slice
	{
		// TODO: fill this out
		return null;
	}
	
	public Item getItem(String name)	// search all items in player's slice (including player's items)
	{
		// TODO: fill this out
		return null;
	}

	public void close()
	{
		// TODO Auto-generated method stub
	}

	public Location getCharacterLocation()
	{
		return sliceMap.get(character.getSliceKey()).getLocation(character.getLocationKey());
	}
	
	public Location getLocation(int sliceKey, String locationKey)
	{
		return sliceMap.get(sliceKey).getLocation(locationKey);
	}

	public String getCharacterTarget() 
	{
		return character.getTarget();
	}
	
	public void setCharacterTarget(String target) 
	{
		character.setTarget(target);
	}
	
	public Object getTarget(String target) 
	{
		return null;
		// TODO Auto-generated method stub
	}
	
	public Class<Object> getTargetType(String target) 
	{
		return Object.class;
		// TODO Auto-generated method stub
	}
}
