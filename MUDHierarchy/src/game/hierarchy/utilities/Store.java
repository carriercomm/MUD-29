package game.hierarchy.utilities;

import game.InputManager;
import game.MenuFormer;
import game.OutputManager;
import game.hierarchy.Npc;
import game.hierarchy.Pc;
import game.hierarchy.Root;
import game.hierarchy.items.Container;
import game.parsers.tokens.Action;

public class Store
{
	Pc pc;
	Npc npc;
	Container c;
	
	OutputManager o;
	int option = 0;
	
	MenuFormer mformer = new MenuFormer(4, true, true);
	
	public boolean trade(Action a, String ability, String target, Root root, InputManager i, OutputManager o)
	{
		this.pc = root.getCharacter();
		this.npc = root.getCharacterLocation().getNpc(target);
		this.o = o;
		
		if(npc != null && ability == null && npc.hasMerchantInventory())
		{
			this.c = npc.getMerchantInventory();
			this.display(this.c);
			
			while(option != -1)
			{
				String input = i.read();
				if(input != null)
				{
					try
					{
						int selection = Integer.parseInt(input);
						if(selection >= 0 && selection <= c.getSubItems().size())
						{
							o.write(String.format("%d", selection) + "\n");
							this.display(this.c);
						}
						else
						{
							o.write("Invalid input. Enter the number next to the response you wish to select.\n");
						}
					}
					catch(Exception e)
					{
						o.write("Invalid input. Enter the number next to the response you wish to select.\n");
						//e.printStackTrace(o.getPrintWriter());
					}
				}
			}
		}
		
		return false;
	}
	
	public void display(Container c)
	{
		mformer.addInputs(new String[]{"Name","Weight","Cost","Description"});
		c.getSubItems().stream()
						.forEachOrdered(i -> 
						this.mformer.addInputs(new String[]{
								i.getName(), 
								String.format("%s", i.getWeight()), 
								String.format("%d", i.getCost()) + "gp",
								i.getDescription()}));
		
		o.write(mformer.format());
	}
}
