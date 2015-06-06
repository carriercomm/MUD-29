package hierarchy.subsystems;

public class NpcStats
{
	private String description;
	private String raceName;
	private String className;
	private String[] flags;
	private int[] baseStats;
	private int level;
	private int bab;	//Base attack bonus
	private int fort;	//Fortitude Save
	private int ref;	//Reflex Save
	private int will;	//Will Save
	private int mana;	//Base Mana
	private String[] weaponProfs;
	private String[] armorProfs;
	private String[] shieldProfs;
	private String[] sAbilities;
	private String[] spells;
	
	public NpcStats(){}	// empty constructor
	
	public NpcStats(Object NpcClass, int[] baseStats, int level)
	{

	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getClassName()
	{
		return this.className;
	}
	
	public String getRaceName()
	{
		return this.raceName;
	}
	
}
