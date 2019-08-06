
package one.microstream.sampler.lazy;

import java.util.List;


public class DataRoot
{
	private final List<Order> orders;
	
	public DataRoot(final List<Order> orders)
	{
		super();

		this.orders = orders;
	}
	
	public List<Order> orders()
	{
		return this.orders;
	}
}
