package game;

import java.util.Collections;
import java.util.ArrayList;

import game.hierarchy.Npc;
import game.hierarchy.Root;

public class AiRunner
{
	public void run(Root root, OutputManager o)
	{		
		try
		{
			ArrayList<Npc> npcs = root.getCharacterLocation().getHostileNpcs();
			if(npcs != null && npcs.size() > 0)
			{
				Collections.shuffle(npcs);
				
				for(Npc npc : npcs)
				{
					npc.getAi().run(root.getCharacter());
				}
			}
		}
		catch(Exception e)
		{
			o.write("Ai error.");
			System.exit(0);
		}
	}
}
