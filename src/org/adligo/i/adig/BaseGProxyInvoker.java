package org.adligo.i.adig;

public class BaseGProxyInvoker extends BaseGInvoker {
	/**
	 * this is the GRegistrys name or key
	 * for the proxy delegate
	 */
	private String name;
	
	public BaseGProxyInvoker(Class<?> pClass, Class<?> rClass) {
		super(pClass, rClass);
		// TODO Auto-generated constructor stub
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}
	
}
