package game.hierarchy;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import game.parsers.tokens.Action;

public class Portal extends RootObject
{
	private String name;
	private String description;
	private int	   sliceKey;
	private String locationKey;
	private boolean isLocked;

	public Portal(String fileName)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject jsonFile = new JSONObject((JSONObject) parser.parse(new FileReader("res/portals/" + fileName)));
			
			this.name =			(String)	jsonFile.get("Name");
			this.description=	(String) 	jsonFile.get("Description");
			this.sliceKey=		(int)(long) jsonFile.get("SliceKey");
			this.locationKey=	(String)	jsonFile.get("LocationKey");
			this.isLocked= 		(boolean)	jsonFile.get("IsLocked");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public int getSliceKey()
	{
		return sliceKey;
	}
	public String getLocationKey()
	{
		return locationKey;
	}
	
	public String getDescription()
	{
		return  name + ": " + description + "\n";
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isLocked()
	{
		return isLocked;
	}
	
	public String interact(Action a, Pc p)
	{
		if(a == Action.go && isLocked)
		{
			p.move(sliceKey, locationKey);
			return "You go through the door.";
		}
		else if(a == Action.go && !isLocked)
		{
			return "The door is locked.";
		}
		return "What are you trying to do?";
	}
}