package org.adligo.i.adig.shared;

import java.util.HashMap;
import java.util.Map;

import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;

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
	
	public static final String GET_CHECKED_INVOKER = "getCheckedInvoker";
	public static final String THE_RETURN_TYPE = "The returnType passed to ";
	public static final String THE_PARAM_PASSED_TO = "The param passed to ";
	public static final String GET_INVOKER = "getInvoker";
	public static final String END_MESSAGE = "(String key, Class param, Class returnType) was null.";
	public static final String THE_KEY_PASSED_TO = "The key passed to ";

	private static final Log log = LogFactory.getLog(GRegistry.class);
	
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
			throw new IllegalStateException(THE_KEY_PASSED_TO + 
					GET_CHECKED_INVOKER + END_MESSAGE);
		}
		if (param == null) {
			throw new IllegalStateException(THE_PARAM_PASSED_TO + 
					GET_CHECKED_INVOKER + END_MESSAGE);
		}
		if (returnType == null) {
			throw new IllegalStateException(THE_RETURN_TYPE + 
					GET_CHECKED_INVOKER + END_MESSAGE);
		}
		ProxyGCheckedInvoker<?,?> result = checked.get(key);
		if (result == null) {
			ProxyGCheckedInvoker<P,R> newResult = new ProxyGCheckedInvoker<P,R>(param,returnType);
			newResult.setName(key);
			checked.put(key, newResult);
			return (I_GCheckedInvoker<P,R>) newResult;
		}
		
		InvokerDelegateMatchVerifier verifier = new InvokerDelegateMatchVerifier();
		verifier.setKey(key);
		verifier.setProxy(result);
		verifier.setGetParam(param);
		verifier.setGetReturn(returnType);
		verifier.verifyProxyObtainMatch();
		return (I_GCheckedInvoker<P,R>) result;
	}

	public static synchronized <P,R> void addCheckedInvoker(String key, I_GCheckedInvoker<P,R> invoker){
		@SuppressWarnings("unchecked")
		ProxyGCheckedInvoker<?,?> pi = (ProxyGCheckedInvoker<?,?>) checked.get(key);
		if (pi == null) {
			ProxyGCheckedInvoker<P,R> newPi = new ProxyGCheckedInvoker<P,R>(invoker, key);
			newPi.setDelegate(invoker);
			checked.put(key, newPi);
			if (log.isInfoEnabled()) {
				log.info("addCheckedInvoker " + key + " is now " + checked.get(key));
			}
		} else {
			if (pi.getDelegate() == null) {
				InvokerDelegateMatchVerifier verifier = new InvokerDelegateMatchVerifier();
				verifier.setKey(key);
				verifier.setProxy(pi);
				verifier.setInvoker(invoker);
				verifier.verifyInvokerDelegateMatch();
				
				@SuppressWarnings("unchecked")
				ProxyGCheckedInvoker<P,R> casted = (ProxyGCheckedInvoker<P,R>) pi;
				casted.setDelegate(invoker);
				
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
			InvokerDelegateMatchVerifier verifier = new InvokerDelegateMatchVerifier();
			verifier.setKey(key);
			verifier.setProxy(pi);
			verifier.setInvoker(invoker);
			verifier.verifyInvokerDelegateMatch();
			ProxyGCheckedInvoker<P,R> casted = (ProxyGCheckedInvoker<P,R>) pi;
			casted.setDelegate(invoker);
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
			throw new IllegalStateException(THE_KEY_PASSED_TO + 
					GET_INVOKER + END_MESSAGE);
		}
		if (param == null) {
			throw new IllegalStateException(THE_PARAM_PASSED_TO + 
					GET_INVOKER + END_MESSAGE);
		}
		if (returnType == null) {
			throw new IllegalStateException(THE_RETURN_TYPE + 
					GET_INVOKER + END_MESSAGE);
		}
		ProxyGInvoker<?,?> result = invokers.get(key);
		if (result == null) {
			ProxyGInvoker<P,R> newResult = new ProxyGInvoker<P,R>(param,returnType);
			newResult.setName(key);
			invokers.put(key, newResult);
			return (I_GInvoker<P,R>) newResult;
		}
		InvokerDelegateMatchVerifier verifier = new InvokerDelegateMatchVerifier();
		verifier.setKey(key);
		verifier.setProxy(result);
		verifier.setGetParam(param);
		verifier.setGetReturn(returnType);
		verifier.verifyProxyObtainMatch();
		return (I_GInvoker<P,R>) result;
	}

	public static synchronized <P,R> void addInvoker(String key, I_GInvoker<P,R> invoker){
		@SuppressWarnings("unchecked")
		ProxyGInvoker<?,?> pi = (ProxyGInvoker<?,?>) invokers.get(key);
		if (pi == null) {
			ProxyGInvoker<P,R> newPi = new ProxyGInvoker<P,R>(invoker, key);
			newPi.setDelegate(invoker);
			invokers.put(key, newPi);
			if (log.isInfoEnabled()) {
				log.info("addInvoker " + key + " is now " + invokers.get(key));
			}
		} else {
			if (pi.getDelegate() == null) {
				
				InvokerDelegateMatchVerifier verifier = new InvokerDelegateMatchVerifier();
				verifier.setKey(key);
				verifier.setProxy(pi);
				verifier.setInvoker(invoker);
				verifier.verifyInvokerDelegateMatch();
				
				@SuppressWarnings("unchecked")
				ProxyGInvoker<P,R> casted = (ProxyGInvoker<P,R>) pi;
				casted.setDelegate(invoker);
				
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
		ProxyGInvoker<?,?> pi = (ProxyGInvoker<?,?>) invokers.get(key);
		if (pi == null) {
			addInvoker(key, invoker);
		} else {
			InvokerDelegateMatchVerifier verifier = new InvokerDelegateMatchVerifier();
			verifier.setKey(key);
			verifier.setProxy(pi);
			verifier.setInvoker(invoker);
			verifier.verifyInvokerDelegateMatch();
			
			@SuppressWarnings("unchecked")
			ProxyGInvoker<P,R> casted = (ProxyGInvoker<P,R>) pi;
			casted.setDelegate(invoker);
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
