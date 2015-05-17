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
	
	public NpcStats(String description, String raceName, String className, String[] flags, int[] baseStats, int level, int bab, int fort, int ref, int will, int mana, String[] weaponProfs, String[] armorProfs, String[] shieldProfs, String[] sAbilities, String[] spells)
	{
		this.description = description;
		this.raceName = raceName;
		this.className = className;
		this.flags = flags;
		this.baseStats = baseStats;
		this.level = level;
		this.bab = bab;
		
		
		this.fort = fort;
		this.ref = ref;
		this.will = will;
		this.mana = mana;
		this.weaponProfs = weaponProfs;
		this.armorProfs = armorProfs;
		this.shieldProfs = shieldProfs;
		this.sAbilities = sAbilities;
		this.spells = spells;
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
