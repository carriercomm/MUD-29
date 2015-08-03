import game.OutputManager;
import game.hierarchy.Npc;
import game.hierarchy.Root;

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
			System.out.println(slicemap.getLocation(0, "entrance").getDescription());
			System.out.println(slicemap.getLocation(0, "grand_hallway").getDescription());
			System.out.println(slicemap.getLocation(0, "audience_chamber").getDescription());
			System.out.println(slicemap.getLocation(0, "suite_hallway").getDescription());
			System.out.println(slicemap.getLocation(0, "dining_room").getDescription());
			System.out.println(slicemap.getLocation(0, "library").getDescription());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
