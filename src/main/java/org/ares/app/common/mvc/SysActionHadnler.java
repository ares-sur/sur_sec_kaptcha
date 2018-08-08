package org.ares.app.common.mvc;

import static org.ares.app.common.cfg.param.GlobalConfig.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	public Map<String, Object> handleException(HttpServletRequest request, Exception e) {
		log.debug(e.getMessage());
		Map<String, Object> m = new HashMap<>();
		m.put(RESULT_KEY_CODE, RESULT_CODE_FAILED);
		m.put(RESULT_KEY_SUCCESS, RESULT_BOOL_FAILED);
		m.put(RESULT_KEY_MSG, e.getMessage());
		return m;
	}
	
	final Logger log = LoggerFactory.getLogger(this.getClass());
	
}
