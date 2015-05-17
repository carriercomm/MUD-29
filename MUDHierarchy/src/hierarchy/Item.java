package hierarchy;

import hierarchy.subsystems.ItemStats;

import java.util.ArrayList;

public abstract class Item
{
	private ArrayList<Item> subItems;
	private ItemStats stats;
	private String[] tokens;	// used in Interact() to determine if user input is valid
								// first token is the item type
	private String type;
	private String description;

	public Item(String descr, String type, String[] tokens, ArrayList<Item> subItems, ItemStats stats)
	{
		this.setDescription("\n" + descr + "\n");
		this.setType(type);
		this.setTokens(tokens);
		this.setSubItems(subItems);
		this.setItemStats(stats);
	}

	public String getDescription()
	{
		String descr = "";
		descr += this.description;
		
		for(Item i : subItems)
		{
			descr += i.description;
		}
		
		return descr;
	}
	
	private void setDescription(String descr)
	{
		this.description = descr;
	}

	public String getType()
	{
		return type;
	}

	private void setType(String type)
	{
		this.type = type;
	}
	
	private void setTokens(String[] tokens)
	{
		this.tokens = tokens;
	}
	
	private void setSubItems(ArrayList<Item> subItems)
	{
		this.subItems = subItems;
	}
	
	private void setItemStats(ItemStats stats)
	{
		this.stats = stats;
	}
	
	public abstract String Interact(String[] args);
}
