import hierarchy.Npc;
public class TestCase 
{

	public static void main(String[] args) 
	{
		try {
			Npc Goblin1 = new Npc("GoblinScout.json", 1);
			Npc Goblin2 = new Npc("GoblinScout.json", 20);
			Npc Goblin3 = new Npc("GoblinScout.json", 6);
			System.out.println(Goblin1.print());
			System.out.println(Goblin2.print());
			System.out.println(Goblin3.print());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
