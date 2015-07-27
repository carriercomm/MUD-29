package game.hierarchy.utilities;

import game.OutputManager;
import game.hierarchy.Pc;
import game.hierarchy.Portal;
import game.hierarchy.Root;
import game.parsers.tokens.Action;

public class Move
{
	Pc pc;
	Portal portal;
	
	public boolean move(Action a, String ability, String target, OutputManager o, Root root)
	{
		pc = root.getCharacter();
		portal = root.getCharacterLocation().getPortal(target);
		
		if(portal != null && ability == null && !portal.isLocked())
		{
			pc.move(portal.getSliceKey(), portal.getLocationKey());
			o.write("You head through the " + target + "\n");
			o.write(root.getCharacterLocation().getDescription());
			return true;
		}
		else if( portal == null)
		{
			o.write("Invalid command. '" + target + "' is not a valid target\n");
		}
		else if(ability != null)
		{
			o.write("Invalid command. Move does not require an ability.\n");
		}
		else if(portal.isLocked())
		{
			o.write("Its locked!");
		}
		return false;
	}

	public void close()
	{
		// TODO Auto-generated method stub
		
	}

}
