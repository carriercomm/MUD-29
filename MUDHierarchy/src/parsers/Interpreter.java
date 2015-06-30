package parsers;

import hierarchy.Root;
import hierarchy.utilities.Combat;
import hierarchy.utilities.Conversation;
import hierarchy.utilities.Store;

import parsers.tokens.Action;

public class Interpreter
{
	private Root root;
	
	Combat combat = new Combat();
	Conversation conversation = new Conversation();
	Store store = new Store();
	
	public Interpreter(Root root)
	{
		this.root = root;
	}
	
	public void interpret(String dirObject, Action action)
	{
		switch(action)
		{
		case attack:
			combat.fight(root.getCharacter(), root.getCreature(dirObject));
		break;
		case talk:
			conversation.talk(root.getCharacter(), root.getCreature(dirObject));
		break;
		case trade:
			store.trade(root.getCharacter(), root.getCreature(dirObject));
		break;
		case use:
			root.interact(dirObject, action);
		break;
		case take:
			root.interact(dirObject, action);
		break;
		case drop:
			root.interact(dirObject, action);
		break;
		case equip:
			root.interact(dirObject, action);
		break;
		case unequip:
			root.interact(dirObject, action);
		break;
		case examine:
			root.interact(dirObject, action);
		break;
		case go:
			root.interact(dirObject, action);
		break;
		}
	}

	public void close()
	{
		
	}
	
}
