package hierarchy;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import parsers.tokens.Action;

public class Portal
{
	private String name;
	private String description;
	private int	   sliceKey;
	private String locationKey;
	private boolean canUse;

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
			this.canUse= 		(boolean)	jsonFile.get("CanUse");
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
		return description + "\n";
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean canUse()
	{
		return canUse;
	}
	
	public String interact(Action a, Pc p)
	{
		if(a == Action.go && canUse)
		{
			p.movePc(sliceKey, locationKey);
			return "You head through the unlocked door.";
		}
		else if(a == Action.go && !canUse)
		{
			return "The door is locked.";
		}
		return "What are you trying to do?";
	}
}