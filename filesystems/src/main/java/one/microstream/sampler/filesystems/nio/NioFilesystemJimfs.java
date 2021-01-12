
package one.microstream.sampler.filesystems.nio;

import java.nio.file.FileSystem;

import com.google.common.jimfs.Jimfs;

import one.microstream.afs.nio.NioFileSystem;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;
import one.microstream.storage.types.StorageLiveFileProvider;


public class NioFilesystemJimfs
{
	public static void main(
		final String[] args
	)
	{
		// create jimfs filesystem
		final FileSystem             jimfs   = Jimfs.newFileSystem();
		
		// start storage with jimfs path
		final EmbeddedStorageManager storage = EmbeddedStorage.start(jimfs.getPath("storage"));
		storage.shutdown();
		
		// or create file provider with jimsfs filesytem for further configuration
		final NioFileSystem           fileSystem   = NioFileSystem.New(jimfs);
		final StorageLiveFileProvider fileProvider = StorageLiveFileProvider.Builder(fileSystem)
			.setDirectory(fileSystem.ensureDirectoryPath("storage"))
			.createFileProvider()
		;
	}
}
