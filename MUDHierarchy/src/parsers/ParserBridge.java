package parsers;

import hierarchy.SliceMap;

import java.util.ArrayList;

import parsers.tokens.Action;

public class ParserBridge
{
	private SliceMap slicemap;
	
	private ArrayList<Action> verbMappings;
	private String dirObject;
	private int verbIndex;
	
	public ParserBridge(ArrayList<Action> verbMappings)
	{
		this.verbMappings = verbMappings;
	}
	
	public void interact()
	{
		slicemap.interact(verbMappings.get(verbIndex), dirObject);
	}
	
	public void setDirObject(String dirObject)
	{
		this.dirObject = dirObject;
	}
	
	public void setVerb(int verbIndex)
	{
		this.verbIndex = verbIndex;
	}
	
}
