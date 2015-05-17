package hierarchy.subsystems;

public class NpcClass
{
	private String NpcClassName;
	private int[] BABTable;
	private int[] FortTable;
	private int[] RefTable;
	private int[] WillTable;
	private int BaseMana;
	private int MPPLevel;
	private int HDPLevel;
	private String[] WeaponProf;
	private String[] ArmorProf;
	private String[] ShieldProf;
	private String[] SpecialAbilities;
	private String[] Spells;
	
	public NpcClass(){}	// empty constructor
	
	public NpcClass(String NpcClassName, int[] BABTable, int[] FortTable, int[] RefTable, int[] WillTable, int BaseMana, int MPPLevel, int HDPLevel, String[] WeaponProf, String[] ArmorProf, String[] ShieldProf, String[] SpecialAbilities, String[] Spells)
	{
		this.NpcClassName = NpcClassName;
		this.BABTable =BABTable;
		this.FortTable = FortTable;
		this.RefTable = RefTable;
		this.WillTable = WillTable;
		this.BaseMana = BaseMana;
		this.MPPLevel = MPPLevel;
		this.HDPLevel = HDPLevel;
		this.WeaponProf = WeaponProf;
		this.ArmorProf = ArmorProf;
		this.ShieldProf = ShieldProf;
		this.SpecialAbilities = SpecialAbilities;
		this.Spells = Spells;
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
