package org.adligo.i.adig.client;

import java.util.HashMap;
import java.util.Map;

import org.adligo.i.adi.client.I_CheckedInvoker;
import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.ProxyInvoker;
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
	
	private static final Map<String,ProxyGInvoker<?,?>> invokers = 
		new HashMap<String, ProxyGInvoker<?,?>>();
	private static final Map<String, ProxyGCheckedInvoker<?,?>> checked = 
		new HashMap<String, ProxyGCheckedInvoker<?,?>>();
	
	static {
		addInvoker(InvokerNames.CACHE_READER, new GCacheReader());
		addInvoker(InvokerNames.CACHE_WRITER, new GCacheWriter());
		addInvoker(InvokerNames.CACHE_REMOVER, new GCacheRemover());
		
		addInvoker(InvokerNames.MEMORY_READER, new GMemoryReader());
		addInvoker(InvokerNames.MEMORY_WRITER, new GMemoryWriter());
		
		addInvoker(InvokerNames.OUT, new GSystemOut());
		addInvoker(InvokerNames.ERR, new GSystemErr());
		addInvoker(InvokerNames.CLOCK, new GSimpleClock());
		
		addInvoker(InvokerNames.CONFIGURATION_PROVIDER, new GConfigProvider());
		addInvoker(InvokerNames.CONSTANTS_FACTORY, new GConstantsFactory());
	}
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
			ProxyGCheckedInvoker<P,R> newResult = new ProxyGCheckedInvoker<P,R>(param,returnType);
			newResult.setName(key);
			checked.put(key, newResult);
			return (I_GCheckedInvoker<P,R>) newResult;
		}
		//isAssignableFrom is not available on GWT's Class JRE emulation
		// so you probably want to use interfaces for you param and return types
		if (param != result.getParameterClass()) {
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
		//isAssignableFrom is not available on GWT's Class JRE emulation
		// so you probably want to use interfaces for you param and return types
		if (returnType != result.getReturnClass()) {
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

	public static synchronized <P,R> void addCheckedInvoker(String key, I_GCheckedInvoker<P,R> invoker){
		@SuppressWarnings("unchecked")
		ProxyGCheckedInvoker<P,R> pi = (ProxyGCheckedInvoker<P,R>) checked.get(key);
		if (pi == null) {
			pi = new ProxyGCheckedInvoker<P,R>(invoker, key);
			pi.setDelegate(invoker);
			checked.put(key, pi);
			if (log.isInfoEnabled()) {
				log.info("addInvoker " + key + " is now " + checked.get(key));
			}
		} else {
			if (pi.getDelegate() == null) {
				pi.setDelegate(invoker);
				if (log.isInfoEnabled()) {
					log.info("addInvoker " + key + " is now " + checked.get(key));
				}
			} else {
				if (log.isWarnEnabled()) {
					log.warn("invoker with name " + key + " was NOT replaced when calling addInvoker");
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.info("addCheckedInvoker " + key + " is now " + invokers.get(key));
		}
	}

	public static synchronized <P,R> void addOrReplaceCheckedInvoker(String key, I_GCheckedInvoker<P,R> invoker){
		@SuppressWarnings("unchecked")
		ProxyGCheckedInvoker<P,R> pi = (ProxyGCheckedInvoker<P,R>) checked.get(key);
		if (pi == null) {
			addCheckedInvoker(key, invoker);
		} else  {
			pi.setDelegate(invoker);
		}
	}
	
	/**
	 * removes the proxys delegate,
	 * for use in test packages only
	 * @param key
	 */
	protected static synchronized void removeCheckedInvoker(String key){
		ProxyGCheckedInvoker<?, ?> pInvoker = checked.get(key);
		pInvoker.setDelegate(null);
	}
	
	/**
	 * deletes the proxy only for use in the adlg_tests package
	 * @param key
	 */
	protected static synchronized void deleteCheckedInvoker(String key){
		log.warn("deleting checked proxy " + key);
		checked.remove(key);
	} 
	/*
	 * Note I could use introspection to do this but 
	 * will not work on GWT
	 * How to do???
	 * @todo
	 * 
	public static synchronized void addOrReplaceCheckedInvoker(String key, I_GCheckedInvoker<?,?> invoker) {
		checked.put(key, invoker);
	}
	
	public static synchronized void addOrReplaceCheckedInvokers(Map<String,I_GCheckedInvoker<?,?>> p ) {
		checked.putAll(p);
	}
	*/
	
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
			ProxyGInvoker<P,R> newResult = new ProxyGInvoker<P,R>(param,returnType);
			newResult.setName(key);
			invokers.put(key, newResult);
			return (I_GInvoker<P,R>) newResult;
		}
		//isAssignableFrom is not available on GWT's Class JRE emulation
		// so you probably want to use interfaces for you param and return types
		if (param != result.getParameterClass()) {
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
		//isAssignableFrom is not available on GWT's Class JRE emulation
		// so you probably want to use interfaces for you param and return types
		if (returnType != result.getReturnClass()) {
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

	public static synchronized <P,R> void addInvoker(String key, I_GInvoker<P,R> invoker){
		@SuppressWarnings("unchecked")
		ProxyGInvoker<P,R> pi = (ProxyGInvoker<P,R>) invokers.get(key);
		if (pi == null) {
			pi = new ProxyGInvoker<P,R>(invoker, key);
			pi.setDelegate(invoker);
			invokers.put(key, pi);
			if (log.isInfoEnabled()) {
				log.info("addInvoker " + key + " is now " + invokers.get(key));
			}
		} else {
			if (pi.getDelegate() == null) {
				pi.setDelegate(invoker);
				if (log.isInfoEnabled()) {
					log.info("addInvoker " + key + " is now " + invokers.get(key));
				}
			} else {
				if (log.isWarnEnabled()) {
					log.warn("invoker with name " + key + " was NOT replaced when calling addInvoker");
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.info("addInvoker " + key + " is now " + invokers.get(key));
		}
	}
	
	/**
	 * removes the delegate of proxy with key
	 * for use in test packages only
	 * @param key
	 */
	protected static synchronized void removeInvoker(String key){
		ProxyGInvoker<?,?> pInvoker = invokers.get(key);
		pInvoker.setDelegate(null);
	}
	
	
	/**
	 * for use in adig_tests only
	 * @param key
	 */
	protected static synchronized void deleteInvoker(String key){
		log.warn("deleting proxy " + key);
		invokers.remove(key);
	} 
	
	public static synchronized <P,R> void addOrReplaceInvoker(String key, I_GInvoker<P,R> invoker){
		@SuppressWarnings("unchecked")
		ProxyGInvoker<P,R> pi = (ProxyGInvoker<P,R>) invokers.get(key);
		if (pi == null) {
			addInvoker(key, invoker);
		} else {
			pi.setDelegate(invoker);
		}
	}
	/*
	 * Note I could use introspection to do this but 
	 * will not work on GWT
	 * How to do???
	 * @todo
	 * 
	public static synchronized void addOrReplaceInvoker(String key, I_GInvoker<?,?> invoker) {
		invokers.put(key, invoker);
	}
	
	public static synchronized void addOrReplaceInvokers(Map<String,I_GInvoker<?,?>> p ) {
		invokers.putAll(p);
	}
	*/
}
