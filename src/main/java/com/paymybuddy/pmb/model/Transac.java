package com.paymybuddy.pmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transac {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TRANSACTION")
	private Integer idTransaction;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "AMOUNT")
	private Integer amount;
	@Column(name = "GIVER_FK")
	private String giverFK;
	@Column(name = "RECEIVER_FK")
	private String receiverFK;

	public Transac() {
	}

	public Transac(Integer idTransaction, String description, Integer amount, String giverFK, String receiverFK) {
		this.idTransaction = idTransaction;
		this.description = description;
		this.amount = amount;
		this.giverFK = giverFK;
		this.receiverFK = receiverFK;
	}

	public Integer getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(Integer idTransaction) {
		this.idTransaction = idTransaction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getGiverFK() {
		return giverFK;
	}

	public void setGiverFK(String giverFK) {
		this.giverFK = giverFK;
	}

	public String getReceiverFK() {
		return receiverFK;
	}

	public void setReceiverFK(String receiverFK) {
		this.receiverFK = receiverFK;
	}

}