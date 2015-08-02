package game.hierarchy.utilities;

import game.Dice;
import game.OutputManager;
import game.hierarchy.Npc;
import game.hierarchy.Pc;
import game.hierarchy.Root;
import game.parsers.tokens.Action;

public class Combat
{
	Pc pc;	// TODO: add changing combat text
	Npc npc;	// TODO: add all of the methods that are called here
	
	public boolean run(Action a, String ability, String target, OutputManager o, Root root)
	{
		this.pc = root.getCharacter();
		this.npc = root.getCharacterLocation().getNpc(target);
		
		if(ability == null && npc != null && !npc.isDead())
		{
			if((pc.getEquippedWeapon().getType().equals("Melee")) && 
			  ((pc.getStats().getBAB() + pc.getStats().getStat("StrengthMod") + Dice.d20())) >= (npc.getAc()))
			{
				npc.getStats().setCurrHp(npc.getStats().getCurrHp() - Dice.roll(pc.getEquippedWeapon().getDamage()));
				o.write("you done damagez\n");
			}
			else if((pc.getEquippedWeapon().getType().equals("Ranged")) && 
			  ((pc.getStats().getBAB() + pc.getStats().getStat("DexterityMod") + Dice.d20())) >= (npc.getAc()))
			{
				npc.getStats().setCurrHp(npc.getStats().getCurrHp() - Dice.roll(pc.getEquippedWeapon().getDamage()));
				o.write("you done damagez\n");
			}
			else
			{
				o.write("u dont done damagez\n");
			}

			if(npc.isDead())
			{
				o.write("he ded\n");
			}
			return true;
		}
		else if(target == null)
		{
			o.write("Invalid command. '" + target + "' is not a valid target\n");
		}
		else if(ability != null)
		{
			o.write("Invalid command. Attack does not require an ability.\n");
		}
		return false;
	}
}
