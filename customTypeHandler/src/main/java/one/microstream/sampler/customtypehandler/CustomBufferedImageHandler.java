package one.microstream.sampler.customtypehandler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import one.microstream.persistence.binary.internal.AbstractBinaryHandlerCustomValue;
import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.types.PersistenceLoadHandler;
import one.microstream.persistence.types.PersistenceStoreHandler;

public class CustomBufferedImageHandler extends AbstractBinaryHandlerCustomValue<BufferedImage, byte[]>
{	
	public CustomBufferedImageHandler()
	{
		super(
			BufferedImage.class,
			CustomFields(
				CustomField(long.class, "capacity"),
				bytes("value")
			)
		);
	}

	@Override
	public boolean hasVaryingPersistedLengthInstances()
	{
		return false;
	}

	@Override
	public void store(
		final Binary bytes,
		final BufferedImage instance,
		final long objectId,
		final PersistenceStoreHandler handler
	)
	{				
		bytes.store_bytes(
			this.typeId(), 
			objectId, 
			instanceState(instance)
		);		
	}
	
	@Override
	public BufferedImage create(
		final Binary data,
		final PersistenceLoadHandler handler
	)
	{
		final byte[] blob = binaryState(data);
		
		BufferedImage image = null;
			
		try(ByteArrayInputStream bis = new ByteArrayInputStream(blob))
		{
			image = ImageIO.read(bis);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}

		return image;
	}

	@Override
	public byte[] getValidationStateFromInstance(BufferedImage instance)
	{
		return instanceState(instance);
	}

	@Override
	public byte[] getValidationStateFromBinary(Binary data)
	{
		return binaryState(data);
	}
	
	private static byte[] instanceState(final BufferedImage instance)
	{
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try ( ImageOutputStream ios = new MemoryCacheImageOutputStream(bos))
		{
			ImageIO.write(instance, "png", ios);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		return bos.toByteArray();
	}
	
	private static byte[] binaryState(final Binary data)
	{
		return data.build_bytes();
	}

}
