package org.adligo.i.adig.shared;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.models.ConfigRequest;

public class GConfigProvider extends BaseGInvoker implements I_GInvoker<ConfigRequest, Object> {
	private static final I_Invoker CONFIG_PROVIDER = Registry.getInvoker(InvokerNames.CONFIGURATION_PROVIDER);
	
	public GConfigProvider() {
		super(ConfigRequest.class, Object.class);
	}

	public Object invoke(ConfigRequest valueObject) {
		return CONFIG_PROVIDER.invoke(valueObject);
	}
	
	
}
