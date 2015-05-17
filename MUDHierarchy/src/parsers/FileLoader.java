package parsers;
import java.util.ArrayList;

// Uses the object class that is taken in the constructor to make new objects from text file input
// A better system would use java reflectors to load objects with any constructor by finding the input data types and extracting them
public class FileLoader
{
	private ArrayList<Object> newObjs = new ArrayList<Object>();
	private Tokenizer tokenizer = new Tokenizer(true);

	private String fileName;
	private Object obj;
	private Class<? extends Object> objc;
	private int currentObj = 0;
	
	public FileLoader(){}	// empty constructor
	
	public FileLoader(Object o, String fileName)	// object to be made, and file with data
	{
		this.obj = o;
		this.objc = obj.getClass();
		this.fileName = fileName;
	}
	
	public void setFile(Object o, String fileName)	// for changing what the loader is loading
	{
		this.obj = o;
		this.objc = obj.getClass();
		this.fileName = fileName;
	}
	
	public void loadFile() throws Exception	// 
	{
		int numArgs = objc.getDeclaredField("numArgs").getInt(objc);	// all classes loaded must have a static int
																		// that represents the number of string args in the constructor
		try
		{
			this.tokenizer.addFile(this.fileName);
			
			while(tokenizer.hasNext(numArgs))	// use the tokenizer to grab the next numArgs of tokens from the file, then make a new object
			{									// if there are enough tokens and add it to the ArrayList
				newObjs.add(Class.forName(objc.getName()).getConstructor(String[].class)
							.newInstance(new Object[] {tokenizer.getNext(numArgs)}));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Object getNext()	// get an object
	{
		Object tempObj = newObjs.get(currentObj);
		this.currentObj++;
		return tempObj;
	}
	
	public boolean hasNext()
	{
		if(currentObj < newObjs.size())
			return true;
		return false;
	}
	
	public boolean hasNext(int numObjs)
	{
		if(newObjs.size() - this.currentObj < numObjs)
			return false;
		return true;
	}
	
	public void previous()
	{
		if((currentObj - 1) >= 0)
			currentObj--;
	}
	
	public void previous(int numObjs)
	{
		if((currentObj - numObjs) >= 0)
			currentObj = (currentObj - numObjs);
	}
	
	public void resetObj()	// reset to the beginning of the ArrayList
	{
		currentObj = 0;
	}
	
	public void reset()	//reset everything
	{
		newObjs = new ArrayList<Object>();
		currentObj = 0;
	}
}
