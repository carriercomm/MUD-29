package parsers;

import game.OutputManager;
import hierarchy.Root;
import hierarchy.utilities.Combat;
import hierarchy.utilities.Conversation;
import hierarchy.utilities.Inventory;
import hierarchy.utilities.Store;
import parsers.tokens.Action;

public class Interpreter
{
	private Root root;
	private OutputManager o;
	
	private Combat combat = new Combat();
	private Conversation conversation = new Conversation();
	private Store store = new Store();
	private Inventory inventory = new Inventory();
	
	public Interpreter(Root root, OutputManager o)
	{
		this.o = o;
		this.root = root;
	}
	
	public void interpret(String action, String ability, String target)
	{
		try
		{
			Action a = Action.valueOf(action);
			
			switch(a)
			{
			case attack:
				combat.fight(root.getCharacter(), root.getCreature(target));
			break;
			case talk:
				conversation.talk(root.getCharacter(), root.getCreature(target));
			break;
			case trade:
				store.trade(root.getCharacter(), root.getCreature(target));
			break;
			case use:	// TODO: search all location items and inventory items and run interact() on them. filter passed action
				root.getCharacterLocation().getItem(target).interact(Action.use);
			break;
			case examine:	// searches location for a matching target, then prints description
				root.getTargetDescription(target);
			break;
			case go:	// move character to a new location
				root.getCharacterLocation().getPortal(target).interact(Action.go, root.getCharacter());	// search location portals
			break;
			case take:
			case drop:
			case equip:
			case unequip:
			case inventory:	// deals with equipping, unequipping, and consumable usage
				inventory.interact(a, target);
			break;
			case rest:	// checks to see if the current location is a valid resting spot
				root.getCharacterLocation().getPortal(target).interact(Action.go, root.getCharacter());	// search location portals
			break;
			case cast:	// manages the character's spells
				root.getCharacterLocation().getPortal(target).interact(Action.go, root.getCharacter());	// search location portals
			break;
			default:
				o.write("Undefined action");
			break;
			}
		}
		catch(Exception e)
		{
			o.write("Invalid Action. Type help for a list of actions");
		}
	}

	public void close()
	{
		
	}
	
}













/*			case take:
Item takeItem = root.getCharacterLocation().getItem(target);	// takes from the player's location
root.getCharacterLocation().removeItem(takeItem);				// TOSO: add taking objects from containers
root.getCharacter().addItem(takeItem);
break;
case drop:
Item dropItem = root.getCharacterLocation().getItem(target);	// drops to the player's location
root.getCharacter().removeItem(dropItem);						// TODO: add placing stuff in containers
root.getCharacterLocation().addItem(dropItem);
break;
case equip:
root.getCharacter().getItem(target).interact(Action.equip);	// TODO: use interact() on items, filter actions and call methods
break;									//
case unequip:							//
root.getCharacter().getItem(target).interact(Action.unequip);	//
break;*/
