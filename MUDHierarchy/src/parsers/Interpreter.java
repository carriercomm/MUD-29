package parsers;

import game.OutputManager;
import hierarchy.Root;
import hierarchy.rootObject;
import hierarchy.utilities.Combat;
import hierarchy.utilities.Conversation;
import hierarchy.utilities.Inventory;
import hierarchy.utilities.Store;
import parsers.tokens.Action;

public class Interpreter
{
	private Root root;			// hierarchy control
	private OutputManager o;	// standard output
	
	private Combat combat = new Combat();					// Hierarchy utilities are initialized here
	private Conversation conversation = new Conversation();	// used to control various interactions
	private Store store = new Store();
	private Inventory inventory = new Inventory();
	
	@SuppressWarnings("unused")
	private String action, ability, target;
	
	public Interpreter(Root root, OutputManager o)
	{
		this.o = o;
		this.root = root;
	}
	
	public void interpret(String action, String ability, String target)
	{
		this.action = action;
		this.ability = ability;
		this.target = target;
		
		this.checkValues();
		
		try
		{
			Action a = Action.valueOf(action);
			
			switch(a)
			{
				case attack:
					combat.fight(a, ability, target);	//***
				break;
				case talk:
					conversation.talk(a, ability, target);	//***
				break;
				case trade:
					store.trade(a, ability, target);	//***
				break;
				case use:	// TODO: search all location items and inventory items and run interact() on them. filter passed action
					root.getCharacterLocation().getItem(target).interact(Action.use);
				break;
				case examine:	// searches location for a matching target, then prints description
					o.write(((rootObject)(root.getTarget(target))).getDescription());
				break;
				case go:	// move character to a new location
					root.getCharacterLocation().getPortal(target).interact(Action.go, root.getCharacter());	// search location portals
				break;
				case take:
				case drop:
				case equip:
				case unequip:
				case inventory:	// deals with equipping, unequipping, and consumable usage
					inventory.interact(a, ability, target);	//***
				break;
				case rest:	// checks to see if the current location is a valid resting spot
					root.getCharacterLocation().getPortal(target).interact(Action.go, root.getCharacter());	// search location portals
				break;
				case cast:	// manages the character's spells
					root.getCharacterLocation().getPortal(target).interact(Action.go, root.getCharacter());	// search location portals
				break;
				case target:
					if(ability == null)
					{
						root.setCharacterTarget(target);
					}
				break;
				case help:
					if(action == null && target == null)
					{
						o.write("Here are all of the valid actions:\n");
						for(Action actionT : Action.values())
						{
							o.write(actionT.toString() + "\n");
						}
					}
					else
					{
						o.write("Invalid action - target pair. The command 'help' does not require a target.");
					}
				break;
				default:
					o.write(a + " is an undefined action.");
				break;
			}
			
			if(a != Action.target)
			{
				root.setCharacterTarget(null);	// reset player target if it was just used
			}
		}
		catch(Exception e)
		{
			o.write("Invalid Action. Type help for a list of actions");
		}
	}

	private void checkValues()
	{
		if(target == null)
		{
			target = root.getCharacterTarget();
		}
	}
	
	public void close()
	{
		//TODO
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
