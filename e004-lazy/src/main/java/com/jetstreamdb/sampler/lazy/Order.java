
package com.jetstreamdb.sampler.lazy;

import java.util.List;

import one.microstream.persistence.lazy.Lazy;


public class Order
{
	private final String           customer;
	private final Lazy<List<Item>> items;
	private Lazy<String>           handler;
	
	public Order(final String customer, final List<Item> items)
	{
		super();

		this.customer = customer;
		this.items    = Lazy.Reference(items);
	}
	
	public String getCustomer()
	{
		return this.customer;
	}
	
	private static long lazyLoadCount = 0;
	
	public List<Item> getItems()
	{
		// Some verbose info if lazy loading happens
		if(this.items.peek() == null)
		{
			if(lazyLoadCount == 0)
			{
				System.out.println("Lazy loading order's items");
			}
			lazyLoadCount++;
			// System.out.print('.');
			// if(lazyLoadCount % 50 == 0)
			// {
			// System.out.println();
			// }
		}
		
		return this.items.get();
	}
	
	public String getHandler()
	{
		return Lazy.get(this.handler);
	}
	
	public void setHandler(final String handler)
	{
		this.handler = handler != null
			? Lazy.Reference(handler)
			: null;
	}
}
