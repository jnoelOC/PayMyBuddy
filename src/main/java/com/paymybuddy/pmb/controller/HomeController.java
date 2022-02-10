package com.paymybuddy.pmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	@GetMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("name", "John"); // set 'John' value for 'name' attribute
		return "index"; // name of the View
	}

	@GetMapping({ "/home" })
	public String home(Model model) {
		return "home_page";
	}

	@GetMapping({ "/transfer" })
	public String transfer(Model model) {
		return "transfer_page";
	}

	@PostMapping({ "/login" })
	public String login(Model model) { // @ModelAttribute UserAccount userAccount) {
		model.addAttribute("name", "John");
		return "login_page";
	}

	@GetMapping({ "/register" })
	public String register(Model model) {
		return "register_page";
	}

	@GetMapping({ "/addConnection" })
	public String addConnection(Model model) {
		return "addConnection_page";
	}

	@GetMapping({ "/user" })
	public String user() {
		return "<h1>Welcome user</h1>";
	}

	@GetMapping({ "/admin" })
	public String admin() {
		return "<h1>Welcome administrator</h1>";
	}
}
