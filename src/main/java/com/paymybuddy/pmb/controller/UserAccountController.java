package com.paymybuddy.pmb.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.paymybuddy.pmb.service.TransacService;
import com.paymybuddy.pmb.service.UserAccountService;

@Controller
public class UserAccountController {

	public static final Logger logger = LogManager.getLogger(UserAccountController.class);

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	TransacService transacService;

//	@Order(1)
//	@ModelAttribute("connections")
//	public Set<String> getAllConnections() {
//
//		List<UserAccount> lua = userAccountService.findAllUserAccounts();
//		Set<String> connections = new HashSet<>();
//		for (UserAccount ua : lua) {
//			connections.add(ua.getFirstName());
//		}
//		return connections;
//	}
//
//	@Order(4)
//	@ModelAttribute("userconnections")
//	public List<String> getConnectionsOfOneUser(Model model, RedirectAttributes redirectAttributes,
//			Principal principal) {
//
//		List<UserAccount> lua = null;
//		List<String> connections = new ArrayList<>();
//		try {
//			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
//			/*
//			 * Optional<UserAccount> toto = userAccountService.findById(1L); if
//			 * (toto.isPresent()) { UserAccount toto2 = toto.get(); }
//			 */
//			lua = userAccountService.retrieveConxUserAccount(sender);
//			for (UserAccount usac : lua) {
//				connections.add(usac.getLoginMail());
//			}
//		} catch (Exception e) {
//			logger.error("Erreur dans getConnectionsOfOneUser : %s ", e.getMessage());
//		}
//
//		return connections;
//	}
//
//	@ModelAttribute("transacs")
//	public List<Transac> getTransacs() {
//		String emailUserConnected = SecurityContextHolder.getContext().getAuthentication().getName();
//		return transacService.findAllTransactionsByGiver(emailUserConnected);
//	}
//
////	@Order(6)
//	@GetMapping("/transfer")
//	public String transferGet() {
//
//		try {
//			System.out.println("je suis dans transferGet !");
//		} catch (Exception e) {
//			logger.error("Erreur dans transferGet : %s ", e.getMessage());
//		}
//		return "transfer_page";
//	}
//
//	@PostMapping("/transfer")
//	public String transferPost(Principal principal,
//			@RequestParam(value = "userconnections", name = "userconnections", required = false) String userconnections,
//			@RequestParam(value = "description", name = "description", required = false) String description,
//			@RequestParam(value = "amount", name = "amount", required = false) Integer amount) {
//
//		try {
//			System.out.println("je suis dans transferPost !");
//			Transac transac = userAccountService.transferMoneyUserAccount(principal.getName(), userconnections,
//					description, amount);
//		} catch (Exception e) {
//			logger.error("Erreur dans transferPost : " + e.getMessage());
//		}
//		return "transfer_page";
//	}
//
//	@GetMapping("/userAccount/addConnection")
//	public String addConnectionGet(Principal principal, Model model) {
//		// récuperer la liste de tous les userAccount
//		List<UserAccount> all = userAccountService.findAllUserAccounts();
//		List<UserAccount> result = null;
//		try {
//			// récupérer toutes les connexions de l'utilisateur connecté
//			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
//			List<String> connectionsSender = userAccountService.retrieveConxUserAccount(sender).stream()
//					.map(UserAccount::getLoginMail).collect(Collectors.toList());
//			// rassembler connxOfSender et l'utilisateur connecté dans la même liste
//			connectionsSender.add(sender.getLoginMail());
//			// enlever les userAccount de la liste connxOfSender de la liste all
//			List<String> allEmail = all.stream().map(UserAccount::getLoginMail).collect(Collectors.toList());
//			result = allEmail.stream().filter(emailUser -> !connectionsSender.contains(emailUser))
//					.map(emailUser -> userAccountService.findByLoginMail(emailUser)).collect(Collectors.toList());
//
//		} catch (Exception ex) {
//			logger.error("Error dans AddConnectionGet : %s ", ex.getMessage());
//		}
//		model.addAttribute("connections", result);
//
//		return "/addConnection_page";
//	}
//
//	@Order(8)
//	@PostMapping("/userAccount/addConnection")
//	public String addConnectionPost(Principal principal,
//			@RequestParam(value = "connection", name = "connection", required = false) String connectionMail) {
//		try {
//			// recuperer le choix de l'utilisateur
//			UserAccount receiver = userAccountService.findByLoginMail(connectionMail);
//			// recuperer le sender
//			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
//			// Ajouter une connexion à la liste des connexions du sender
//			UserAccount ua = userAccountService.addConxUserAccount(sender, receiver.getId());
//		} catch (Exception ex) {
//			logger.error("Error dans AddConnectionPost : %s ", ex.getMessage());
//		}
//		return "redirect:/transfer";
//	}
//
//	@Order(7)
//	@GetMapping("/userAccount/deleteConnection")
//	public String deleteConnectionGet(Principal principal,
//			@RequestParam(value = "connectionMail", name = "connectionMail", required = false) String connectionMail) {
//		try {
//			// recuperer le sender
//			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
//			// Ajouter une connexion à la liste des connexions du sender
//			userAccountService.deleteConxUserAccount(sender, connectionMail);
//		} catch (Exception ex) {
//			logger.error("Error dans AddConnectionPost : %s ", ex.getMessage());
//		}
//		return "redirect:/transfer";
//	}

//	@GetMapping("/useraccounts")
//	public List<UserAccount> findAllUserAccounts() {
//
//		List<UserAccount> lua = userAccountService.findAllUserAccounts();
//
//		if (lua.isEmpty()) {
//			logger.error("Erreur dans Find all userAccounts : status Non trouvé.");
//			return null;
//		}
//		logger.info("userAccounts trouvés.");
//		return lua;
//	}
//
//	@RequestMapping("/useraccount/addoneconnection")
//	public String addOneConnection(Model model) {
//		List<UserAccount> lua = userAccountService.findAllUserAccounts();
//		// model.addAttribute("transfer_page", lua.get(0).getFirstName());
//		List<String> names = new ArrayList<>();
//		names.add("clement");
//		names.add("jean-noel");
//		names.add("toto");
//		model.addAttribute("firstNames", names);
//		return "transfer_page :: #connections";
//	}
//
//	@PostMapping("/useraccount/addconnection")
//	public ResponseEntity<UserAccount> addConnection(@RequestBody UserAccount userAccount) {
//
//		UserAccount ua = null;
////		UserAccount ua = userAccountService.addConxUserAccount(userAccount);
//
////		if (ua.getLoginMail().isEmpty()) {
////			logger.error("Erreur dans add connection : status No PK.");
////			return new ResponseEntity<>(ua, HttpStatus.NOT_FOUND);
////		}
////		logger.info("connection ajoutée.");
//		return new ResponseEntity<>(ua, HttpStatus.CREATED);
//	}
//
//	@PostMapping("/useraccount/pay")
//	public ResponseEntity<Transac> transferMoney(@RequestBody UserAccount userAccount) {
//
//		Transac trx = null;
////		try {
////			trx = userAccountService.transferMoneyUserAccount(userAccount);
////		} catch (SQLException e) {
////			logger.error("Erreur SQL dans transfer money : " + e.getMessage());
////		}
//
//		if ((trx == null) || (trx.getIdTransaction() <= 0)) {
//			logger.error("Erreur dans transfer money : status No PK.");
//			return new ResponseEntity<>(trx, HttpStatus.NOT_FOUND);
//		}
//		logger.info("transaction ajoutée.");
//		return new ResponseEntity<>(trx, HttpStatus.CREATED);
//	}
//
//	@PostMapping("/useraccount/create")
//	public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccount userAccount) {
//
//		UserAccount ua = userAccountService.saveUserAccount(userAccount);
//
//		if (ua.getLoginMail().isEmpty()) {
//			logger.error("Erreur dans create userAccount : status No PK.");
//			return new ResponseEntity<>(ua, HttpStatus.NOT_FOUND);
//		}
//		logger.info("userAccount trouvé.");
//		return new ResponseEntity<>(ua, HttpStatus.CREATED);
//	}
//
//	@PutMapping("/useraccount/update")
//	public ResponseEntity<UserAccount> updateUserAccount(@RequestBody UserAccount userAccount) {
//
//		UserAccount ua = userAccountService.saveUserAccount(userAccount);
//
//		if (ua.getLoginMail().isEmpty()) {
//			logger.error("Erreur dans update userAccount : status No PK.");
//			return new ResponseEntity<>(ua, HttpStatus.NOT_FOUND);
//		}
//		logger.info("userAccount mis à jour.");
//		return new ResponseEntity<>(ua, HttpStatus.OK);
//	}
//
//	@DeleteMapping("/useraccount/delete")
//	public ResponseEntity<org.springframework.http.HttpStatus> deleteUserAccount(@RequestBody UserAccount userAccount) {
//
//		userAccountService.deleteUserAccount(userAccount);
//
//		logger.info("userAccount supprimé.");
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
}