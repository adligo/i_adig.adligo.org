package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;

/**
 * a wrapper around the MemoryReader that adds casting for 
 * the I_GInvoker impl
 * for use on GWT and JSE
 * @author scott
 *
 */
public class GMemoryReader extends BaseGInvoker implements I_GInvoker<String, Object> {
	private static final I_Invoker MEMORY_READER = Registry.getInvoker(InvokerNames.MEMORY_READER);

	public GMemoryReader() {
		super (String.class, Object.class); 
	}
	@Override
	public Object invoke(String valueObject) {
		return MEMORY_READER.invoke(valueObject);
	}
	
}
