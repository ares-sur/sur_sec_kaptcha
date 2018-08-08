package org.ares.app.common.cfg.param;

public class GlobalConfig {

	public static final String ERR_MSG_OF_VALID_CODE="Verify code entered is wrong";
	
	public static final String RESULT_KEY_CODE="code";
	public static final String RESULT_KEY_MSG="message";
	public static final String RESULT_KEY_SUCCESS="success";
	
	public static final String RESULT_MSG_SUCCESS="success";
	public static final Boolean RESULT_BOOL_SUCCESS=Boolean.TRUE;
	public static final Boolean RESULT_BOOL_FAILED=Boolean.FALSE;
	public static final String RESULT_MSG_FAILED="failed";
	
	public static final int RESULT_CODE_SUCCESS=1;
	public static final int RESULT_CODE_FAILED=-1;
	
	public static final String RESULT_MSG_SUCCESS_LOGIN="login success";
	
	/***********************************URL**************************************************************/
	public static final String URL_LOGIN_ERROR="/login?error";
	public static final String URL_LOGIN_SUCCESS="/login_success";
	public static final String URL_LOGIN="/login";
	
	public static final String URL_ALLOW_CSS="/css/**";
	public static final String URL_ALLOW_JS="/js/**";
	public static final String URL_ALLOW_FONTS="/fonts/**";
	public static final String URL_ALLOW_INDEX="/index*";
	
}
