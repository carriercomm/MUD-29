package hierarchy.subsystems;

import hierarchy.Item;
import hierarchy.items.Consumable;
import hierarchy.items.Container;
import hierarchy.items.Equipable;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ItemBuilder
{
	private String fileName;
	
	public ItemBuilder(String fileName)
	{
		this.fileName = fileName;
	}

	public Item getItem()
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
