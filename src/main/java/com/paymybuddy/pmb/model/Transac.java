package com.paymybuddy.pmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * This class realize an entity of Transac.
 * 
 * @author jean-noel.chambe
 * 
 */
@Entity
@Table(name = "transac")
public class Transac {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	@Column(name = "DESCRIPTION", length = 60)
	private String description;
	@Column(name = "AMOUNT")
	private Double amount;
	@Column(name = "GIVER", length = 50)
	private String giver;
	@Column(name = "RECEIVER", length = 50)
	private String receiver;

	public Transac() {
	}

	public Transac(Long idTransaction, String description, Double amount, String giver, String receiver) {
		this.id = idTransaction;
		this.description = description;
		this.amount = amount;
		this.giver = giver;
		this.receiver = receiver;
	}

	public Long getIdTransaction() {
		return id;
	}

	public void setIdTransaction(Long idTransaction) {
		this.id = idTransaction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getGiver() {
		return giver;
	}

	public void setGiver(String giver) {
		this.giver = giver;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}