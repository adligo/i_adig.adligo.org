package org.adligo.i.adig.shared;


public class InvokerDelegateMatchVerifier {
	public static final String PLEASE_FIX_THE_CODE_OR_TALK_TO_YOUR_ARCHITECT = "\nPlease fix the code or talk to your architect.";
	public static final String GET_CHECKED_INVOKER = "getCheckedInvoker(String key, Class param, Class return)\n";
	public static final String GET_INVOKER = "getInvoker(String key, Class param, Class return)\r";
	public static final String AND_THIS_RETURN_CLASS_IS = "\nand this return class is;\n";
	public static final String HAD_A_RETURN_CLASS = "\nhad a return class;\n";
	public static final String AND_THIS_PARAMETER_CLASS_IS = "\nand this parameter class is;\n";
	public static final String HAD_A_PARAM_CLASS = "\nhad a param class;\n";
	public static final String WITH_KEY = "with key;\n";
	public static final String THE_INITAL_CALL_TO = "The inital call to the GRegistry's method\n";

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
			throwParamMismatchError();
		}
		if (!proxyReturn.equals(invokerReturn)) {
			throwReturnMismatchError();
		}
	}

	private void throwParamMismatchError() {
		throw new IllegalStateException(THE_INITAL_CALL_TO + 
				methodName + WITH_KEY +
				key + HAD_A_PARAM_CLASS + proxyParam +
				AND_THIS_PARAMETER_CLASS_IS +
				invokerParam + 
				PLEASE_FIX_THE_CODE_OR_TALK_TO_YOUR_ARCHITECT, marker);
	}

	private void throwReturnMismatchError() {
		throw new IllegalStateException(THE_INITAL_CALL_TO + 
				methodName + WITH_KEY +
				key + HAD_A_RETURN_CLASS + proxyReturn +
				AND_THIS_RETURN_CLASS_IS +
				invokerReturn + 
				PLEASE_FIX_THE_CODE_OR_TALK_TO_YOUR_ARCHITECT, marker);
	}

	public void verifyProxyObtainMatch() {
		if (!proxyParam.equals(getParam)) {
			throwParamMismatchError();
		}
		if (!proxyReturn.equals(getReturn)) {
			throwReturnMismatchError();
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
