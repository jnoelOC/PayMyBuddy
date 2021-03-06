package com.paymybuddy.pmb.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.impl.TransacService;
import com.paymybuddy.pmb.service.impl.UserAccountService;

/**
 * 
 * This class realize all the controller operation on Transac.
 * 
 * @author jean-noel.chambe
 * 
 */
@Controller
public class TransacController {
	public static final Logger logger = LogManager.getLogger(TransacController.class);

	@Autowired
	TransacService transacService;

	@Autowired
	UserAccountService userAccountService;

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
	public List<String> getConnectionsOfOneUser(Principal principal) {

		List<UserAccount> lua = null;
		List<String> connections = new ArrayList<>();
		try {
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());

			lua = userAccountService.retrieveConxUserAccount(sender);
			for (UserAccount usac : lua) {
				connections.add(usac.getLoginMail());
			}
		} catch (Exception e) {
			logger.error("Erreur dans getConnectionsOfOneUser : %s ", e.getMessage());
		}

		return connections;
	}

	@GetMapping("/transfer/page/{pageNumber}")
	public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {

		try {
			// managment of pages
			Page<Transac> page = transacService.findPage(currentPage);
			int totalPages = page.getTotalPages();
			long totalItems = page.getTotalElements();
			List<Transac> transacs2 = page.getContent();

			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalItems", totalItems);
			model.addAttribute("transacs", transacs2);

		} catch (Exception e) {
			logger.error("Erreur dans getOnePage : " + e.getMessage());
		}
		return "transfer_page";
	}

//	@GetMapping("/transfer")
//	public String getAllPages(Model model) {
////	public String transferGet(Model model) {
//		return getOnePage(model, 1);
//	}

	@GetMapping("/transfer")
	public String transferGet(Model model) {

		List<Transac> ltOfReceiver = new ArrayList<>();
		List<Transac> ltOfGiver = new ArrayList<>();
		try {

			String emailUserConnected = SecurityContextHolder.getContext().getAuthentication().getName();
			ltOfGiver = transacService.findAllTransactionsByGiver(emailUserConnected);
			ltOfReceiver = transacService.findAllTransactionsByReceiver(emailUserConnected);
			ltOfGiver.addAll(ltOfReceiver);
			model.addAttribute("transacs", ltOfGiver);

		} catch (Exception e) {
			logger.error("Erreur dans GetAll : %s ", e.getMessage());
		}

		return getOnePage(model, 1);
	}

