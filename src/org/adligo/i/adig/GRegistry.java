package org.adligo.i.adig;

import java.util.HashMap;
import java.util.Map;

import org.adligo.i.adi.client.I_CheckedInvoker;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.I_Map;

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
	private static final Log log = LogFactory.getLog(GRegistry.class);
	
	public static final String GET_CHECKED_INVOKER_METHOD_NAME = "getCheckedInvoker(String key, P param, R returnType)";
	public static final String GET_INVOKER_METHOD_NAME = "getInvoker(String key, P param, R returnType)";
	
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
		I_GCheckedInvoker<?,?> current = checked.get(key);
		if (current != null) {
			if (log.isWarnEnabled()) {
				log.warn("checked invoker with name " + key + " was NOT replaced when calling addCheckedInvoker");
			}
		} else  {
			checked.put(key, invoker);
		}
	}
	
	public static synchronized void removeCheckedInvoker(String key){
		checked.remove(key);
	}
	
	public static synchronized void addOrReplaceCheckedInvoker(String key, I_GCheckedInvoker<?,?> invoker) {
		checked.put(key, invoker);
	}
	
	public static synchronized void addOrReplaceCheckedInvokers(Map<String,I_GCheckedInvoker<?,?>> p ) {
		checked.putAll(p);
	}
	
	/**
	 * see comments in org.adligo.i.adi.client.Registry method with same name
	 * @param <P> the class that the returned I_GInvoker accepts as a parameter
	 * @param <R> the class that the invoke method of the I_GInvoker returns as a result
	 * @param key the logical name of the I_GInvoker
	 * @param param
	 * @param returnType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <P,R> I_GInvoker<P,R> getInvoker(String key, Class<P> param, Class<R> returnType) {
		if (key == null) {
			InvokerRequestException x = new InvokerRequestException();
			x.setChecked(false);
			x.setMethodCall(GET_INVOKER_METHOD_NAME);
			throw x;
		}
		if (param == null) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_INVOKER_METHOD_NAME);
			x.setChecked(false);
			x.setKey(key);
			x.setWas_null_request_param(true);
			throw x;
		}
		if (returnType == null) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_INVOKER_METHOD_NAME);
			x.setChecked(false);
			x.setKey(key);
			x.setWas_null_request_return(true);
			throw x;
		}
		I_GInvoker<?,?> result = invokers.get(key);
		if (result == null) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_INVOKER_METHOD_NAME);
			x.setChecked(false);
			x.setWas_null(true);
			x.setKey(key);
			throw x;
		}
		if (!param.isAssignableFrom(result.getParameterClass())) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_INVOKER_METHOD_NAME);
			x.setChecked(false);
			x.setKey(key);
			x.setActualParam(result.getParameterClass());
			x.setActualReturn(result.getReturnClass());
			x.setRequestedParam(param);
			x.setRequestedReturn(returnType);
			throw x;
		}
		if (!returnType.isAssignableFrom(result.getReturnClass())) {
			InvokerRequestException x = new InvokerRequestException();
			x.setMethodCall(GET_INVOKER_METHOD_NAME);
			x.setChecked(false);
			x.setKey(key);
			x.setActualParam(result.getParameterClass());
			x.setActualReturn(result.getReturnClass());
			x.setRequestedParam(param);
			x.setRequestedReturn(returnType);
			throw x;
		}
		return (I_GInvoker<P,R>) result;
	}

	public static synchronized void addInvoker(String key, I_GInvoker<?,?> invoker){
		I_GInvoker<?,?> current = invokers.get(key);
		if (current != null) {
			if (log.isWarnEnabled()) {
				log.warn("checked invoker with name " + key + " was NOT replaced when calling addInvoker");
			}
		} else  {
			invokers.put(key, invoker);
		}
	}
	
	public static synchronized void removeInvoker(String key){
		invokers.remove(key);
	}
	
	public static synchronized void addOrReplaceInvoker(String key, I_GInvoker<?,?> invoker) {
		invokers.put(key, invoker);
	}
	
	public static synchronized void addOrReplaceInvokers(Map<String,I_GInvoker<?,?>> p ) {
		invokers.putAll(p);
	}
}
