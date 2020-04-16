package one.microstream.sampler.layeredentities;

import one.microstream.entity.Entity;

public interface Animal extends Beeing<Animal>, Entity
{
	public String species();
}
