package com.paymybuddy.pmb.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.UserAccountService;

@Controller
public class HomeController {

	@Autowired
	private UserAccountService userAccountService;

	@GetMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("name", "John"); // set 'John' value for 'name' attribute
		return "index"; // name of the View
	}

	@GetMapping({ "/home" })
	public String home(Model model) {
		return "home_page";
	}

	@ModelAttribute("transacs")
	public List<String> getTransacs() {
		List<String> transacs = new ArrayList<>();
		transacs.add("tutu");
		transacs.add("cinema");
		transacs.add("45");
		transacs.add("titi");
		transacs.add("restau");
		transacs.add("150");
		return transacs;
	}

	@ModelAttribute("connections")
	public Set<String> getAllConnections() {

		List<UserAccount> lua = userAccountService.findAllUserAccounts();
		Set<String> connections = new HashSet<>();
		for (Integer i = 0; i < lua.size(); i++) {
			connections.add(lua.get(i).getFirstName());
		}
		return connections;
	}

	@ModelAttribute("userconnections")
	public Set<String> getConnectionsOfOneUser() {

		UserAccount ua = new UserAccount(3L, "jn@gmail.com", "jn", "jnoel", "chambe", 120);

		List<UserAccount> lua = userAccountService.retrieveConxUserAccount(ua);
		// ua.getConnections();
		Set<String> connections = new HashSet<>();
		for (Integer i = 0; i < lua.size(); i++) {
			connections.add(lua.get(i).getFirstName());
		}
		return connections;
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
