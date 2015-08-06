package game;

import java.util.ArrayList;

public class MenuFormer
{
	private int strPerLine;
	private boolean header;
	private boolean number;
	
	private int[] maxWidths;
	private ArrayList<String[]> inputs = new ArrayList<String[]>();
	
	public MenuFormer(int strPerLine, boolean header, boolean number)
	{
		this.strPerLine = strPerLine;
		this.maxWidths = new int[strPerLine];
		this.header = header;
		this.number = number;
	}
	
	public void addInputs(String[] input)
	{
		String[] strArr = input;
		for(int i = 0; i<strPerLine; i++)
		{
			if(strArr[i].length() > maxWidths[i])
				maxWidths[i] = strArr[i].length();
		}
		inputs.add(strArr);
	}
	
	private int getFullLength()
	{
		return maxWidths[maxWidths.length-1];
	}
	
	private String makeLine(String character, int length)
	{
		String temp = "";
		if(length > 0)
		{
			for(int i = 0; i<length; i++)
			{
				temp += character;
			}
		}
		return temp;
	}
	
	public String format()
	{
		String total = "";
		String partial = "";
		
		int counter = 0;
		
		for(int i = 0; i < maxWidths.length; i++)
		{
			if((i-1) >= 0)
				maxWidths[i] = (maxWidths[i-1] + maxWidths[i] + 2);
			else
				maxWidths[i] = maxWidths[i] + 2;
		}
		
		for(String[] arr : inputs)
		{
			for(int i = 0; i < strPerLine; i++)
			{
				partial += arr[i];
				partial += this.makeLine(" ", (maxWidths[i] - partial.length()));
			}
			
			if(number)	// Line numbering
			{
				if(header && counter == 0)
					total += "   ";
				else
				{
					total += counter + "> ";
				}
			}
			
			total += partial + "\n";
			partial = "";
			counter++;
			
			if(counter == 1 && header)	// inserting line under header
			{
				total += makeLine("-", this.getFullLength() + (strPerLine - 1)) + "\n";
			}
		}
		
		for(int i = 0; i < maxWidths.length; i++)
		{
			maxWidths[i] = 0;	//reset the offsets
		}
		inputs.clear();	// reset inputs
		
		return total;
	}
}
