package game.hierarchy;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import game.hierarchy.subsystems.NpcClass;
import game.hierarchy.subsystems.NpcStats;

public class Pc extends Creature
{
	
	private String target;
	
	private int	   sliceKey;
	private String locationKey;
	
	public Pc(String fileName)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject PcOutline = new JSONObject((JSONObject) parser.parse(new FileReader(fileName)));
				
			super.baseStats = 	(JSONArray) PcOutline.get("BaseStats");
	
			super.name 			= 	(String) PcOutline.get("Name");
			super.className 		= 	(String) PcOutline.get("Class");
			super.raceName 		= 	(String) PcOutline.get("Race");
			super.level			= 	(int)(long) PcOutline.get("Level");
			super.gold			= 	(int)(long) PcOutline.get("Gold");
			sliceKey 		= 	(int)(long) PcOutline.get("SliceKey");
			locationKey 	= 	(String) PcOutline.get("LocationKey");
			
			this.target 	= "";
			
			super.npcclass 	= new NpcClass (className + ".json");
			super.stats 		= new NpcStats(npcclass, baseStats, level);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int getSliceKey()
	{
		return sliceKey;
	}
	
	public String getLocationKey()
	{
		return locationKey;
	}
	
	public String getTarget()
	{
		return target;
	}
	
	public void setTarget(String o)
	{
		this.target = o;
	}
	
	public void move(int sliceKey, String locationKey)
	{
		this.sliceKey = sliceKey;
		this.locationKey = locationKey; 
	}
}
