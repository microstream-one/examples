
package one.microstream.sampler.lazy;


public class Item
{
	private final String	name;
	private final int		amount;
	private final double	price;


	public Item(final String name, final int amount, final double price)
	{
		super();
		this.name = name;
		this.amount = amount;
		this.price = price;
	}


	public String getName()
	{
		return this.name;
	}


	public int getAmount()
	{
		return this.amount;
	}


	public double getPrice()
	{
		return this.price;
	}
}
