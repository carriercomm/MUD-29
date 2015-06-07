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
	
	private String Name;
	private String Description;
	private String SliceKey;
	private String LocationKey;
	private String Key;
	private boolean CanUse;
	
	
	public Portal(String fileName) throws FileNotFoundException, IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject jsonFile = new JSONObject((JSONObject) parser.parse(new FileReader("res/Portal/" + fileName)));
		this.Name =			(String)	jsonFile.get("Name");
		this.Description=	(String) 	jsonFile.get("Description");
		this.SliceKey=		(String)	jsonFile.get("SliceKey");
		this.LocationKey=	(String)	jsonFile.get("LocationKey");
		this.Key=			(String)	jsonFile.get("Key");
		this.CanUse= 		(boolean)	jsonFile.get("CanUse:");
	}
	
	public String getSliceKey()
	{
		return SliceKey;
	}
	public String getLocationKey()
	{
		return LocationKey;
	}
	public String getKey()
	{
		return Key;
	}
}
