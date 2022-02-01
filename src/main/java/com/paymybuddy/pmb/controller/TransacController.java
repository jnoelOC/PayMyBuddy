package com.paymybuddy.pmb.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.service.TransacService;

@RestController
public class TransacController {
	public static final Logger logger = LogManager.getLogger(TransacController.class);

	@Autowired
	TransacService transacService;

	@GetMapping("/transactions")
	public List<Transac> findAllTransactions() {

		List<Transac> lt = transacService.findAllTransactions();

		if (lt.isEmpty()) {
			logger.error("Erreur dans Find all Transactions : status Non trouvé.");
			return null;
		}
		logger.info("Transactions trouvées.");
		return lt;
	}

	@PostMapping("/transac/create")
	public ResponseEntity<Transac> createTransac(@RequestBody Transac transac) {

		Transac ts = transacService.saveTransac(transac);

		if (ts.getIdTransaction() <= 0) {
			logger.error("Erreur dans create Transac : status No PK.");
			return new ResponseEntity<>(ts, HttpStatus.NOT_FOUND);
		}
		logger.info("Transaction créée.");
		return new ResponseEntity<>(ts, HttpStatus.CREATED);
	}

	@PutMapping("/transac/update")
	public ResponseEntity<Transac> updateTransac(@RequestBody Transac transac) {

		Transac ts = transacService.saveTransac(transac);

		if (ts.getIdTransaction() <= 0) {
			logger.error("Erreur dans update Transac : status No PK.");
			return new ResponseEntity<>(ts, HttpStatus.NOT_FOUND);
		}
		logger.info("Transaction mise à jour.");
		return new ResponseEntity<>(ts, HttpStatus.OK);
	}

	@DeleteMapping("/transac/delete")
	public ResponseEntity<org.springframework.http.HttpStatus> deleteTransac(@RequestBody Transac transac) {

		transacService.deleteTransac(transac);

		logger.info("Transaction supprimée.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
