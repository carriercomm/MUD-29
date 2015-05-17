package hierarchy;

public abstract class Portal
{
	private String[] tokens;
	
	private int linkID;
	private String type;
	private String description;
	
	public Portal(String description, int id, String type, String[] tokens)
	{
		this.setDescription(description);
		this.setLinkID(id);
		this.setType(type);
		this.setTokens(tokens);
	}

	public int getLinkID()
	{
		return linkID;
	}

	private void setLinkID(int linkID)
	{
		this.linkID = linkID;
	}

	public String getDescription()
	{
		return description;
	}

	private void setDescription(String description)
	{
		this.description = description;
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
	
	public abstract String Interact(String[] args);
}
