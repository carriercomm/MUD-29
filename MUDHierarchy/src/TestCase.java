import hierarchy.Npc;
public class TestCase 
{

	public static void main(String[] args) 
	{
		try {
			Npc defaultNpc = new Npc("NpcOutline.json");
			System.out.println(defaultNpc.print());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
