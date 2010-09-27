package org.adligo.i.adig;

import org.adligo.i.adi.client.InvocationException;

public interface I_GCheckedInvoker<P,R> extends I_GenericInvoker<P,R> {
	/**
	 * @param a valueObject could be a Integer, String
	 *          I_TemplateParams, List exc
	 *          
	 * @return
	 */
	public R invoke(P valueObject) throws InvocationException;
}
