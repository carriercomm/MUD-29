package game.hierarchy.utilities;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

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
	private TextHandler cText;
	
	public Combat(OutputManager o)
	{
		this.cText = new TextHandler(o);
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
		String subtype = aggressor.getEquippedWeapon().getType().substring(0,1);
		
		if
		(((subtype.equals("M")) && 
		((aggressor.getStats().getBAB() + aggressor.getStats().getStat("StrengthMod") + roll)) >= (defender.getAc())) ||
		((subtype.equals("R")) && 
		((aggressor.getStats().getBAB() + aggressor.getStats().getStat("DexterityMod") + roll)) >= (defender.getAc())))
		{
			defender.getStats().setCurrHp(defender.getStats().getCurrHp() - Dice.roll(aggressor.getEquippedWeapon().getDamage()));
			
			if(aggressorIsPc){
				if(roll == 20)
					o.write( cText.get(subtype + "PcCritical"));
				else
					o.write( cText.get(subtype + "PcHit"));
			}else{
				if(roll == 20)
					o.write( cText.get(subtype + "NpcCritical"));
				else
					o.write( cText.get(subtype + "NpcHit"));
			}	
		}
		else
		{
			if(aggressorIsPc)
				o.write( cText.get(subtype + "PcMiss"));
			else
				o.write( cText.get(subtype + "NpcMiss"));
		}

		if(defender.isDead()){
			if(aggressorIsPc)
				o.write( cText.get(subtype + "PcDeath"));
			else
				o.write( cText.get(subtype + "NpcDeath"));
		}
	}
	
	private class TextHandler
	{
		JSONObject PcCritical, PcDeath, PcHit, PcMiss, NpcCritical, NpcDeath, NpcHit, NpcMiss;
		private Map<String, JSONArray> cText = new HashMap<String, JSONArray>();
		
		public TextHandler(OutputManager o)
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
				
				cText.put("MPcCritical", (JSONArray) PcCritical.get("MeleeDescriptionArray"));
				cText.put("MPcDeath", (JSONArray) PcDeath.get("MeleeDescriptionArray"));
				cText.put("MPcHit",(JSONArray) PcHit.get("MeleeDescriptionArray"));
				cText.put("MPcMiss", (JSONArray) PcMiss.get("MeleeDescriptionArray"));
				cText.put("MNpcCritical", (JSONArray) NpcCritical.get("MeleeDescriptionArray"));
				cText.put("MNpcDeath", (JSONArray) NpcDeath.get("MeleeDescriptionArray"));
				cText.put("MNpcHit", (JSONArray) NpcHit.get("MeleeDescriptionArray"));
				cText.put("MNpcMiss", (JSONArray) NpcMiss.get("MeleeDescriptionArray"));
				
				cText.put("RPcCritical", (JSONArray) PcCritical.get("RangedDescriptionArray"));
				cText.put("RPcDeath", (JSONArray) PcDeath.get("RangedDescriptionArray"));
				cText.put("RPcHit", (JSONArray) PcHit.get("RangedDescriptionArray"));
				cText.put("RPcMiss", (JSONArray) PcMiss.get("RangedDescriptionArray"));
				cText.put("RNpcCritical", (JSONArray) NpcCritical.get("RangedDescriptionArray"));
				cText.put("RNpcDeath", (JSONArray) NpcDeath.get("RangedDescriptionArray"));
				cText.put("RNpcHit", (JSONArray) NpcHit.get("RangedDescriptionArray"));
				cText.put("RNpcMiss", (JSONArray) NpcMiss.get("RangedDescriptionArray"));
			}
			catch(Exception e)
			{
				e.printStackTrace(o.getPrintWriter());
			}
		}
		
		public String get(String key)
		{
			JSONArray array = cText.get(key);
			String text = ((String) array.get(Dice.roll(array.size() - 1))) + "\n";
			
			return text;
		}
	}
}
