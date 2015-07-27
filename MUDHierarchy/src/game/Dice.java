package game;

import java.util.Random;

public class Dice
{
	static Random rand = new Random();
	
	public static int d20()
	{
		return (rand.nextInt(20) + 1);
	}

	public static int roll(int max)
	{
		return (rand.nextInt(max) + 1);
	}
	
	
}
