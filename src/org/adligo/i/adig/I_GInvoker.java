package org.adligo.i.adig;

/**
 * this adds Generics to the I_Invoker for jse and gwt platforms
 * (jme doesn't have generics at this time, if it did I could put this in I_Invoker)
 * 
 * @author scott
 *
 * @param <P>
 * @param <R>
 */
public interface I_GInvoker<P,R> {
	/**
	 * @param a valueObject could be a Integer, String
	 *          I_TemplateParams, List exc
	 * @return
	 */
	public R invokeG(P valueObject);
	
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
