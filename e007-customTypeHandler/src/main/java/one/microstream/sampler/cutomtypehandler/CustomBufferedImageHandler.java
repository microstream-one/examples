package one.microstream.sampler.cutomtypehandler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import one.microstream.X;
import one.microstream.memory.XMemory;
import one.microstream.persistence.binary.internal.AbstractBinaryHandlerCustomValue;
import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.types.PersistenceLoadHandler;
import one.microstream.persistence.types.PersistenceStoreHandler;

public class CustomBufferedImageHandler extends AbstractBinaryHandlerCustomValue<BufferedImage>
{

	protected static final long LENGTH_CAPACITY = Long.BYTES;
	
	protected static final long
		OFFSET_CAPACITY = 0                                ,
		OFFSET_BYTES    = OFFSET_CAPACITY + LENGTH_CAPACITY;
	
	public CustomBufferedImageHandler()			 
	{
		super(BufferedImage.class,
				CustomFields(
						CustomField(long.class, "capacity"),
						bytes("value")
						));
	}

	@Override
	public boolean hasVaryingPersistedLengthInstances() 
	{
		return false;
	}

	@Override
	public void store(Binary bytes, BufferedImage instance, long objectId, PersistenceStoreHandler handler) 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try ( ImageOutputStream ios = new MemoryCacheImageOutputStream(bos))
		{
			ImageIO.write(instance, "png", ios);						
		} 
		catch (IOException e) 
		{		
			throw new RuntimeException(e);
		}
		
		final long numBytes = (long)bos.toByteArray().length;
		final long contentLength = LENGTH_CAPACITY + numBytes;
		
		final long contentAddress = bytes.storeEntityHeader(contentLength, this.typeId(), objectId);
		bytes.store_long(contentAddress + OFFSET_CAPACITY, numBytes);
		
		XMemory.copyArrayToAddress(bos.toByteArray(), contentAddress + OFFSET_BYTES);
	}

	@Override
	public BufferedImage create(Binary bytes, PersistenceLoadHandler handler)
	{
		long capcity = X.checkArrayRange(bytes.get_long(OFFSET_CAPACITY));		
		byte[] blob = new byte[(int)capcity]; 
				
		bytes.read_bytes(bytes.loadItemEntityContentAddress() + OFFSET_BYTES, blob);
		
		BufferedImage image = null;
			
		try(ByteArrayInputStream bis = new ByteArrayInputStream(blob))
		{		
			image = ImageIO.read(bis);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e); 
		} 

		return image;
	}	

}
