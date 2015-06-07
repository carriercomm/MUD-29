package hierarchy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Portal
{
	//private String[] tokens;
	private String name;
	private String description;
	private String sliceKey;
	private String locationKey;
	private String key;
	private boolean canUse;

	public Portal(String fileName) throws FileNotFoundException, IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject jsonFile = new JSONObject((JSONObject) parser.parse(new FileReader("res/Portal/" + fileName)));
		this.name =			(String)	jsonFile.get("Name");
		this.description=	(String) 	jsonFile.get("Description");
		this.sliceKey=		(String)	jsonFile.get("SliceKey");
		this.locationKey=	(String)	jsonFile.get("LocationKey");
		this.key=			(String)	jsonFile.get("Key");
		this.canUse= 		(boolean)	jsonFile.get("CanUse:");
	}
	public String getSliceKey()
	{
		return sliceKey;
	}
	public String getLocationKey()
	{
		return locationKey;
	}
	public String getKey()
	{
		return key;
	}
	
	public String getDescription()
	{
		return description;
	}
	
}