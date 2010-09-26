package org.adligo.i.adig;

/**
 * this is what a impl will look like
 * 
 * eventually will move this class to the test package/project
 * 
 * @author scott
 *
 */
public class ImplTest extends BaseGInvoker implements I_GInvoker<String, Integer>{

	public ImplTest() {
		super(String.class, Integer.class);
	}
	
	@Override
	public Integer invokeG(String valueObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
