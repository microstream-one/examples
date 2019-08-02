
package com.jetstreamdb.sampler.helloworld;

public class DataRoot
{
	private String name;

	public DataRoot()
	{
		super();
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Root: " + this.name;
	}
}
