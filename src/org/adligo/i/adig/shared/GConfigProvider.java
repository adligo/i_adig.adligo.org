package org.adligo.i.adig.shared;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.Registry;
import org.adligo.i.adi.client.models.ConfigRequest;

public class GConfigProvider extends BaseGInvoker implements I_GInvoker<ConfigRequest, Object> {
	private static final I_Invoker CONFIG_PROVIDER = Registry.getInvoker(InvokerNames.CONFIGURATION_PROVIDER);
	
	public GConfigProvider() {
		super(ConfigRequest.class, Object.class);
	}

	public Object invoke(ConfigRequest valueObject) {
		return CONFIG_PROVIDER.invoke(valueObject);
	}
	
	
}
