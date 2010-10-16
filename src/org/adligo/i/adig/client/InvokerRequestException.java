package org.adligo.i.adig.client;

/**
 * This class encapsulates errors in requests for I_GInvokers and I_GCheckedInvokers
 * @author scott
 *
 */
public class InvokerRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;
	private boolean was_null = false;
	private boolean was_null_request_param = false;
	private boolean was_null_request_return = false;
	private boolean checked = false;
	private Class<?> requestedParam;
	private Class<?> requestedReturn;
	private Class<?> actualParam;
	private Class<?> actualReturn;
	private String methodCall;
	
	public String getMethodCall() {
		return methodCall;
	}

	public void setMethodCall(String methodCall) {
		this.methodCall = methodCall;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Class<?> getRequestedParam() {
		return requestedParam;
	}

	public void setRequestedParam(Class<?> requestedParam) {
		this.requestedParam = requestedParam;
	}

	public Class<?> getRequestedReturn() {
		return requestedReturn;
	}

	public void setRequestedReturn(Class<?> requestedReturn) {
		this.requestedReturn = requestedReturn;
	}

	public Class<?> getActualParam() {
		return actualParam;
	}

	public void setActualParam(Class<?> actualParam) {
		this.actualParam = actualParam;
	}

	public Class<?> getActualReturn() {
		return actualReturn;
	}

	public void setActualReturn(Class<?> actualReturn) {
		this.actualReturn = actualReturn;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isWas_null() {
		return was_null;
	}

	public void setWas_null(boolean was_null) {
		this.was_null = was_null;
	}

	public boolean isWas_null_request_param() {
		return was_null_request_param;
	}

	public void setWas_null_request_param(boolean was_null_request_param) {
		this.was_null_request_param = was_null_request_param;
	}

	public boolean isWas_null_request_return() {
		return was_null_request_return;
	}

	public void setWas_null_request_return(boolean was_null_request_return) {
		this.was_null_request_return = was_null_request_return;
	}
	
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("Your call to the GRegistry's method ");
		sb.append(methodCall);
		if (key == null) {
			sb.append(" passed a null for the key parameter.");
		} else if (was_null_request_param) {
			sb.append(" passed a null for the param parameter.");
		} else if (was_null_request_return) {
			sb.append(" passed a null for the returnType parameter.");
		} else {
			sb.append(" obtained a ");
			if (was_null) {
				sb.append(" null for key '");
				sb.append(key);
				sb.append("', please put something in the GRegistry for this key.");
			} else {
				if (checked) {
					appendChecked(sb, "I_GCheckedInvoker");
				} else {
					appendChecked(sb, "I_GInvoker");
				}
			}
		}
		return sb.toString();
	}

	private void appendChecked(StringBuilder sb, String invokerName) {
		sb.append(invokerName);
		sb.append("<");
		sb.append(actualParam.getName());
		sb.append(",");
		sb.append(actualReturn.getName());
		sb.append("> which is not assignable to ");
		sb.append(invokerName);
		sb.append("<");
		sb.append(requestedParam.getName());
		sb.append(",");
		sb.append(requestedReturn.getName());
		sb.append(", please fix your coding error or talk to your architect. ");
	}
}
