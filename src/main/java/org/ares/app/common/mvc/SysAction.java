package org.ares.app.common.mvc;

import static org.ares.app.common.cfg.param.GlobalConfig.ERR_MSG_OF_VALID_CODE;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_BOOL_SUCCESS;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_CODE_SUCCESS;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_KEY_CODE;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_KEY_MSG;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_KEY_SUCCESS;
import static org.ares.app.common.cfg.param.GlobalConfig.RESULT_MSG_SUCCESS_LOGIN;
import static org.ares.app.common.cfg.param.GlobalConfig.URL_LOGIN;
import static org.ares.app.common.cfg.param.GlobalConfig.URL_LOGIN_SUCCESS;

import java.util.HashMap;
import java.util.Map;

import org.ares.app.common.exception.AppSysException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysAction {

	@RequestMapping(value = URL_LOGIN, method = RequestMethod.GET)
	@ResponseBody
    public void login_error(@RequestParam(value = "error", required = false) String error) {
		throw new InsufficientAuthenticationException(ERR_MSG_OF_VALID_CODE);
	}
	
	@RequestMapping("/403")
	@ResponseBody
    public void access_403() {
		throw new AppSysException("resource access denied,please try to access again");
	}
	
	@RequestMapping(value = URL_LOGIN_SUCCESS)
	@ResponseBody
    public Map<String,Object> login_success() {
		Map<String,Object> r=new HashMap<>();
		r.put(RESULT_KEY_CODE, RESULT_CODE_SUCCESS);
		r.put(RESULT_KEY_SUCCESS, RESULT_BOOL_SUCCESS);
		r.put(RESULT_KEY_MSG, RESULT_MSG_SUCCESS_LOGIN);
		return r;
	}
	
}
