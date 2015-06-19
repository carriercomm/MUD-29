package hierarchy.items;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;

import hierarchy.Item;
import hierarchy.subsystems.ItemBuilder;

public class Container extends Item
{
	private ArrayList<Item> subItems = new ArrayList<Item>();
	
	@SuppressWarnings("unchecked")
	public Container(String fileName) throws Exception
	{
		super(fileName);
		
		subItems = ((ArrayList<Item>) ((JSONArray)JsonFile.get("Items")).stream().map(i -> new ItemBuilder((String) i).getItem()).collect(Collectors.toList()));
	}

	public ArrayList<Item> getSubItems()
	{
		return subItems;
	}
	
	public Item getSubItem(Predicate<Item> p)	// test a predicate against all of the items in the subItem ArrayList. returns the first match
	{
		return (Item) subItems.stream().map(t -> {
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

}
