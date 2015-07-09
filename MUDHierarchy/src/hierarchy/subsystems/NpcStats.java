package hierarchy.subsystems;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class NpcStats
{
	private Map<String,Integer> baseStats  = new HashMap<String, Integer>();
	
	private int level;
	private int bab;		//Base attack bonus
	private int fort;		//Fortitude Save
	private int ref;		//Reflex Save
	private int will;		//Will Save
	private int mana;		//Base Mana
	private int hp;			//Base HP
	private int currHP;		//Current HP
	private int currMana;	//Current Mana
	
	//private String[] sAbilities;
	//private String[] spells;
	
	public NpcStats(NpcClass npcclass, JSONArray BaseStats, int Level)
	{
		Random rand = new Random();
		this.level = Level;
		
		//Base Stats
		this.baseStats.put("Strength",		(int)(long)	(((JSONObject)(BaseStats.get(0))).get("Strength")));
		this.baseStats.put("Dexterity",		(int)(long)	(((JSONObject)(BaseStats.get(1))).get("Dexterity")));
		this.baseStats.put("Constitution",	(int)(long)	(((JSONObject)(BaseStats.get(2))).get("Constitution")));
		this.baseStats.put("Wisdom",		(int)(long)	(((JSONObject)(BaseStats.get(3))).get("Wisdom")));
		this.baseStats.put("Intellegence",	(int)(long)	(((JSONObject)(BaseStats.get(4))).get("Intellegence")));
		this.baseStats.put("Charisma",		(int)(long)	(((JSONObject)(BaseStats.get(5))).get("Charisma")));
		
		//Base Stats Modifiers
		this.baseStats.put("StrengthMod",		(baseStats.get("Strength")/2)-5);
		this.baseStats.put("DexterityMod",		(baseStats.get("Dexterity")/2)-5);	//set ability modifiers based on formula
		this.baseStats.put("ConstitutionMod",	(baseStats.get("Constitution")/2)-5);
		this.baseStats.put("WisdomMod",			(baseStats.get("Wisdom")/2)-5);
		this.baseStats.put("IntellegenceMod",	(baseStats.get("Intellegence")/2)-5);
		this.baseStats.put("CharismaMod",		(baseStats.get("Charisma")/2)-5);
		
		//Saves & Base Attack Bonus
		this.bab = npcclass.getBAB(level);
		this.fort = npcclass.getFortSave(level);
		this.ref = npcclass.getRefxSave(level);
		this.will = npcclass.getWillSave(level);
		
		//Hp and Mana
		for(int x  = 0; x<level;x++)
		{
			if(x==0)
			{
				mana = npcclass.getBaseMana();
				hp = npcclass.getBaseHp();
			}
			else	// randomly generate mana and hp levels based on class level
			{
				hp +=(rand.nextInt(npcclass.getHPpLevel())+1);
				if(npcclass.getMPpLevel()!=0)
				{
					mana+= (rand.nextInt(npcclass.getMPpLevel())+1);
				}
			}
		}
		
		this.currHP = hp;
		this.currMana = mana;
		//TODO: Add Abilities and Spells
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getBAB()
	{
		return bab;
	}
	
	public int getFortSave()
	{
		return fort;
	}
	
	public int getRefSave()
	{
		return ref;
	}
	
	public int getWillSave()
	{
		return will;
	}
	
	public int getCurrMana()
	{
		return currMana;
	}
	
	public int getMana()
	{
		return mana;
	}
	
	public int getCurrHp()
	{
		return currHP;
	}
	
	public int getHP()
	{
		return hp;
	}
	
	public void setLevel(int newLevel)
	{
		level = newLevel;
	}
	
	public void setBAB(int newBab)
	{
		bab = newBab;
	}
	
	public void setFortSave(int newFort)
	{
		fort = newFort;
	}
	
	public void setRefSave(int newRef)
	{
		ref = newRef;
	}
	
	public void setWillSave(int newWill)
	{
		will = newWill;
	}
	
	public void setCurrMana(int newCurrMana)
	{
		currMana = newCurrMana;
	}
	
	public void setMana(int newMana)
	{
		mana = newMana;
	}
	
	public void setCurrHp(int newCurrHp)
	{
		currHP = newCurrHp;
	}
	
	public void setHP(int newHp)
	{
		hp = newHp;
	}
	
	public void setStat(String key, int value)
	{
		baseStats.put(key, value);
	}
	
	public int getStat(String key)
	{
		return baseStats.get(key);
	}
	
	public String printStats()
	{
		return	"STR: "+baseStats.get("Strength")+" : "+baseStats.get("StrengthMod")+"\nDEX: "+baseStats.get("Dexterity")+" : "+baseStats.get("DexterityMod")+"\nCON: "+baseStats.get("Constitution")+" : "+baseStats.get("ConstitutionMod")+
				"\nWIS: "+baseStats.get("Wisdom")+" : "+baseStats.get("WisdomMod")+"\nINT: "+baseStats.get("Intellegence")+" : "+baseStats.get("IntellegenceMod")+"\nCHA: "+baseStats.get("Charisma")+" : "+baseStats.get("CharismaMod");	
	}
	
	public String print()
	{
		return printStats()+"\nLevel: "+level+"\nBase Attack Bonus: "+bab+"\nFort Save: "+fort+"\nRef Save: "+ref+"\nWill Save: "+will+"\nMax mana: "+mana+"\nCurrent Mana: "+currMana+"\nMax HP: "+hp+"\nCurrent HP: "+currHP;
	}
}
//TODO: giant moth
