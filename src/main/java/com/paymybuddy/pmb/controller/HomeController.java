package com.paymybuddy.pmb.controller;

import java.security.Principal;
import java.util.regex.Pattern;

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

/**
 * 
 * This class realize all the controller operation on Home.
 * 
 * @author jean-noel.chambe
 * 
 */
@Controller
public class HomeController {

//	private static final String regex = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+$";
	private static final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

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
	public String registerUserAccount(RedirectAttributes redirectAttributes, Model model,
			@RequestParam(value = "firstName", name = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", name = "lastName", required = false) String lastName,
			@RequestParam(value = "loginMail", name = "loginMail", required = false) String loginMail,
			@RequestParam(value = "psswrd", name = "psswrd", required = false) String psswrd) throws Exception {

		try {
			if (loginMail.isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Please, fill E-mail at least.");
				return "redirect:/register";
			}

//			if (patternMatches(loginMail, regex)) {
//				redirectAttributes.addFlashAttribute("message", "Wrong loginMail syntax.");
//				return "redirect:/register";
//			}

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

	public static boolean patternMatches(String emailAddress, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(emailAddress).matches();
	}
}
