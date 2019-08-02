
package com.jetstreamdb.sampler.helloworld;

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

		root.setName("Hello there! [" + System.currentTimeMillis() + "]");

		// Store modified root
		storageManager.storeRoot();

		// Save shutdown
		storageManager.shutdown();
	}
}
