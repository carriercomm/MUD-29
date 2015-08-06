package game.hierarchy.items;

public class Consumable extends Item
{
	private int uses;
	
	public Consumable(String fileName) throws Exception
	{
		super(fileName);
		
		this.uses 		= (int) (super.JsonFile).get("Uses");
	}
	
	public int getCurrentUses()
	{
		return uses;
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
