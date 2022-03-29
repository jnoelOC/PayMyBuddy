package com.paymybuddy.pmb.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	public static final Logger logger = LogManager.getLogger(HomeController.class);

	@Autowired
	private UserAccountService userAccountService;

	@GetMapping({ "/", "/index" })
	public String mainIndex() {

		return "index";
	}

	@GetMapping({ "/home" })
	public String home(RedirectAttributes redirectAttributes, Model model, Principal principal) {
		System.out.println("TOOOOOOOOOOOOOOOOOOTOOOOOOO " + principal.getName());
		UserAccount user = userAccountService.findByLoginMail(principal.getName());
		// creer un attribut
		model.addAttribute("user", user);
		// rediriger l'attribut
		redirectAttributes.addAttribute("userAttrib", "user");

		return "home_page";
//		return "redirect:home_page";
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

//	@GetMapping({ "/transfer" })
	@ModelAttribute("userconnections")
	public List<String> getConnectionsOfOneUser(RedirectAttributes redirectAttributes, Principal principal) {

		List<UserAccount> lua = null;
		try {
			System.out.println("TOOOOOOOOOOOOOOOOOOTOOOOOOOpopopo " + principal.getName());
//			UserAccount sender = (UserAccount) redirectAttributes.getAttribute("userAttrib");
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());

			lua = userAccountService.retrieveConxUserAccount(sender);
		} catch (Exception e) {
			logger.error("Erreur dans getConnectionsOfOneUser : " + e.getMessage());
		}
		List<String> connections = new ArrayList<>();
		for (UserAccount usac : lua) {
			connections.add(usac.getFirstName());
		}
		return connections;
	}

//	@ModelAttribute("userconnections")
//	public List<String> getConnectionsOfOneUser() {
//		
//		UserAccount sender = new UserAccount(3L, "jn@gmail.com", "jn", "jnoel", "chambe", 120);
//
//		List<UserAccount> lua = userAccountService.retrieveConxUserAccount(sender);
//		
//		List<String> connections = new ArrayList<>();
//		for (UserAccount usac : lua) {
//			connections.add(usac.getFirstName());
//		}
//		return connections;
//	}

	@GetMapping({ "/transfer" })
	public String transferGet() {

		try {

		} catch (Exception e) {
			logger.error("Erreur dans transfer money : " + e.getMessage());
		}
		return "transfer_page";
	}

	@PostMapping({ "/transfer" })
	public String transferPost(
			@RequestParam(value = "userconnections", name = "userconnections", required = false) Long idConnection,
			@RequestParam(value = "description", name = "description", required = false) String description,
			@RequestParam(value = "amount", name = "amount", required = false) Integer amount) {

		try {
			Transac trx = userAccountService.transferMoneyUserAccount(idConnection, description, amount);
		} catch (SQLException e) {
			logger.error("Erreur dans transferPost : " + e.getMessage());
		}
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
		try {
			// recuperer le choix de l'utilisateur
			Long choiceOfConx = connection;
			// recuperer le sender
			UserAccount sender = userAccountService.getById(connection);
//			UserAccount sender = new UserAccount(3L, "jn@gmail.com", "jn", "jnoel", "chambe", 120);
			// Ajouter une connexion à la liste des connexions du sender
			UserAccount ua = userAccountService.addConxUserAccount(sender, choiceOfConx);

		} catch (Exception ex) {
			System.out.println("Error dans AddConnectionPost : " + ex.getMessage());
		}
		return "redirect:/transfer";
	}

}
