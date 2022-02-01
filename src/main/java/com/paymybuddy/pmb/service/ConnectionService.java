package com.paymybuddy.pmb.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.pmb.model.Connection;
import com.paymybuddy.pmb.repository.IConnectionRepository;

@Service
public class ConnectionService {
	public static final Logger logger = LogManager.getLogger(ConnectionService.class);

	@Autowired
	private IConnectionRepository connectionRepository;

	public List<Connection> findAllConnections() {
		return connectionRepository.findAll();
	}

	@Transactional
	public Connection saveConnection(Connection connection) {

		return connectionRepository.save(connection);
	}

	@Transactional
	public void deleteConnection(Connection connection) {
		connectionRepository.delete(connection);
	}
}
