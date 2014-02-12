package org.adligo.i.adig.shared;

import org.adligo.i.adi.client.InvocationException;
import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;

public class ProxyGCheckedInvoker<P,R> extends BaseGProxyInvoker implements I_GCheckedInvoker<P,R>{
	private static final Log log = LogFactory.getLog(ProxyGCheckedInvoker.class);
	private I_GCheckedInvoker<P,R> delegate;
	
	public ProxyGCheckedInvoker(Class<?> pClass, Class<?> rClass) {
		super(pClass, rClass);
		// TODO Auto-generated constructor stub
	}
	
	public ProxyGCheckedInvoker(I_GCheckedInvoker<?,?> invoker, String name) {
		super(invoker.getParameterClass(), invoker.getReturnClass());
		super.setName(name);
	}

	@Override
	public R invoke(P valueObject) throws InvocationException {
		if (delegate == null) {
			RuntimeException e = new RuntimeException("Proxy isn't initalized yet for " + super.getName() + 
					" please add one to your Adig GRegistry (or did you want a (non checked) invoker ?) !");
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

	public synchronized I_GCheckedInvoker<P, R> getDelegate() {
		return delegate;
	}

	public synchronized void setDelegate(I_GCheckedInvoker<P, R> delegate) {
		if (log.isDebugEnabled()) {
			log.debug("setting delegate " + delegate + " for ProxyGCheckedInvoker " + super.getName());
		}
		this.delegate = delegate;
	}

}
