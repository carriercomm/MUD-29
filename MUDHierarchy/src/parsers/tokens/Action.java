package parsers.tokens;

public enum Action	// enum of the unique actions possible
{					// the interpreter maps these to specific methods in the map root, or interaction utilities
	attack ("attack"),
	talk ("talk"),
	trade ("trade"),
	use ("use"),
	take ("take"),
	drop ("drop"),
	equip ("equip"),
	unequip ("unequip"),
	examine ("examine"),
	go ("go"),
	inventory ("inventory"),
	rest ("rest"),
	cast ("cast");
	
	private String name;

	private Action(String name)
	{
		this.name = name;
	}
	
}
