package hierarchy;

import hierarchy.subsystems.AI;
import hierarchy.subsystems.NpcStats;

public class Npc
{
	private String[] tokens = {"",""};	//TODO: fill in this
	private AI ai;
	private NpcStats stats;
	
	private String className;
	private String raceName;
	
	public Npc(String className, String raceName)
	{
		this.className = className;
		this.raceName = raceName;
	}

	public String getDescription()
	{
		return this.stats.getDescription();
	}
	
	public String getClassName()
	{
		return this.stats.getClassName();
	}
	
	public String getRace()
	{
		return this.stats.getRaceName();
	}
	
	public String Interact(String[] args)
	{
		return null;
	}
}
