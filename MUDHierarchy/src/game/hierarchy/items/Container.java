package game.hierarchy.items;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import game.hierarchy.subsystems.Ability;
import game.hierarchy.subsystems.ItemBuilder;

public class Container extends Item
{
	private boolean isInteractable;
	private boolean isOpenable;
	private boolean isOpen;
	private ArrayList<Item> subItems = new ArrayList<Item>();
	
	public Container(String fileName) throws Exception
	{
		super(fileName);
		this.containerSetup();
	}
	
	public Container(String fileName, String description, String name, ArrayList<Item> items) throws Exception
	{
		super(fileName, description, name);
		this.containerSetup();
		this.subItems = items;
	}
	
	@SuppressWarnings("unchecked")
	private void containerSetup()
	{
		this.isInteractable = (boolean) JsonFile.get("IsInteractable");
		this.isOpenable = (boolean) JsonFile.get("IsOpenable");
		this.isOpen = (boolean) JsonFile.get("IsOpen");
		this.subItems = (ArrayList<Item>) ((JSONArray)JsonFile.get("SubItems")).stream().map(i -> ItemBuilder.getItem((String)((JSONObject) i).get("FileName"))).collect(Collectors.toList());
	}
	
	public boolean isInteractable()
	{
		return isInteractable;
	}

	public boolean isOpenable()
	{
		return isOpenable;
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	public ArrayList<Item> getSubItems()
	{
		return subItems;
	}
	
	public Item getItem(int index)
	{
		return subItems.get(index);
	}
	
	public void addItem(Item item)
	{
		subItems.add(item);
	}
	
	public boolean removeItem(Item item)
	{
		for(Item i : subItems)
		{
			if(i.equals(item))
			{
				subItems.remove(i);
				return true;
			}
			else if(i.getType().equals("Container"))
			{
				if(((Container)i).removeItem(item))
					return true;
			}
		}
		return false;
	}
	
/*	public Item getSubItem(Predicate<Item> p)	// test a predicate against all of the items in the subItem ArrayList. returns the first match
	{
		return (Item) subItems.stream().map(t -> 
		{
			if(p.test(t))
			{
				return t;
			}
			return null;
		});
	}*/
	
	@Override
	public String interact()
	{
		//TODO: override this
		return null;
	}

	@Override
	public String getDescription(int level)
	{
		String temp = "";
		for(int i = 0; i < level; i++)
			temp += "\t";
		final String tabs = temp;
		
		if(!super.isHidden)
		{
			return tabs + super.description +"\n"+ super.abilities.stream().map(Ability::getDescription).filter(s -> s != null).reduce((s,t) -> s+t).orElse("")
									 + (subItems.size() > 0 ? tabs+"On it you see:\n" : "")
									 + this.subItems.stream().map(i -> i.getDescription(level+1)).filter(s -> s != null).reduce((s,t) -> s+t).orElse("");
		}
		
		return "";
	}

	@Override
	public String getDescription()
	{
		return super.description;
	}	
}
