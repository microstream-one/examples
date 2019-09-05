
package one.microstream.sampler.helloworld;

import java.io.File;

import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;


public class HelloWorld
{
	public static void main(final String[] args)
	{
		// Root instance
		final DataRoot               root           = new DataRoot();

		// Init storage manager
		final EmbeddedStorageManager storageManager = EmbeddedStorage.start(root, new File("data"));

		System.out.println(root);

		//set content data to the root element
		root.setContent("Hello World! @" + System.currentTimeMillis());

		// Store modified root
		storageManager.storeRoot();

		// Save shutdown
		storageManager.shutdown();
	}
}
