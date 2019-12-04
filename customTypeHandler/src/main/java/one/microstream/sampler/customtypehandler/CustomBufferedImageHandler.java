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
import one.microstream.persistence.types.PersistenceObjectIdResolver;
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
				
		bytes.store_bytes(this.typeId(), objectId, bos.toByteArray());
		
	}
	
	@Override
	public BufferedImage create(Binary bytes, PersistenceObjectIdResolver handler)
	{			
		byte[] blob = bytes.build_bytes();
		
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
