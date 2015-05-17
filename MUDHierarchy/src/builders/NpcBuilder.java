package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import parsers.FileLoader;
import hierarchy.Npc;
import hierarchy.subsystems.NpcStats;
import hierarchy.subsystems.NpcClass;

public class NpcBuilder
{

	private NpcClass npcClass = new NpcClass();
	private NpcStats npcStats = new NpcStats();
	private Npc npc;
	private String fileName;
	private FileLoader fl = new FileLoader();
	
	NpcBuilder(String fileName)
	{
		this.fileName = fileName;
	}
	
	private boolean loadNpc()
	{
		try
		{
			
		}
		catch(Exception e)
		{
			
		}
	}
	
}
