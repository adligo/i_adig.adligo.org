package org.adligo.i.adig.client;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.Registry;
import org.adligo.i.adi.client.models.MemoryWriterToken;

/**
 * a wrapper around the MemoryWriter that adds casting for 
 * the I_GInvoker impl
 * for use on GWT and JSE
 * @author scott
 *
 */
public class GMemoryWriter extends BaseGInvoker implements I_GInvoker<MemoryWriterToken, Boolean> {
	private static final I_Invoker MEMORY_WRITER = Registry.getInvoker(InvokerNames.MEMORY_WRITER);

	public GMemoryWriter() {
		super (MemoryWriterToken.class, Boolean.class); 
	}
	
	@Override
	public Boolean invoke(MemoryWriterToken valueObject) {
		return (Boolean) MEMORY_WRITER.invoke(valueObject);
	}
	
}
