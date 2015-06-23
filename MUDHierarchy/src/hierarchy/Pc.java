package hierarchy;

import hierarchy.subsystems.NpcClass;
import hierarchy.subsystems.NpcStats;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Pc
{
	private NpcStats stats;
	private NpcClass npcclass;
	
	private String name;
	private String className;
	private String raceName;
	private int level;
	private JSONArray baseStats;
	
	private int	   sliceKey;
	private String locationKey;
	
	public Pc(String fileName)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject PcOutline = new JSONObject((JSONObject) parser.parse(new FileReader(fileName)));
				
			this.baseStats = 	(JSONArray) PcOutline.get("BaseStats");
	
			this.name 			= 	(String) PcOutline.get("Name");
			this.className 		= 	(String) PcOutline.get("Class");
			this.raceName 		= 	(String) PcOutline.get("Race");
			this.level			= 	(int)(long) PcOutline.get("Level");
			this.sliceKey 		= 	(int)(long) PcOutline.get("SliceKey");
			this.locationKey 	= 	(String) PcOutline.get("LocationKey");
			
			this.npcclass 	= new NpcClass (className + ".json");
			this.stats 		= new NpcStats(npcclass, baseStats, level);
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
	
	public String getClassName()
	{
		return className;
	}
	
	public String getRace()
	{
		return raceName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public NpcStats getStat()
	{
		return stats;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void movePc(int sliceKey, String locationKey)
	{
		this.sliceKey = sliceKey;
		this.locationKey = locationKey; 
	}
}
