
package one.microstream.sampler.helloworld;

import java.io.IOException;

import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorageManager;


public class HelloWorld
{
	public static void main(final String[] args) throws IOException
	{
		final Configuration          configuration  = Configuration.LoadIni(
			HelloWorld.class.getResource("/META-INF/microstream/storage.ini"));
		
		final DataRoot               root           = new DataRoot();
		
		final EmbeddedStorageManager storageManager = configuration
			.createEmbeddedStorageFoundation()
			.createEmbeddedStorageManager(root)
			.start();
		
		System.out.println(root);
		
		//set content data to the root element
		root.setContent("Hello there! @" + System.currentTimeMillis());
		
		// Store modified object
		storageManager.store(root);
		
		// Save shutdown
		storageManager.shutdown();
	}
}
