import game.OutputManager;
import hierarchy.Npc;
import hierarchy.Root;

public class TestCase 
{

	public static void main(String[] args) 
	{
		try
		{
			Npc Goblin1 = new Npc("GoblinScout.json",1);
			OutputManager o = new OutputManager();
			System.out.println(Goblin1.print());
			//Goblin1.levelUp();
			//System.out.println(Goblin1.print());
			
			Root slicemap = new Root("res/slicemap.json", o);
			slicemap.getLocationDescription(0, "entrance");
			slicemap.getLocationDescription(0, "grand_hallway");
			slicemap.getLocationDescription(0, "audience_chamber");
			slicemap.getLocationDescription(0, "suite_hallway");
			slicemap.getLocationDescription(0, "dining_room");
			slicemap.getLocationDescription(0, "library");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
