package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;

/**
 * a wrapper around the I_Invoker clock that
 * has a cast to a Long for JSE and GWT
 * @author scott
 *
 */
public class GSimpleClock extends BaseGInvoker implements I_GInvoker<Object, Long> {
	private static final I_Invoker CLOCK = Registry.getInvoker(InvokerNames.CLOCK);
	
	public GSimpleClock() {
		super(Object.class, Long.class);
	}

	@Override
	public Long invoke(Object valueObject) {
		return (Long) CLOCK.invoke(valueObject);
	}
	
}
