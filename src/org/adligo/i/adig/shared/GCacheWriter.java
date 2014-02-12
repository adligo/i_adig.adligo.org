package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.models.CacheWriterToken;

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
