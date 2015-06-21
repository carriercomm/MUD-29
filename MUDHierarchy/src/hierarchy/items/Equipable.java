package hierarchy.items;

import hierarchy.Item;
import hierarchy.subsystems.Ability;

public class Equipable extends Item
{
	private boolean isEquiped;
	
	public Equipable(String fileName) throws Exception
	{
		super(fileName);
		
		this.isEquiped = (boolean) (super.JsonFile).get("IsEquiped");
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

	@Override
	public String getDescription(int level)
	{
		String temp = "";
		for(int i = 0; i < level; i++)
			temp += "\t";
		final String tabs = temp;
		
		if(!super.isHidden)
			return tabs + super.description +"\n"+ super.abilities.stream().map(Ability::getDescription).filter(s -> s != null).reduce((s,t) -> s+t).orElse("");
		return "";
	}

}
