package org.adligo.i.adig.client;

/**
 * a helper class for I_GInvoker implementations
 * for the get Return and Parameter Class methods
 * 
 * @author scott
 *
 */
public abstract class BaseGInvoker {
	private Class<?> returnClass;
	private Class<?> paramClass;
	
	public BaseGInvoker(Class<?> pClass, Class<?> rClass) {
		paramClass = pClass;
		returnClass = rClass;
	}
	
	public Class<?> getReturnClass() {
		return returnClass;
	}
	
	public Class<?> getParameterClass() {
		return paramClass;
	}
}
