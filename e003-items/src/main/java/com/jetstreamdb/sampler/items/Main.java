
package com.jetstreamdb.sampler.items;

import java.io.Console;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;


public class Main
{
	public static void main(final String[] args)
	{
		new Main().start();
	}

	private final EmbeddedStorageManager storageManager;

	public Main()
	{
		this.storageManager = EmbeddedStorage.start(new DataRoot(), new File("data"));
	}

	private void start()
	{
		usage();
		command();
	}

	private DataRoot root()
	{
		return (DataRoot)this.storageManager.root();
	}

	private void usage()
	{
		System.out.println("Usage:");
		System.out.println("a <title> - adds an item");
		System.out.println("r <index> - removes an item");
		System.out.println("s <term> - searches for items");
		System.out.println("l - list all items");
		System.out.println("q - quit");
	}

	private void command()
	{
		final String  command;

		final Console console = System.console();
		if(console != null)
		{
			command = console.readLine().trim();
		}
		else
		{
			command = new Scanner(System.in).nextLine();
		}

		if(command.startsWith("a "))
		{
			final String title = command.substring(2).trim();
			if(title.isEmpty())
			{
				usage();
			}
			else
			{
				final List<Item> items = this.root().items();
				items.add(new Item(title));
				this.storageManager.store(items);
				System.out.println("Item added");
			}
			command();
		}
		else if(command.startsWith("r "))
		{
			try
			{
				final int        index = Integer.parseInt(command.substring(2).trim());
				final List<Item> items = this.root().items();
				if(index < 0 || index >= items.size())
				{
					System.out.println("Invalid index");
				}
				else
				{
					items.remove(index);
					this.storageManager.store(items);
					System.out.println("Item removed");
				}
			}
			catch(final NumberFormatException e)
			{
				usage();
			}
			command();
		}
		else if(command.startsWith("s "))
		{
			final String term = command.substring(2).trim().toLowerCase();
			if(term.isEmpty())
			{
				usage();
			}
			else
			{
				final List<Item> items = this.root().items().stream()
					.filter(item -> item.getTitle().toLowerCase().startsWith(term))
					.collect(Collectors.toList());
				print(items);
			}
			command();
		}
		else if(command.equals("l"))
		{
			print(this.root().items());
			command();
		}
		else if(command.equals("q"))
		{
			this.storageManager.shutdown();
		}
		else
		{
			usage();
			command();
		}
	}

	private void print(final List<Item> items)
	{
		if(items.isEmpty())
		{
			System.out.println("No items found");
		}
		else
		{
			System.out.println("Found " + items.size() + " item(s):");
			for(int i = 0, c = items.size(); i < c; i++)
			{
				System.out.println(i + " " + items.get(i));
			}
		}
	}
}
