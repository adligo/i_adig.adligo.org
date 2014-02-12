package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;

public class GSystemErr extends BaseGInvoker implements I_GInvoker<String, Object> {
	private static final I_Invoker ERR = Registry.getInvoker(InvokerNames.ERR);
	
	public GSystemErr() {
		super(String.class, Object.class);
	}

	/**
	 * note output (return) should be ignored
	 */
	@Override
	public Object invoke(String valueObject) {
		return ERR.invoke(valueObject);
	}
	
}
