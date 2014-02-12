package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;

public class GSystemOut extends BaseGInvoker implements I_GInvoker<String, Object> {
	private static final I_Invoker OUT = Registry.getInvoker(InvokerNames.OUT);
	
	public GSystemOut() {
		super(String.class, Object.class);
	}

	/**
	 * note output (return) should be ignored
	 */
	@Override
	public Object invoke(String valueObject) {
		return OUT.invoke(valueObject);
	}
	
}
