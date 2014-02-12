package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;

/**
 * a wrapper around the CacheReader that adds casting for 
 * the I_GInvoker impl
 * for use on GWT and JSE
 * @author scott
 *
 */
public class GCacheReader extends BaseGInvoker implements I_GInvoker<String, Object> {
	private static final I_Invoker CACHE_READER = Registry.getInvoker(InvokerNames.CACHE_READER);

	public GCacheReader() {
		super (String.class, Object.class); 
	}
	@Override
	public Object invoke(String valueObject) {
		return CACHE_READER.invoke(valueObject);
	}
	
}
