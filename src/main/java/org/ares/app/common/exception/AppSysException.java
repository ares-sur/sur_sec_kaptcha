package org.ares.app.common.exception;

import static org.ares.app.common.cfg.param.GlobalConfig.RETCODE_SYS_PROCESS_ERROR;

/**
 * App Sys Exception
 * @author ly
 */
@SuppressWarnings("serial")
public class AppSysException extends UDException {

	public AppSysException() {
		super();
	}

	public AppSysException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppSysException(String message) {
		super(message);
	}

	public AppSysException(Throwable cause) {
		super(cause);
	}
	
	public int getRetCode() {
		return RETCODE;
	}

	final static int RETCODE=RETCODE_SYS_PROCESS_ERROR;
	
}
