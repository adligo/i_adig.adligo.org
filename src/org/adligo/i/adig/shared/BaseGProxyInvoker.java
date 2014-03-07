package org.adligo.i.adig.shared;

public class BaseGProxyInvoker extends BaseGInvoker {
	/**
	 * this is the GRegistrys name or key
	 * for the proxy delegate
	 */
	private String name;
	private InitalProxyMarker initalMarker;
	
	public BaseGProxyInvoker(Class<?> pClass, Class<?> rClass) {
		super(pClass, rClass);
		initalMarker = new InitalProxyMarker();
		initalMarker.fillInStackTrace();
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}

	public InitalProxyMarker getInitalMarker() {
		return initalMarker;
	}
	
}
