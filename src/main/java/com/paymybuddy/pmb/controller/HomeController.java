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
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.pmb.model.Transac;
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
	public List<Transac> getTransacs() {
		List<Transac> transacs = new ArrayList<>();
		transacs.add(new Transac(1L, "restau", 100, "jn@gmail.com", "cs@gmail.com"));
		transacs.add(new Transac(2L, "cinema", 40, "jn@gmail.com", "cs@gmail.com"));
		transacs.add(new Transac(3L, "theatre", 120, "jn@gmail.com", "cs@gmail.com"));
		transacs.add(new Transac(4L, "co-voiturage", 20, "jn@gmail.com", "cs@gmail.com"));
		return transacs;
	}

	@ModelAttribute("connections")
	public Set<String> getAllConnections() {

		List<UserAccount> lua = userAccountService.findAllUserAccounts();
		Set<String> connections = new HashSet<>();
		for (UserAccount ua : lua) {
			connections.add(ua.getFirstName());
		}
		return connections;
	}

	@ModelAttribute("userconnections")
	public List<String> getConnectionsOfOneUser() {

		UserAccount sender = new UserAccount(3L, "jn@gmail.com", "jn", "jnoel", "chambe", 120);

		List<UserAccount> lua = userAccountService.retrieveConxUserAccount(sender);

		List<String> connections = new ArrayList<>();
		for (UserAccount usac : lua) {
			connections.add(usac.getFirstName());
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

	@GetMapping("/addConnection")
	public String addConnectionGet(Model model) {
		// populate list of all connections
		List<UserAccount> lua = userAccountService.findAllUserAccounts();
		model.addAttribute("connections", lua);

		return "/addConnection_page";
	}

	@PostMapping("/addConnection")
	public String addConnectionPost(
			@RequestParam(value = "connection", name = "connection", required = false) Long connection) {

		// recuperer le choix de l'utilisateur
		Long choiceOfConx = connection;
		// recuperer en param le sender
		UserAccount sender = new UserAccount(3L, "jn@gmail.com", "jn", "jnoel", "chambe", 120);
		// Ajouter une connexion à la liste des connexions du sender
		UserAccount ua = userAccountService.addConxUserAccount(sender, choiceOfConx);

//		// recuperer la liste des conx du sender
//		List<UserAccount> luaSender = userAccountService.retrieveConxUserAccount(sender);
//
//
//		List<String> connections = new ArrayList<>();
//		// recuperer le premier useraccount avec firstName==choiceOfConnection
//		List<UserAccount> luaFirstname = userAccountService.findAllUserAccounts();
//		for (UserAccount ua : luaFirstname) {
//			if (ua.getFirstName().equals(choiceOfConnection)) {
//				// copier le choiceOfConnection dans liste des connections de transfer_page.html
//				connections.add(ua.getFirstName());
//				break;
//			}
//		}

		return "redirect:/transfer";
//		return "addConnection_page";
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
