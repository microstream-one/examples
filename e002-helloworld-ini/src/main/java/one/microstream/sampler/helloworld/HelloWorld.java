
package one.microstream.sampler.helloworld;

import java.io.IOException;

import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorageManager;


public class HelloWorld
{
	public static void main(final String[] args) throws IOException
	{
		// Application-specific root instance
		final DataRoot root = new DataRoot();
		
		// configuring the database via .ini file instead of API. Here the directory and the thread count.
		final Configuration configuration = Configuration.LoadIni(
			HelloWorld.class.getResource("/META-INF/microstream/storage.ini"));
				
		final EmbeddedStorageManager storageManager = configuration
			.createEmbeddedStorageFoundation()
			.createEmbeddedStorageManager(root)
			.start();
				
		// Set content data to the root element, including a timestamp to visualize changes on the next execution.
		root.setContent("Hello World! @" + System.currentTimeMillis());

		// Store the modified root
		storageManager.storeRoot();

		// Shutdown is optional as the storage concept is inherently crash-safe
//		storageManager.shutdown();
		System.exit(0);
	}
}
