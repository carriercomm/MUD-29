package game.hierarchy;

public class Pc extends Creature
{
	
	private String target;
	
	public Pc(String fileName) throws Exception
	{
		super(fileName);
		this.target 	= "";		
	}
	
	public String getTarget()
	{
		return target;
	}
	
	public void setTarget(String o)
	{
		this.target = o;
	}
}
