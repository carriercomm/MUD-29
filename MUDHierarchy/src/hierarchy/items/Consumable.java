package hierarchy.items;

import hierarchy.Item;

public class Consumable extends Item
{
	private int usesTotal;
	private int usesCurrent;
	
	public Consumable(String fileName) throws Exception
	{
		super(fileName);
		
		this.usesTotal 		= (int) (super.JsonFile).get("UsesTotal");
		this.usesCurrent 	= (int) (super.JsonFile).get("UsesCurrent");
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

	@Override
	public String getDescription(int level)
	{
		String temp = "";
		for(int i = 0; i < level; i++)
			temp += "\t";
		final String tabs = temp;
		
		if(!super.isHidden)
			return tabs + super.description +"\n"+ super.abilities.stream().map(d -> d.getDescription()).filter(s -> s != null).reduce((s,t) -> s+t).orElse("");
		return "";
	}

	@Override
	public String getDescription()
	{
		return super.description;
	}

}
