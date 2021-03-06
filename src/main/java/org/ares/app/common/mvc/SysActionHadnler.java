package org.ares.app.common.mvc;

import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_BOOL_FAILED;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_CODE_FAILED;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_KEY_CODE;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_KEY_MSG;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_KEY_SUCCESS;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ares.app.common.exception.UDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SysActionHadnler {
	
	@ExceptionHandler(InsufficientAuthenticationException.class)//校验码错误的处理
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> handle_iae_exception(HttpServletRequest request, Exception e) {
		log.debug(e.getMessage());
		Map<String, Object> m = new HashMap<>();
		m.put(RESULT_KEY_CODE, RESULT_CODE_FAILED);
		m.put(RESULT_KEY_SUCCESS, RESULT_BOOL_FAILED);
		m.put(RESULT_KEY_MSG, e.getMessage());
		return m;
	}
	
	@ExceptionHandler(UDException.class)//系统级别的异常
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> handle_uc_exception(HttpServletRequest request, Exception e) {
		log.debug(e.getMessage());
		Map<String, Object> m = new HashMap<>();
		m.put(RESULT_KEY_CODE,((UDException)e).getRetCode());
		m.put(RESULT_KEY_SUCCESS, RESULT_BOOL_FAILED);
		m.put(RESULT_KEY_MSG, e.getMessage());
		return m;
	}
	
	final Logger log = LoggerFactory.getLogger(this.getClass());
	
}
