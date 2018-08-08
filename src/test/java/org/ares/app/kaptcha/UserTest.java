package org.ares.app.kaptcha;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserTest {

	public static void main(String[] args) {
		String password = "admin";
		BCryptPasswordEncoder passwordEncoder = null;
		for (int i = 0; i < 5; i++) {
			passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			System.out.println(passwordEncoder.matches(password, hashedPassword));;
			System.out.println(hashedPassword);
		}
	}

}
