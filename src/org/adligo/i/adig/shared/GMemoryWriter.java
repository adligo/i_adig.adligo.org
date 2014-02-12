package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.models.MemoryWriterToken;

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
