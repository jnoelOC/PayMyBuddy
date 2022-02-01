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

import com.paymybuddy.pmb.model.Connection;
import com.paymybuddy.pmb.service.ConnectionService;

@RestController
public class ConnectionController {
	public static final Logger logger = LogManager.getLogger(ConnectionController.class);

	@Autowired
	ConnectionService connectionService;

	@GetMapping("/connections")
	public List<Connection> findAllConnections() {

		List<Connection> lc = connectionService.findAllConnections();

		if (lc.isEmpty()) {
			logger.error("Erreur dans Find all Connections : status Non trouvé.");
			return null;
		}
		logger.info("Connections trouvées.");
		return lc;
	}

	@PostMapping("/connection/create")
	public ResponseEntity<Connection> createConnection(@RequestBody Connection connection) {

		Connection cn = connectionService.saveConnection(connection);

		if (cn.getIdCnxPK() <= 0) {
			logger.error("Erreur dans create Connection : status No PK.");
			return new ResponseEntity<>(cn, HttpStatus.NOT_FOUND);
		}
		logger.info("Connection créée.");
		return new ResponseEntity<>(cn, HttpStatus.CREATED);
	}

	@PutMapping("/connection/update")
	public ResponseEntity<Connection> updateConnection(@RequestBody Connection connection) {

		Connection cn = connectionService.saveConnection(connection);

		if (cn.getIdCnxPK() <= 0) {
			logger.error("Erreur dans update Connection : status No PK.");
			return new ResponseEntity<>(cn, HttpStatus.NOT_FOUND);
		}
		logger.info("Connection mise à jour.");
		return new ResponseEntity<>(cn, HttpStatus.OK);
	}

	@DeleteMapping("/connection/delete")
	public ResponseEntity<org.springframework.http.HttpStatus> deleteConnection(@RequestBody Connection connection) {

		connectionService.deleteConnection(connection);

		logger.info("Connection supprimée.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
