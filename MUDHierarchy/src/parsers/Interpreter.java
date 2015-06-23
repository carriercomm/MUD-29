package parsers;

import hierarchy.MapRoot;
import hierarchy.utilities.Combat;
import hierarchy.utilities.Conversation;
import hierarchy.utilities.Store;

import java.util.ArrayList;

import parsers.tokens.Action;

public class Interpreter
{
	private MapRoot root;
	
	Combat combat = new Combat();
	Conversation conversation = new Conversation();
	Store store = new Store();
	
	public Interpreter(MapRoot slicemap, ArrayList<Action> verbMappings)
	{
		this.root = slicemap;
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
			root.interact(action, dirObject);
		break;
		case take:
			root.interact(action, dirObject);
		break;
		case drop:
			root.interact(action, dirObject);
		break;
		case equip:
			root.interact(action, dirObject);
		break;
		case unequip:
			root.interact(action, dirObject);
		break;
		case examine:
			root.interact(action, dirObject);
		break;
		case go:
			root.interact(action, dirObject);
		break;
		}
	}
	
}
