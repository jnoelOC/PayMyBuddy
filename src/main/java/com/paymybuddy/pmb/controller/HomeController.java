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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.UserAccountService;

@Controller
public class HomeController {

	@Autowired
	private UserAccountService userAccountService;

	@GetMapping({ "/", "/index" })
	public String index() {

		return "index";
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
	public String transfer() {
		return "transfer_page";
	}

	@GetMapping({ "/login" })
//	@RolesAllowed("USER")
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
					lastName, 0);

			if (registration == null) {
				redirectAttributes.addAttribute("attribute", "index");
			}
		} catch (Exception ex) {
			System.out.println("Duplicated user (by email) : " + ex.getMessage());
		}
		return "redirect:/transfer";
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

		return "redirect:/transfer";
	}

}
