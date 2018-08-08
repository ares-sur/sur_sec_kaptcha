package org.ares.app.kaptcha.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloAction {
	
	@RequestMapping("/home")
	public String index() {
		return "index";
	}
}
