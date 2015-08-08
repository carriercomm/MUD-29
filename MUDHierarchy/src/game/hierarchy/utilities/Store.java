package game.hierarchy.utilities;

import java.util.ArrayList;

import game.InputManager;
import game.OutputManager;
import game.hierarchy.Npc;
import game.hierarchy.Pc;
import game.hierarchy.Root;
import game.hierarchy.items.Item;
import game.parsers.tokens.Action;

public class Store
{
	private Pc pc;
	private Npc npc;
	private ArrayList<Item> npcc;	// merchant inventory
	private ArrayList<Item> pcc;	// player inventory
	
	private OutputManager o;
	private InputManager i;
	
	MenuFormer mformer = new MenuFormer(4);	//  new menu with four elements per line
	
	public boolean trade(Action a, String ability, String target, Root root, InputManager i, OutputManager o)
	{
		this.o = o;
		this.i = i;
		
		this.pc = root.getCharacter();
		this.npc = root.getCharacterLocation().getNpc(target);
		
		this.npcc = npc.getMerchantInventory().getSubItems();
		this.pcc = pc.getInventory();
		
		if(npc != null && ability == null && npc.getIsInteractable() && npc.hasMerchantInventory())
		{	
			o.write("Buy, Sell, or Quit? (b/s/q)\n");
			
			String strinput  = "";
			while(true)
			{
				strinput = i.read();
				if(strinput != null)
				{
					if(strinput.equals("b"))
						this.shop(npcc, true, pc, npc);
					else if(strinput.equals("s"))
						this.shop(pcc, false, pc, npc);
					else if(strinput.equals("q"))
						break;
					else
						o.write("Invalid input. Enter 'b', 's', or 'q'.\n");
					
					o.write("Buy, Sell, or Quit? (b/s/q)\n");
				}
			}
			
			return true;
		}
		else if(npc != null && ability == null && !npc.getIsInteractable())
		{
			o.write("You can not interact with" + npc.getName() + ".\n");
		}
		else if(npc == null)
		{
			o.write("Invalid command. " + target + " is not a valid target.\n");
		}
		else if(ability != null)
		{
			o.write("Invalid command. 'Trade' does not require an ability.\n");
		}
		
		return false;
	}
	
	private void shop(ArrayList<Item> c, boolean isbuy, Pc pc, Npc npc)
	{
		String strinput = null;
		String numinput = null;
		int selection = 0;	// parsed numinput
		
		this.display(c);
		
		while(selection != -1)
		{	
			numinput = i.read();
			if(numinput != null)
			{
				try
				{
					selection = Integer.parseInt(numinput);
					if(selection > 0 && selection <= c.size())	// if the item selection is valid
					{
						Item item = c.get(selection - 1);
						
						if(isbuy)
							o.write("Would you like to buy the " + item.getName() + " for " + item.getCost() + "gp? (y/n)\n");
						else
							o.write("Would you like to sell the " + item.getName() + " for " + item.getCost() + "gp? (y/n)\n");
						
						while(true)
						{
							strinput = i.read();
							if(strinput != null)
							{
								if(strinput.toLowerCase().equals("y"))
								{
									if(isbuy && pc.getGold() >= item.getCost())
									{
										npc.removeItem(item);
										pc.setGold(pc.getGold() - item.getCost());
										pc.addItem(item);
									}
									else if(!isbuy)
									{
										pc.removeItem(item);
										npc.addItem(item);
										pc.setGold(pc.getGold() + item.getCost());
									}
									else if(isbuy && pc.getGold() < item.getCost())
									{
										o.write("You do not have enough gold to purchase this item.\n");
									}
									this.display(c);
									break;
								}
								else if(strinput.toLowerCase().equals("n"))
								{
									this.display(c);
									break;
								}
								else
									o.write("Invalid input. Enter 'y' or 'n'.\n");
							}
						}
					}
					else if(selection == (c.size() + 1))
					{
						selection = -1;	// set exit value if 'exit' is selected
					}
					else
					{
						o.write("Invalid input. Enter the number next to the item you wish to select.\n");
					}
				}
				catch(Exception e)
				{
					o.write("Invalid input. Enter the number next to the item you wish to select.\n");
					//e.printStackTrace(o.getPrintWriter());
				}
			}
		}
	}
	
	private void display(ArrayList<Item> items)
	{
		mformer.addInputs(new String[]{"Name","Weight","Cost","Description"});	// add the title to the menu
		if(items != null)
		{
			items.stream()															// add all of the items and their attributes
				 .forEachOrdered(i -> 
				 this.mformer.addInputs(new String[]{
						 	i.getName(), 
						 	String.format("%s", i.getWeight()) + "lb", 
						 	String.format("%d", i.getCost()) + "gp",
						 						i.getDescription()}));
		}
		
		o.write(mformer.format());
	}
	
	
	
	private class MenuFormer
	{
		private int strPerLine;
		private int[] maxWidths;
		private ArrayList<String[]> inputs = new ArrayList<String[]>();
		
		/*
		** The first input is the title of the Menu
		*/
		public MenuFormer(int strPerLine)
		{
			this.strPerLine = strPerLine;
			this.maxWidths = new int[strPerLine];
		}
		
		public void addInputs(String[] input)
		{
			String[] strArr = input;
			for(int i = 0; i<strPerLine; i++){
				if(strArr[i].length() > maxWidths[i])
					maxWidths[i] = strArr[i].length();
			}
			inputs.add(strArr);
		}
		
		private int getLineLength()
		{
			return maxWidths[maxWidths.length-1];
		}
		
		private String makeLine(String character, int length)
		{
			String temp = "";
			if(length > 0){
				for(int i = 0; i<length; i++)
					temp += character;
			}
			return temp;
		}
		
		public String format()
		{
			String total = "\n";
			String partial = "";
			
			int counter = 0;
			
			for(int i = 0; i < maxWidths.length; i++){	// set up line spacing & max widths of each section
				if((i-1) >= 0)
					maxWidths[i] = (maxWidths[i-1] + maxWidths[i] + 2);
				else
					maxWidths[i] = maxWidths[i] + 2;
			}
			
			for(String[] arr : inputs)
			{
				for(int i = 0; i < strPerLine; i++){
					partial += arr[i];	// add the first word
					partial += this.makeLine(" ", (maxWidths[i] - partial.length()));	// add spaces so that it is as long as the section
				}
				
				if(counter == 0)
					total += "   ";	// add numbering before line
				else
					total += counter + "> ";
				
				total += partial + "\n";	// add partial line to the total 
				
				partial = "";
				counter++;
				
				if(counter == 1)	// inserting line under header
					total += makeLine("-", this.getLineLength() + (strPerLine - 1)) + "\n";
			}
			
			total += counter + "> " + "exit\n";
			maxWidths = new int[maxWidths.length];	// reset offsets
			inputs.clear();	// reset inputs
			
			return total;
		}
	}
}
