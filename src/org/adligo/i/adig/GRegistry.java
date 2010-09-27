package org.adligo.i.adig;

import java.util.HashMap;
import java.util.Map;

import org.adligo.i.adi.client.I_CheckedInvoker;

/**
 * This class is used to house swappable parts of code
 * for unit testing and extensibility of code.  It serves the same purpose as
 * the Registry in the adi package, but adds generics and standard java
 * Collections which arn't available on jme.
 * 
 * @author scott
 *
 */
public class GRegistry {
	public static final String GET_CHECKED_INVOKER_METHOD_NAME = "getCheckedInvoker(String key, P param, R returnType)";
	
	private static final Map<String,I_GInvoker<?,?>> invokers = 
		new HashMap<String, I_GInvoker<?,?>>();
	private static final Map<String,I_GCheckedInvoker<?,?>> checked = 
		new HashMap<String, I_GCheckedInvoker<?,?>>();
	
	/**
	 * see comments in org.adligo.i.adi.client.Registry method with same name
	 * @param <P> the class that the returned I_GCheckedInvoker accepts as a parameter
	 * @param <R> the class that the invoke method of the I_GCheckedInvoker returns as a result
	 * @param key the logical name of the I_GCheckedInvoker
	 * @param param
	 * @param returnType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <P,R> I_GCheckedInvoker<P,R> getCheckedInvoker(String key, Class<P> param, Class<R> returnType) {
		if (key == null) {
			InvokerRequestException x = new InvokerRequestException();
			x.setChecked(true);
			x.setMethodCall(GET_CHECKED_INVOKER_METHOD_NAME);
			throw x;
		}
		if (param == null) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_CHECKED_INVOKER_METHOD_NAME);
			x.setChecked(true);
			x.setKey(key);
			x.setWas_null_request_param(true);
			throw x;
		}
		if (returnType == null) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_CHECKED_INVOKER_METHOD_NAME);
			x.setChecked(true);
			x.setKey(key);
			x.setWas_null_request_return(true);
			throw x;
		}
		I_GCheckedInvoker<?,?> result = checked.get(key);
		if (result == null) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_CHECKED_INVOKER_METHOD_NAME);
			x.setChecked(true);
			x.setWas_null(true);
			x.setKey(key);
			throw x;
		}
		if (!param.isAssignableFrom(result.getParameterClass())) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_CHECKED_INVOKER_METHOD_NAME);
			x.setChecked(true);
			x.setKey(key);
			x.setActualParam(result.getParameterClass());
			x.setActualReturn(result.getReturnClass());
			x.setRequestedParam(param);
			x.setRequestedReturn(returnType);
			throw x;
		}
		if (!returnType.isAssignableFrom(result.getReturnClass())) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_CHECKED_INVOKER_METHOD_NAME);
			x.setChecked(true);
			x.setKey(key);
			x.setActualParam(result.getParameterClass());
			x.setActualReturn(result.getReturnClass());
			x.setRequestedParam(param);
			x.setRequestedReturn(returnType);
			throw x;
		}
		return (I_GCheckedInvoker<P,R>) result;
	}

	public static synchronized void addCheckedInvoker(String key, I_GCheckedInvoker<?,?> invoker){
		checked.put(key, invoker);
	}
	
	public static synchronized void removeCheckedInvoker(String key){
		checked.remove(key);
	}
}
