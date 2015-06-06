package hierarchy.subsystems;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class NpcStats
{
	private Map<String,Integer >baseStats  = new HashMap<String, Integer>();
	
	private int level;
	private int bab;		//Base attack bonus
	private int fort;		//Fortitude Save
	private int ref;		//Reflex Save
	private int will;		//Will Save
	private int mana;		//Base Mana
	private int hp;			//Base HP
	private int currHP;		//Current HP
	private int currMana;	//Current Mana
	private String[] sAbilities;
	private String[] spells;
	
	public NpcStats(){}	// empty constructor
	
	public NpcStats(NpcClass npcclass, JSONArray BaseStats, int Level)
	{
		level=Level;
		//Base Stats
		baseStats.put("Strength",(Integer)(((JSONObject)(BaseStats.get(0))).get("Strength")));
		baseStats.put("Dexterity",(Integer)(((JSONObject)(BaseStats.get(0))).get("Dexterity")));
		baseStats.put("Constitution",(Integer)(((JSONObject)(BaseStats.get(0))).get("Constitution")));
		baseStats.put("Wisdom",(Integer)(((JSONObject)(BaseStats.get(0))).get("Wisdom")));
		baseStats.put("Intellegence",(Integer)(((JSONObject)(BaseStats.get(0))).get("Intellegence")));
		baseStats.put("Charisma",(Integer)(((JSONObject)(BaseStats.get(0))).get("Charisma")));
		//Base Stats Bonus
		baseStats.put("StrengthMod", (baseStats.get("Strength")/2)-5);
		baseStats.put("DexterityMod", (baseStats.get("Dexterity")/2)-5);
		baseStats.put("ConstitutionMod", (baseStats.get("Constitution")/2)-5);
		baseStats.put("WisdomMod", (baseStats.get("Wisdom")/2)-5);
		baseStats.put("IntellegenceMod", (baseStats.get("Intellegence")/2)-5);
		baseStats.put("CharismaMod", (baseStats.get("Charisma")/2)-5);
		//Saves&Base attack Bonus
		bab=npcclass.getBAB(level);
		fort=npcclass.getFortSave(level);
		ref=npcclass.getRefxSave(level);
		will=npcclass.getWillSave(level);
		//HP AND MANA
		mana = npcclass.getBaseMana()+(npcclass.getMPpLevel()*(level-1));
		hp = npcclass.getBaseHp()+(npcclass.getHPpLevel()*(level-1));
		currHP=hp;
		currMana=mana;
		//TODO
		//ADD ABULITIES
		//ADD SPELLS
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
	public int get
	
	
	
	
}
