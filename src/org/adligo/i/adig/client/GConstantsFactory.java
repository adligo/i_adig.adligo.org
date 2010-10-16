package org.adligo.i.adig.client;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.Registry;

/**
 * a wrapper for the InvokerNames.CONSTANTS_FACTORY
 * that is compatibile with the GRegistry
 * @author scott
 *
 */
public class GConstantsFactory extends BaseGInvoker implements I_GInvoker<Class<?>, Object> {
	private static final I_Invoker CONSTANTS = Registry.getInvoker(InvokerNames.CONSTANTS_FACTORY);
	
	public GConstantsFactory() {
		super(Class.class, Object.class);
	}

	@Override
	public Object invoke(Class<?> valueObject) {
		return CONSTANTS.invoke(valueObject);
	}
	
	
}
