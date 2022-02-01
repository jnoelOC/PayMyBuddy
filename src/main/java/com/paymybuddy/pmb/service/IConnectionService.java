package com.paymybuddy.pmb.service;

import java.util.List;

import com.paymybuddy.pmb.model.Connection;

public interface IConnectionService {
	public List<Connection> findAllUserAccounts();

	public Connection saveConnection(Connection connection);

	public void deleteConnection(Connection connection);
}
