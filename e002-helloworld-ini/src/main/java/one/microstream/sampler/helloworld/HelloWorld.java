
package one.microstream.sampler.helloworld;

import java.io.IOException;
import java.io.InputStream;

import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.configuration.ConfigurationLoader;
import one.microstream.storage.configuration.ConfigurationParser;
import one.microstream.storage.types.EmbeddedStorageManager;


public class HelloWorld
{
	public static void main(final String[] args) throws IOException
	{
		String configurationData;
		try(InputStream inputStream = HelloWorld.class
			.getResourceAsStream("/META-INF/microstream/storage.ini"))
		{
			configurationData = ConfigurationLoader.FromInputStream(inputStream).loadConfiguration();
		}

		final Configuration          configuration  = ConfigurationParser.Ini().parse(configurationData);

		final DataRoot               root           = new DataRoot();

		final EmbeddedStorageManager storageManager = configuration
			.createEmbeddedStorageFoundation()
			.createEmbeddedStorageManager(root)
			.start();

		System.out.println(root);

		root.setName("Hello there! [" + System.currentTimeMillis() + "]");

		// Store modified object
		storageManager.store(root);

		// Save shutdown
		storageManager.shutdown();
	}
}
