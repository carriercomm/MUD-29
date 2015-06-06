package npcOutlineTest;
import java.util.*;
import java.io.*;

//import org.json.simple.*;
//import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


//import javax.json;

public class npcTestMain {

	public static void main(String[] args) {
		 //JSONObject NpcTest = Json.createObjectBuilder().build();
		JSONParser parser = new JSONParser();
		JSONValue test = new JSONValue();
		
		
		try {
			JSONArray NpcTest = new JSONArray();
			NpcTest.add(parser.parse(new FileReader("D:/Users/Bennett/Documents/Workspace/MUDHierarchy/res/creatures/NpcOutline.json")));
			JSONObject NpcClass =(JSONObject) NpcTest.get(0);
			System.out.print(NpcClass.get("Name"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
