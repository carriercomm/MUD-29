package hierarchy.items;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hierarchy.Item;
import hierarchy.subsystems.Ability;
import hierarchy.subsystems.ItemBuilder;

public class Container extends Item
{
	private ArrayList<Item> subItems = new ArrayList<Item>();
	private boolean isStatic;
	
	@SuppressWarnings("unchecked")
	public Container(String fileName) throws Exception
	{
		super(fileName);
		
		this.isStatic = (boolean) super.JsonFile.get("IsStatic");
		this.subItems = (ArrayList<Item>) ((JSONArray)JsonFile.get("SubItems")).stream().map(i -> new ItemBuilder((String)((JSONObject) i).get("FileName")).getItem()).collect(Collectors.toList());
	}

	public boolean getIsStatic()
	{
		return isStatic;
	}
	
	public ArrayList<Item> getSubItems()
	{
		return subItems;
	}
	
	public Item getSubItem(Predicate<Item> p)	// test a predicate against all of the items in the subItem ArrayList. returns the first match
	{
		return (Item) subItems.stream().map(t -> 
		{
			if(p.test(t))
			{
				return t;
			}
			return null;
		});
	}
	
	@Override
	public String interact()
	{
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
