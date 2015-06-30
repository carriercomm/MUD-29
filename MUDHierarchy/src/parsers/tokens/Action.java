package parsers.tokens;

public enum Action	// enum of the unique actions possible
{					// the interpreter maps these to specific methods in the map root, or interaction utilities
	attack,
	talk,
	trade,
	use,
	take,
	drop,
	equip,
	unequip,
	examine,
	go;
}
