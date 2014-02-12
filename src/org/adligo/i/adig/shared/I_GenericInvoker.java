package org.adligo.i.adig.shared;

public interface I_GenericInvoker<P,R> {
	
	/**
	 * should return the class of the return type R
	 * a helper method for the GRegistry so it can assert 
	 * that the request for a I_GInvoker will return what 
	 * it says it will
	 * 
	 * @return
	 */
	public Class<?> getReturnClass();
	
	/**
	 * should return the class of the parameter type P
	 * a helper method for the GRegistry so it can assert 
	 * that the request for a I_GInvoker will accept what 
	 * it says it will
	 * 
	 * @return
	 */
	public Class<?> getParameterClass();
}
