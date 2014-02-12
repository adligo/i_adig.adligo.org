package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;

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
