package org.adligo.i.adig.shared;

import org.adligo.i.util.shared.AppenderFactory;


public class InvokerDelegateMatchVerifier {
	public static final String PLEASE_FIX_THE_CODE_OR_TALK_TO_YOUR_ARCHITECT = "Please fix the code or talk to your architect.";
	public static final String GET_CHECKED_INVOKER = "getCheckedInvoker(String key, Class param, Class return)";
	public static final String GET_INVOKER = "getInvoker(String key, Class param, Class return)";
	public static final String AND_THIS_RETURN_CLASS_IS = "and this return class is;";
	public static final String HAD_A_RETURN_CLASS = "had a return class;";
	public static final String AND_THIS_PARAMETER_CLASS_IS = "and this parameter class is;";
	public static final String HAD_A_PARAM_CLASS = "had a param class;";
	public static final String WITH_KEY = " with key;";
	public static final String THE_INITAL_CALL_TO = "The inital call to the GRegistry's method";

	private String key;
	private String methodName;
	private Class<?> invokerParam;
	private Class<?> invokerReturn;
	private Class<?> getParam;
	private Class<?> getReturn;
	private Class<?> proxyParam;
	private Class<?> proxyReturn;
	private InitalProxyMarker marker;
	
	public void verifyInvokerDelegateMatch() {
		if (!proxyParam.equals(invokerParam)) {
			throwParamMismatchError(invokerParam);
		}
		if (!proxyReturn.equals(invokerReturn)) {
			throwReturnMismatchError(invokerReturn);
		}
	}

	private void throwParamMismatchError(Class<?> paramClass) {
		String lineSeperator = AppenderFactory.lineSeperator();
		throw new IllegalStateException(THE_INITAL_CALL_TO + 
				lineSeperator+
				methodName + WITH_KEY + lineSeperator+
				key + lineSeperator +
				HAD_A_PARAM_CLASS + lineSeperator +
				proxyParam + lineSeperator+
				AND_THIS_PARAMETER_CLASS_IS + lineSeperator +
				paramClass + lineSeperator +
				PLEASE_FIX_THE_CODE_OR_TALK_TO_YOUR_ARCHITECT, marker);
	}

	private void throwReturnMismatchError(Class<?> returnClass) {
		String lineSeperator = AppenderFactory.lineSeperator();
		throw new IllegalStateException(THE_INITAL_CALL_TO + lineSeperator+
				methodName + WITH_KEY + lineSeperator +
				key + lineSeperator +
				HAD_A_RETURN_CLASS + lineSeperator +
				proxyReturn + lineSeperator+
				AND_THIS_RETURN_CLASS_IS + lineSeperator +
				returnClass + lineSeperator +
				PLEASE_FIX_THE_CODE_OR_TALK_TO_YOUR_ARCHITECT, marker);
	}

	public void verifyProxyObtainMatch() {
		if (!proxyParam.equals(getParam)) {
			throwParamMismatchError(getParam);
		}
		if (!proxyReturn.equals(getReturn)) {
			throwReturnMismatchError(getReturn);
		}
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setInvoker(I_GInvoker<?,?> p) {
		invokerParam = p.getParameterClass();
		invokerReturn = p.getReturnClass();
	}

	public void setInvoker(I_GCheckedInvoker<?,?> p) {
		invokerParam = p.getParameterClass();
		invokerReturn = p.getReturnClass();
	}
	
	public void setProxy(ProxyGInvoker<?, ?> p) {
		proxyParam = p.getParameterClass();
		proxyReturn = p.getReturnClass();
		marker = p.getInitalMarker();
		methodName = GET_INVOKER;
	}

	public void setProxy(ProxyGCheckedInvoker<?, ?> p) {
		proxyParam = p.getParameterClass();
		proxyReturn = p.getReturnClass();
		marker = p.getInitalMarker();
		methodName = GET_CHECKED_INVOKER;
	}

	public Class<?> getGetParam() {
		return getParam;
	}

	public void setGetParam(Class<?> getParam) {
		this.getParam = getParam;
	}

	public Class<?> getGetReturn() {
		return getReturn;
	}

	public void setGetReturn(Class<?> getReturn) {
		this.getReturn = getReturn;
	}
}
