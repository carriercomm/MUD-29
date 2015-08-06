package game.hierarchy.subsystems;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ability
{
	private String name;
	private String damageType;
	private int maxDamage;
	private int numTargets;
	private Effect effect;
	
	public Ability(String fileName)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject ability = (JSONObject) parser.parse(new FileReader("res/abilities" + fileName));
			
			this.name = (String) ability.get("Ability");
			this.damageType = (String) ability.get("DamageType");
			this.maxDamage = (int) ability.get("MaxDamage");
			this.numTargets = (int) ability.get("NumberTargets");
			this.effect = (Effect) ability.get("Effect");
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	
	public String getDescription()
	{
		return "Ability description\n";
	}
	
}
