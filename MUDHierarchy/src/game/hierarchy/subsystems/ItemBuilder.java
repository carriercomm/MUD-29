package game.hierarchy.subsystems;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import game.hierarchy.items.Consumable;
import game.hierarchy.items.Container;
import game.hierarchy.items.Equipable;
import game.hierarchy.items.Item;

public class ItemBuilder
{
	public static Item getItem(String fileName)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject item = (JSONObject)(parser.parse(new FileReader("res/items/"+fileName)));
			switch((String)item.get("Type"))
			{
				case "Consumable":
					return new Consumable(fileName);
				case "Container":
					return new Container(fileName);
				case "Equipable":
					return new Equipable(fileName);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
