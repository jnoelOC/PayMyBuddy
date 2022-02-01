package com.paymybuddy.pmb.service;

import java.util.List;

import com.paymybuddy.pmb.model.Transac;

public interface ITransacService {

	public List<Transac> findAllTransactions();

	public Transac saveTransac(Transac transaction);

	public void deleteTransac(Transac transaction);
}
