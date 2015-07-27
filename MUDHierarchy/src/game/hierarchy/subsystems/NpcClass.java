package game.hierarchy.subsystems;

import java.util.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NpcClass
{
	private Map<Integer,Object >savesAndBonus  = new HashMap<Integer, Object>();
	
	private String NpcClassName;
	private int BaseMana;
	private int MPpLevel;
	private int BaseHP;
	private int HitDie;
	//private String[] Spells;
	//private String[] Abilities;
	
	public NpcClass(String fileName) throws Exception
	{
		JSONParser parser = new JSONParser();
		
		JSONObject JsonFile = (JSONObject)(parser.parse(new FileReader("res/classes/"+fileName)));
		
		this.NpcClassName =	(String) 	JsonFile.get("Name");
		this.BaseMana = 	(int)(long) JsonFile.get("BaseMana");
		this.MPpLevel = 	(int)(long) JsonFile.get("ManaPerLevel");
		this.BaseHP = 		(int)(long) JsonFile.get("BaseHp");
		this.HitDie = 		(int)(long) JsonFile.get("HitDie");
			
		//Saves and Bonus map
		JSONArray JsonArray=(JSONArray)JsonFile.get("Levels");
		Iterator<?> iter = JsonArray.iterator();
			
		int x = 1;
		while(iter.hasNext())
		{
			savesAndBonus.put(x, iter.next());	// Assigns saves and bonuses to the map with the level number as the key
			x++;
		}
		//TODO: Add Spells and Abilities
	}
	
	public String getNpcClassName()
	{
		return NpcClassName;
	}
	
	public int getBaseMana()
	{
		return BaseMana;
	}
	
	public int getMPpLevel()
	{
		return MPpLevel;
	}
	
	public int getBAB(int level)
	{
		return (int)(long) ((JSONObject)(savesAndBonus.get(level))).get("BaseAttackBonus");
	}
	
	public int getFortSave(int level)
	{
		return (int)(long) ((JSONObject)(savesAndBonus.get(level))).get("FortitudeSave");
	}
	
	public int getRefxSave(int level)
	{
		return (int)(long) ((JSONObject)(savesAndBonus.get(level))).get("ReflexSave");
	}
	
	public int getWillSave(int level)
	{
		return (int)(long) ((JSONObject)(savesAndBonus.get(level))).get("WillSave");
	}
	
	public int getBaseHp()
	{
		return BaseHP;
	}
	
	public int getHPpLevel()
	{
		return HitDie;
	}
}
