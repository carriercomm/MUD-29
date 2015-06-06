package npcOutlineTest;
import java.util.*;
import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


//import javax.json;

public class npcTestMain {

	public static void main(String[] args) {
		String fileName = "ClassOutline.json"; //For testing
		Map<Integer,Object >savesAndBonus  = new HashMap<Integer, Object>();
		JSONParser parser = new JSONParser();
			try {
					JSONObject JsonFile = (JSONObject)(parser.parse(new FileReader("res/classes/"+fileName)));
					JSONArray JsonArray=(JSONArray)JsonFile.get("Levels");
					Iterator iter = JsonArray.iterator();
					int x = 1;
					while(iter.hasNext())
					{
						savesAndBonus.put(x, iter.next());
						x++;
					}
					System.out.println(((JSONObject)(savesAndBonus.get(1))).get("BaseAttackBonus"));
					
					
					
					
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
/*JSONParser parser = new JSONParser();
		JSONValue test = new JSONValue();
		
		
		try {
			JSONArray NpcTest = new JSONArray();
			NpcTest.add(parser.parse(new FileReader("res/creatures/NPCOutline.json")));
			JSONObject NpcClass =(JSONObject) NpcTest.get(0);
			System.out.print(((JSONArray)(NpcClass.get("BaseStat"))).get(0));
			
			
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
		
		*/
