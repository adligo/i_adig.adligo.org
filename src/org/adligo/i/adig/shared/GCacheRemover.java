package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.models.CacheRemoverToken;


/**
 * a wrapper around the CacheRemover that adds casting for 
 * the I_GInvoker impl
 * for use on GWT and JSE
 * @author scott
 *
 */
public class GCacheRemover extends BaseGInvoker implements I_GInvoker<CacheRemoverToken, Boolean>{
	private static final I_Invoker CACHE_REMOVER = Registry.getInvoker(InvokerNames.CACHE_REMOVER);
	
	public GCacheRemover() {
		super(CacheRemoverToken.class, Boolean.class);
	}

	@Override
	public Boolean invoke(CacheRemoverToken valueObject) {
		return (Boolean) CACHE_REMOVER.invoke(valueObject);
	}
	
	
}
