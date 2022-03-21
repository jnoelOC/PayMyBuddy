package com.paymybuddy.pmb.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

//	@RequestMapping({ "/", "/index", "/home" })
	// @RequestMapping("/*")
	// @RolesAllowed("USER")
	// public String getUser() {
	// return "Welcome, user";
	// }

	@RequestMapping("/admin")
	@RolesAllowed("ADMIN")
	public String getAdmin() {
		return "Welcome, admin";
	}
}
