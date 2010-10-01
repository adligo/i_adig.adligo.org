package org.adligo.i.adig;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;

public class ProxyGInvoker<P,R> extends BaseGProxyInvoker implements I_GInvoker<P,R>{
	private static final Log log = LogFactory.getLog(ProxyGInvoker.class);
	I_GInvoker<P,R> delegate;

	public ProxyGInvoker(Class<?> pClass, Class<?> rClass) {
		super(pClass, rClass);
	}

	public ProxyGInvoker(I_GInvoker<?,?> invoker, String name) {
		super(invoker.getParameterClass(), invoker.getReturnClass());
		super.setName(name);
	}

	
	@Override
	public R invoke(P valueObject) {
		if (delegate == null) {
			RuntimeException e = new RuntimeException("Proxy isn't initalized yet for " + super.getName() + 
					" please add one to your Adig GRegistry (or did you want a checked invoker ?) !");
			log.error(e.getMessage(), e);
			throw e;
		} else {
			try {
				return delegate.invoke(valueObject);
			} catch (RuntimeException x) {
				log.error(x.getMessage(), x);
				throw x;
			}
		}
	}

	public synchronized I_GInvoker<P, R> getDelegate() {
		return delegate;
	}

	public synchronized void setDelegate(I_GInvoker<P, R> delegate) {
		if (log.isDebugEnabled()) {
			log.debug("setting delegate " + delegate + " for ProxyGInvoker " + super.getName());
		}
		this.delegate = delegate;
	}

}
