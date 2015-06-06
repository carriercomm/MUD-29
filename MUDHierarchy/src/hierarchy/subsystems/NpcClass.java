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
	private int MPPLevel;
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
		try {
			JSONObject JsonFile = (JSONObject)(parser.parse(new FileReader("res/classes/"+fileName)));
			NpcClassName=(String) JsonFile.get("Name");
			BaseMana= (int)(long) JsonFile.get("BaseMana");
			MPPLevel = (int)(long) JsonFile.get("ManaPerLevel");
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
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public String getNpcClassName()
	{
		return NpcClassName;
	}
	
	public int[] getBABTable()
	{
		return BABTable;
	}
	
	public int[] getFortTable()
	{
		return FortTable;
	}
	
	public int[] getRefTable()
	{
		return RefTable;
	}
	
	public int[] getWillTable()
	{
		return WillTable;
	}
	
	public int getBaseMana()
	{
		return BaseMana;
	}
	
	public int getMPpLevel()
	{
		return MPPLevel;
	}
	
	public int getHDpLevel()
	{
		return HDPLevel;
	}
	
	public String[] getWeaponProf()
	{
		return WeaponProf;
	}
	
	public String[] getArmorProf()
	{
		return ArmorProf;
	}
	
	public String[] getShieldProf()
	{
		return ShieldProf;
	}
	
	public String[] getSpecialAbilities()
	{
		return SpecialAbilities;
	}
	
	public String[] getSpells()
	{
		return Spells;
	}
	
}
