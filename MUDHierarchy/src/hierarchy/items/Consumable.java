package hierarchy.items;

import hierarchy.Item;

public class Consumable extends Item
{
	private int usesTotal;
	private int usesCurrent;
	
	public Consumable(String fileName) throws Exception
	{
		super(fileName);
		
		usesTotal 		= (int) (super.JsonFile).get("UsesTotal");
		usesCurrent 	= (int) (super.JsonFile).get("UsesCurrent");
	}

	public int getUsesTotal()
	{
		return usesTotal;
	}
	
	public int getUsesCurrent()
	{
		return usesTotal - usesCurrent;
	}
	
	@Override
	public String interact()
	{
		return null;
	}

}
