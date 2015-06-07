package hierarchy.subsystems;

import java.util.*;
import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NpcClass
{
	private String NpcClassName;
	private int BaseMana;
	private int MPpLevel;
	private int BaseHP;
	private int HitDie;
	private String[] Spells;
	private String[] Abilities;
	private Map<Integer,Object >savesAndBonus  = new HashMap<Integer, Object>();
	
	public NpcClass(){}	// empty constructor
	
	public NpcClass(String fileName)
	{
		//fileName = "ClassOutline.json"; //For testing
		JSONParser parser = new JSONParser();
		
		try
		{	
			JSONObject JsonFile = (JSONObject)(parser.parse(new FileReader("res/classes/"+fileName)));
			NpcClassName=(String) JsonFile.get("Name");
			BaseMana= (int)(long) JsonFile.get("BaseMana");
			MPpLevel = (int)(long) JsonFile.get("ManaPerLevel");
			BaseHP = (int)(long) JsonFile.get("BaseHp");
			HitDie = (int)(long) JsonFile.get("HitDie");
			
			//Saves and Bonus map
			JSONArray JsonArray=(JSONArray)JsonFile.get("Levels");
			Iterator iter = JsonArray.iterator();
			
			int x = 1;
			while(iter.hasNext())
			{
				savesAndBonus.put(x, iter.next());
				x++;
			}
			//TODO
			//ADD SPELLS
			//ADD ABILITES
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
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
	
	public String[] getSpells()
	{
		return Spells;
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
