package one.microstream.sampler.layeredentities;

import one.microstream.entity.Entity;

public interface Human extends Beeing<Human>, Named, Entity
{
	public Address address();
}
