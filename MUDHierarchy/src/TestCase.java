import hierarchy.Npc;
import hierarchy.SliceMap;

public class TestCase 
{

	public static void main(String[] args) 
	{
		try
		{
/*			Npc Goblin1 = new Npc("GoblinScout.json",1);
			System.out.println(Goblin1.print());
			Goblin1.levelUp();
			System.out.println(Goblin1.print());*/
			
			SliceMap slicemap = new SliceMap("slicemap.json");
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
