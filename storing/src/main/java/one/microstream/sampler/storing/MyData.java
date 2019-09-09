package one.microstream.sampler.storing;

public class MyData 
{
	private String name;
	private int    intValue;
	
	public MyData(String content)
	{
		super();
		this.name = content;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
		
	public int getIntegerValue() 
	{
		return intValue;
	}

	public void setIntValue(int integerValue) 
	{
		this.intValue = integerValue;
	}

	public String toString()
	{
		return name + " value: " + intValue;
	}
	
	
	
}
