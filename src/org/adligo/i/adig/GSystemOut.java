package org.adligo.i.adig;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.Registry;

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
