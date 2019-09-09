
package one.microstream.sampler.items;

import java.time.LocalDateTime;


public class Item
{
	private String              title;
	private final LocalDateTime createdAt;
	
	public Item(final String title)
	{
		super();

		this.title     = title;
		this.createdAt = LocalDateTime.now();
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(final String title)
	{
		this.title = title;
	}
	
	public LocalDateTime getCreatedAt()
	{
		return this.createdAt;
	}
	
	@Override
	public String toString()
	{
		return this.title + " created at " + this.createdAt;
	}
}
