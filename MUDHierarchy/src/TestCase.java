import hierarchy.Npc;
public class TestCase 
{

	public static void main(String[] args) 
	{
		try {
			Npc Goblin1 = new Npc("GoblinScout.json",1);
			System.out.println(Goblin1.print());
			Goblin1.levelUp();
			System.out.println(Goblin1.print());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
