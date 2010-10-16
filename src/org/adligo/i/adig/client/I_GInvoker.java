package org.adligo.i.adig.client;

/**
 * this adds Generics to the I_Invoker for jse and gwt platforms
 * (jme doesn't have generics at this time, if it did I could put this in I_Invoker)
 * 
 * @author scott
 *
 * @param <P>
 * @param <R>
 */
public interface I_GInvoker<P,R> extends I_GenericInvoker<P,R> {
	/**
	 * @param a valueObject could be a Integer, String
	 *          I_TemplateParams, List exc
	 *          
	 * @return
	 */
	public R invoke(P valueObject);

}
