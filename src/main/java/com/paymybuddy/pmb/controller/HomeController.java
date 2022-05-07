package com.paymybuddy.pmb.controller;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.impl.UserAccountService;

@Controller
public class HomeController {

	public static final Logger logger = LogManager.getLogger(HomeController.class);

	@Autowired
	private UserAccountService userAccountService;

	@GetMapping({ "/", "/index" })
	public String mainIndex() {

		return "index";
	}

	@GetMapping({ "/home" })
	public String home(Principal principal, Model model) {

		UserAccount user = userAccountService.findByLoginMail(principal.getName());
		if (user.getLoginMail().equalsIgnoreCase("admin@gmail.com")) {
			model.addAttribute("sold", user.getSolde());
		} else {
			model.addAttribute("sold", user.getSolde().intValue());
		}

		return "home_page";
	}

	@GetMapping({ "/login" })
	public String login() {
		return "login_page";
	}

	@GetMapping({ "/logout" })
	public String logout() {
		return "index";
	}

	@GetMapping({ "/register" })
	public String register() {
		return "register_page";
	}

	@PostMapping({ "/register" })
	public String registerUserAccount(RedirectAttributes redirectAttributes,
			@RequestParam(value = "firstName", name = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", name = "lastName", required = false) String lastName,
			@RequestParam(value = "loginMail", name = "loginMail", required = false) String loginMail,
			@RequestParam(value = "psswrd", name = "psswrd", required = false) String psswrd) throws Exception {

		try {
			UserAccount registration = userAccountService.registerNewUserAccount(null, loginMail, psswrd, firstName,
					lastName, 0D);

			if (registration == null) {
				redirectAttributes.addAttribute("attribute", "index");
			}
		} catch (Exception ex) {
			System.out.println("Duplicated user : " + ex.getMessage());
		}
		return "redirect:/transfer";
	}
}
