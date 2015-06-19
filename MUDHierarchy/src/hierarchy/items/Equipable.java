package hierarchy.items;

import hierarchy.Item;

public class Equipable extends Item
{
	private boolean isEquiped;
	
	public Equipable(String fileName) throws Exception
	{
		super(fileName);
		
		isEquiped = (boolean) (super.JsonFile).get("IsEquipped");
	}

	public boolean isEquiped()
	{
		return isEquiped;
	}
	
	@Override
	public String interact()
	{
		return null;
	}

}
