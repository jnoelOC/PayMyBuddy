package com.paymybuddy.pmb.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.TransacService;
import com.paymybuddy.pmb.service.UserAccountService;

@Controller
public class HomeController {

	public static final Logger logger = LogManager.getLogger(HomeController.class);

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private TransacService transacService;

	@GetMapping({ "/", "/index" })
	public String mainIndex() {

		return "index";
	}

	@Order(3)
	@GetMapping({ "/home" })
	public String home(Principal principal) {

//		UserAccount user = userAccountService.findByLoginMail(principal.getName());

		return "home_page";
	}

	@Order(1)
	@ModelAttribute("connections")
	public Set<String> getAllConnections() {

		List<UserAccount> lua = userAccountService.findAllUserAccounts();
		Set<String> connections = new HashSet<>();
		for (UserAccount ua : lua) {
			connections.add(ua.getFirstName());
		}
		return connections;
	}

	@Order(4)
	@ModelAttribute("userconnections")
	public List<String> getConnectionsOfOneUser(Model model, RedirectAttributes redirectAttributes,
			Principal principal) {

		List<UserAccount> lua = null;
		List<String> connections = new ArrayList<>();
		try {
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			/*
			 * Optional<UserAccount> toto = userAccountService.findById(1L); if
			 * (toto.isPresent()) { UserAccount toto2 = toto.get(); }
			 */
			lua = userAccountService.retrieveConxUserAccount(sender);
			for (UserAccount usac : lua) {
				connections.add(usac.getLoginMail());
			}
		} catch (Exception e) {
			logger.error("Erreur dans getConnectionsOfOneUser : %s ", e.getMessage());
		}

		return connections;
	}

	@Order(5)
	@ModelAttribute("transacs")
	public List<Transac> getTransacs(Principal principal) {

		return transacService.findAllTransactionsByGiver("j@gmail.com"); // principal.getName());

	}

	@Order(6)
	@GetMapping({ "/transfer" })
	public String transferGet() {

		try {

		} catch (Exception e) {
			logger.error("Erreur dans transferGet : %s ", e.getMessage());
		}
		return "transfer_page";
	}

	@PostMapping({ "/transfer" })
	public String transferPost(Principal principal,
			@RequestParam(value = "userconnections", name = "userconnections", required = false) String idReceiverConnection,
			@RequestParam(value = "description", name = "description", required = false) String description,
			@RequestParam(value = "amount", name = "amount", required = false) Integer amount) {

		try {
			Transac transac = userAccountService.transferMoneyUserAccount(principal.getName(), idReceiverConnection,
					description, amount);
		} catch (Exception e) {
			logger.error("Erreur dans transferPost : %s ", e.getMessage());
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
			System.out.println("Duplicated user : " + ex.getMessage());
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

	@Order(8)
	@PostMapping("/addConnection")
	public String addConnectionPost(Principal principal,
			@RequestParam(value = "connection", name = "connection", required = false) Long connection) {
		try {
			// recuperer le choix de l'utilisateur
			Long choiceOfReceiverConx = connection;
			// recuperer le sender
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			// Ajouter une connexion à la liste des connexions du sender
			UserAccount ua = userAccountService.addConxUserAccount(sender, choiceOfReceiverConx);
		} catch (Exception ex) {
			logger.error("Error dans AddConnectionPost : %s ", ex.getMessage());
		}
		return "redirect:/transfer";
	}

}
