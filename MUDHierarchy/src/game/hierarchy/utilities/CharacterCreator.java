package game.hierarchy.utilities;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import game.OutputManager;
import game.hierarchy.Pc;

public class CharacterCreator
{
	private JSONArray classArray = new JSONArray();
	private OutputManager o;

	@SuppressWarnings("unchecked")
	public CharacterCreator(OutputManager o)
	{
		this.o = o;
		
		try
		{
			JSONParser parser = new JSONParser();
			
			File folder = new File("res/classes");
			File[] files = folder.listFiles();
			
			if (folder != null)
			{
				for (File file : files)
				{
					if(file.isFile() && (file.getName().endsWith(".json")))
						classArray.add(parser.parse(new FileReader(file.getPath())));
				}
			}
		}
		catch(Exception e)
		{
			this.o.write("!!!!!Class directory not found!!!!!\n");
			e.printStackTrace(this.o.getPrintWriter());
		}
	}
	
	public Pc create()
	{
		o.write("First time character creation reached\n");
		return null;	// need to figure out how to pipe the user input to here instead of the parser
	}
}
