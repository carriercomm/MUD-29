package game.parsers;

import game.OutputManager;
import game.hierarchy.Root;
import game.hierarchy.RootObject;
import game.hierarchy.utilities.Cast;
import game.hierarchy.utilities.Combat;
import game.hierarchy.utilities.Conversation;
import game.hierarchy.utilities.Interact;
import game.hierarchy.utilities.Inventory;
import game.hierarchy.utilities.Move;
import game.hierarchy.utilities.Rest;
import game.hierarchy.utilities.Store;
import game.parsers.tokens.Action;

public class Interpreter
{
	private Root root;			// hierarchy control
	private OutputManager o;	// standard output
	
	private Combat combat = new Combat();					// Hierarchy utilities are initialized here
	private Conversation conversation = new Conversation();	// used to control various interactions
	private Store store = new Store();
	private Inventory inventory = new Inventory();
	private Rest rest = new Rest();
	private Interact interact = new Interact();
	private Move move = new Move();
	private Cast cast = new Cast();
	
	private String action = null, ability = null, target = null;
	private boolean success = false;
	
	public Interpreter(Root root, OutputManager o)
	{
		this.o = o;
		this.root = root;
	}
	
	public boolean interpret(String actionT, String abilityT, String targetT)
	{
		this.action = actionT;
		this.ability = abilityT;
		this.target = targetT;
		this.success = false;
		
		this.checkValues();
		
		try
		{
			Action a = Action.valueOf(action);
			
			switch(a)
			{
				case attack:
					success = combat.run(a, ability, target, o, root);	//***
				break;
				
				case talk:
					success = conversation.talk(a, ability, target, o, root);	//***
				break;
				
				case buy:
				case sell:
				case trade:
					success = store.trade(a, ability, target, o, root);	//***
				break;
				
				case use:	// calls interact() on items, and activates abilities
					success = interact.interact(a, ability, target, o, root);	//***
				break;
				
				case examine:	// searches location for a matching target, then prints description
					o.write(((RootObject)(root.getCharacterLocation().getTarget(target))).getDescription());	//***?
				break;
				
				case go:
					success = move.move(a, ability, target, o, root);
				break;
				
				case take:
				case drop:
				case equip:
				case unequip:
				case inventory:	// deals with equipping, unequipping, and consumable usage
					success = inventory.interact(a, ability, target, o, root);	//***
				break;
				
				case rest:	// checks to see if the current location is a valid resting spot
					success = rest.rest(a, ability, target, o, root);	//***
				break;
				
				case cast:	// manages the character's spell casting
					success = cast.cast(a, ability, target, o, root);
				break;
				
				case target:
					if(ability == null)
					{
						root.getCharacter().setTarget(target);
					}
				break;
				
				case help:
					if(ability == null && target == null)
					{
						o.write("Here are all of the valid actions:\n");
						for(Action e : Action.values())
						{
							o.write(e.toString() + "\n");
						}
					}
					else
					{
						o.write("Invalid action - target pair. The command 'help' does not require a target.\n");
						o.write("ability: \"" + ability + "\" target: \"" + target + "\"");
						o.write("character target: " + root.getCharacter().getTarget());
					}
				break;
				
				default:
					o.write(action + " is an undefined action.\n");
				break;
			}
			
			if(!a.equals(Action.target))
			{
				root.getCharacter().setTarget(null);	// reset player target if it was just used
				this.target = null;
			}
			
			return success;
		}
		catch(Exception e)
		{
			//e.printStackTrace(o.getPrintWriter());	// debugging
			//o.getPrintWriter().flush();
			o.write("Invalid Action. Type help for a list of actions\n");
			o.write(action + ", " + target + ", " + ability + "\n");
			return false;
		}
	}

	private void checkValues()
	{
		if(target == null)
		{
			target = root.getCharacter().getTarget();
		}
	}
	
	public void close()
	{
		this.combat.close();
		this.conversation.close();
		this.store.close();
		this.inventory.close();
		this.rest.close();
		this.interact.close();
		this.move.close();
		this.cast.close();
		
		this.combat = null;
		this.conversation = null;
		this.store = null;
		this.inventory = null;
		this.rest = null;
		this.interact = null;
		this.move = null;
		this.cast = null;
		
		this.o = null;
		this.root = null;
	}
}