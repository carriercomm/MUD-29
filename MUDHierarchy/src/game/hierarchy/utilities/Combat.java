package game.hierarchy.utilities;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import game.Dice;
import game.OutputManager;
import game.hierarchy.Creature;
import game.hierarchy.Root;
import game.parsers.tokens.Action;

public class Combat
{	
	JSONObject PcCritical, PcDeath, PcHit, PcMiss, NpcCritical, NpcDeath, NpcHit, NpcMiss;
	
	public Combat(OutputManager o)
	{
		try
		{
			JSONParser parser = new JSONParser();
			
			this.PcCritical = (JSONObject) parser.parse(new FileReader("res/combat/PcCritical.json"));
			this.PcDeath = (JSONObject) parser.parse(new FileReader("res/combat/PcDeath.json"));
			this.PcHit = (JSONObject) parser.parse(new FileReader("res/combat/PcHit.json"));
			this.PcMiss = (JSONObject) parser.parse(new FileReader("res/combat/PcMiss.json"));
			this.NpcCritical = (JSONObject) parser.parse(new FileReader("res/combat/NpcCritical.json"));
			this.NpcDeath = (JSONObject) parser.parse(new FileReader("res/combat/NpcDeath.json"));
			this.NpcHit = (JSONObject) parser.parse(new FileReader("res/combat/NpcHit.json"));
			this.NpcMiss = (JSONObject) parser.parse(new FileReader("res/combat/NpcMiss.json"));
		}
		catch(Exception e)
		{
			e.printStackTrace(o.getPrintWriter());
		}
	}
	
	public boolean run(Action a, String ability, String target, OutputManager o, Root root)
	{
		Creature pc = root.getCharacter();
		Creature npc = root.getCharacterLocation().getNpc(target);
		
		if(ability == null && npc != null && !npc.isDead())
		{
			this.attack(pc, npc, o, root);
			return true;
		}
		else if(npc == null)
		{
			o.write("Invalid command. '" + target + "' is not a valid target\n");
		}
		else if(ability != null)
		{
			o.write("Invalid command. Attack does not require an ability.\n");
		}

		return false;
	}
	
	public void attack(Creature aggressor, Creature defender, OutputManager o, Root root)
	{
		int roll = Dice.d20();
		boolean aggressorIsPc = aggressor.getClass().getName().equals("game.hierarchy.Pc");
		
		if((aggressor.getEquippedWeapon().getType().equals("Melee")) && 
		((aggressor.getStats().getBAB() + aggressor.getStats().getStat("StrengthMod") + roll)) >= (defender.getAc()))
		{
			defender.getStats().setCurrHp(defender.getStats().getCurrHp() - Dice.roll(aggressor.getEquippedWeapon().getDamage()));
			if(aggressorIsPc)
			{
				if(roll == 20)
				{
					o.write( (String)((JSONArray) PcCritical.get("MeleeDescriptionArray")).get((Dice.roll((((JSONArray) PcCritical.get("MeleeDescriptionArray")).size())) - 1)) );
				}
				o.write( (String)((JSONArray) PcHit.get("MeleeDescriptionArray")).get((Dice.roll((((JSONArray) PcHit.get("MeleeDescriptionArray")).size())) - 1)) );
			}
			else
			{
				if(roll == 20)
				{
					o.write( (String)((JSONArray) NpcCritical.get("MeleeDescriptionArray")).get((Dice.roll((((JSONArray) NpcCritical.get("MeleeDescriptionArray")).size())) - 1)) );
				}
				o.write( (String)((JSONArray) NpcHit.get("MeleeDescriptionArray")).get((Dice.roll((((JSONArray) NpcHit.get("MeleeDescriptionArray")).size())) - 1)) );
			}
		}
		else if((aggressor.getEquippedWeapon().getType().equals("Ranged")) && 
		((aggressor.getStats().getBAB() + aggressor.getStats().getStat("DexterityMod") + roll)) >= (defender.getAc()))
		{
			defender.getStats().setCurrHp(defender.getStats().getCurrHp() - Dice.roll(aggressor.getEquippedWeapon().getDamage()));
			if(aggressorIsPc)
			{
				if(roll == 20)
				{
					o.write( (String)((JSONArray) PcCritical.get("RangedDescriptionArray")).get((Dice.roll((((JSONArray) PcCritical.get("RangedDescriptionArray")).size())) - 1)) );
				}
				o.write( (String)((JSONArray) PcHit.get("RangedDescriptionArray")).get((Dice.roll((((JSONArray) PcHit.get("RangedDescriptionArray")).size())) - 1)) );
			}
			else
			{
				if(roll == 20)
				{
					o.write( (String)((JSONArray) NpcCritical.get("RangedDescriptionArray")).get((Dice.roll((((JSONArray) NpcCritical.get("RangedDescriptionArray")).size())) - 1)) );
				}
				o.write( (String)((JSONArray) NpcHit.get("RangedDescriptionArray")).get((Dice.roll((((JSONArray) NpcHit.get("RangedDescriptionArray")).size())) - 1)) );
			}
		}
		else
		{
			if(aggressor.getEquippedWeapon().getType().equals("Melee"))
			{
				if(aggressorIsPc)
				{
					o.write( (String)((JSONArray) PcMiss.get("MeleeDescriptionArray")).get((Dice.roll((((JSONArray) PcMiss.get("MeleeDescriptionArray")).size())) - 1)) );
				}
				else
				{
					o.write( (String)((JSONArray) NpcMiss.get("MeleeDescriptionArray")).get((Dice.roll((((JSONArray) NpcMiss.get("MeleeDescriptionArray")).size())) - 1)) );
				}
			}
			else
			{
				if(aggressorIsPc)
				{
					o.write( (String)((JSONArray) PcMiss.get("RangedDescriptionArray")).get((Dice.roll((((JSONArray) PcMiss.get("RangedDescriptionArray")).size())) - 1)) );
				}
				else
				{
					o.write( (String)((JSONArray) NpcMiss.get("RangedDescriptionArray")).get((Dice.roll((((JSONArray) NpcMiss.get("RangedDescriptionArray")).size())) - 1)) );
				}
			}
		}

		if(defender.isDead())
		{
			if(aggressor.getEquippedWeapon().getType().equals("Melee"))
			{
				if(aggressorIsPc)
				{
					o.write( (String)((JSONArray) PcDeath.get("MeleeDescriptionArray")).get((Dice.roll((((JSONArray) PcDeath.get("MeleeDescriptionArray")).size())) - 1)) );
				}
				else
				{
					o.write( (String)((JSONArray) NpcDeath.get("MeleeDescriptionArray")).get((Dice.roll((((JSONArray) NpcDeath.get("MeleeDescriptionArray")).size())) - 1)) );
				}
			}
			else
			{
				if(aggressorIsPc)
				{
					o.write( (String)((JSONArray) PcDeath.get("RangedDescriptionArray")).get((Dice.roll((((JSONArray) PcDeath.get("RangedDescriptionArray")).size())) - 1)) );
				}
				else
				{
					o.write( (String)((JSONArray) NpcDeath.get("RangedDescriptionArray")).get((Dice.roll((((JSONArray) NpcDeath.get("RangedDescriptionArray")).size())) - 1)) );
				}
			}
		}
	}
}