//	@GetMapping("/transfer")
//	public String transferGet(Model model) {
//
//		List<Transac> ltOfReceiver = new ArrayList<>();
//		List<Transac> ltOfGiver = new ArrayList<>();
//		try {
//
//			String emailUserConnected = SecurityContextHolder.getContext().getAuthentication().getName();
//			ltOfGiver = transacService.findAllTransactionsByGiver(emailUserConnected);
//			ltOfReceiver = transacService.findAllTransactionsByReceiver(emailUserConnected);
//			ltOfGiver.addAll(ltOfReceiver);
//			model.addAttribute("transacs", ltOfGiver);
//
//		} catch (Exception e) {
//			logger.error("Erreur dans transferGet : " + e.getMessage());
//		}
//
//		return "transfer_page";
//	}

	@PostMapping("/transfer")
	public String transferPost(RedirectAttributes redirectAttributes, Principal principal,
			@RequestParam(value = "userconnections", name = "userconnections", required = false) String userconnections,
			@RequestParam(value = "description", name = "description", required = false) String description,
			@RequestParam(value = "amount", name = "amount", required = false) Double amount) {

		try {

			if (description.isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Please, fill a description.");
				return "redirect:/transfer";
			}

			if (amount == null) {
				redirectAttributes.addFlashAttribute("message", "Please, fill a positive amount.");
				return "redirect:/transfer";
			}

			Transac transac = userAccountService.transferMoneyUserAccount(principal.getName(), userconnections,
					description, amount);
		} catch (Exception e) {
			logger.error("Erreur dans transferPost : %s", e.getMessage());
		}

		return "redirect:/transfer";
	}

	@GetMapping("/transac/addConnection")
	public String addConnectionGet(Principal principal, Model model) {
		// r??cuperer la liste de tous les userAccount
		List<UserAccount> all = userAccountService.findAllUserAccounts();
		List<UserAccount> result = null;
		try {
			// r??cup??rer toutes les connexions de l'utilisateur connect??
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			List<String> connectionsSender = userAccountService.retrieveConxUserAccount(sender).stream()
					.map(UserAccount::getLoginMail).collect(Collectors.toList());
			// rassembler connxOfSender et l'utilisateur connect?? dans la m??me liste
			connectionsSender.add(sender.getLoginMail());
			// enlever les userAccount de la liste connxOfSender de la liste all
			List<String> allEmail = all.stream().map(UserAccount::getLoginMail).collect(Collectors.toList());
			result = allEmail.stream().filter(emailUser -> !connectionsSender.contains(emailUser))
					.map(emailUser -> userAccountService.findByLoginMail(emailUser)).collect(Collectors.toList());

		} catch (Exception ex) {
			logger.error("Error dans AddConnectionGet : %s ", ex.getMessage());
		}
		model.addAttribute("connections", result);

		return "/addConnection_page";
	}

	@PostMapping("/transac/addConnection")
	public String addConnectionPost(Principal principal,
			@RequestParam(value = "connection", name = "connection", required = false) String connectionMail) {
		try {
			// recuperer le choix de l'utilisateur
			UserAccount receiver = userAccountService.findByLoginMail(connectionMail);
			// recuperer le sender
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			// Ajouter une connexion ?? la liste des connexions du sender
			UserAccount ua = userAccountService.addConxUserAccount(sender, receiver.getId());
		} catch (Exception ex) {
			logger.error("Error dans AddConnectionPost : " + ex.getMessage());
		}
		return "redirect:/transfer";
	}

	@GetMapping("/transac/deleteConnection")
	public String deleteConnectionGet(Principal principal,
			@RequestParam(value = "connectionMail", name = "connectionMail", required = false) String connectionMail) {
		try {
			// recuperer le sender
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			// supprimer une connexion ?? la liste des connexions du sender
			userAccountService.deleteConxUserAccount(sender, connectionMail);
		} catch (Exception ex) {
			logger.error("Error dans deleteConnectionGet : " + ex.getMessage());
		}
		return "redirect:/transfer";
	}

//	@GetMapping("/transactions")
//	public List<Transac> findAllTransactions() {
//
//		List<Transac> lt = transacService.findAllTransactions();
//
//		if (lt.isEmpty()) {
//			logger.error("Erreur dans Find all Transactions : status Non trouv??.");
//			return null;
//		}
//		logger.info("Transactions trouv??es.");
//		return lt;
//	}
//
//	@PostMapping("/transac/create")
//	public ResponseEntity<Transac> createTransac(@RequestBody Transac transac) {
//
//		Transac ts = transacService.saveTransac(transac);
//
//		if (ts.getIdTransaction() <= 0) {
//			logger.error("Erreur dans create Transac : status No PK.");
//			return new ResponseEntity<>(ts, HttpStatus.NOT_FOUND);
//		}
//		logger.info("Transaction cr????e.");
//		return new ResponseEntity<>(ts, HttpStatus.CREATED);
//	}
//
//	@PutMapping("/transac/update")
//	public ResponseEntity<Transac> updateTransac(@RequestBody Transac transac) {
//
//		Transac ts = transacService.saveTransac(transac);
//
//		if (ts.getIdTransaction() <= 0) {
//			logger.error("Erreur dans update Transac : status No PK.");
//			return new ResponseEntity<>(ts, HttpStatus.NOT_FOUND);
//		}
//		logger.info("Transaction mise ?? jour.");
//		return new ResponseEntity<>(ts, HttpStatus.OK);
//	}
//
//	@DeleteMapping("/transac/delete")
//	public ResponseEntity<org.springframework.http.HttpStatus> deleteTransac(@RequestBody Transac transac) {
//
//		transacService.deleteTransac(transac);
//
//		logger.info("Transaction supprim??e.");
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
}
