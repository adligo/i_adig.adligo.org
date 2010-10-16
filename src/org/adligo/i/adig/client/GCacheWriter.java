package org.adligo.i.adig.client;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.Registry;
import org.adligo.i.adi.client.models.CacheWriterToken;

/**
 * a wrapper of the CacheWriter that adds the generics (I_GInvoker)
 * for use on GWT and JSE
 * @author scott
 *
 */
public class GCacheWriter extends BaseGInvoker implements I_GInvoker<CacheWriterToken, Boolean> {
	public static final I_Invoker CACHE_WRITER = Registry.getInvoker(InvokerNames.CACHE_WRITER);
	
	public GCacheWriter () {
		super(CacheWriterToken.class, Boolean.class);
	}

	@Override
	public Boolean invoke(CacheWriterToken valueObject) {
		return (Boolean) CACHE_WRITER.invoke(valueObject);
	}
}
