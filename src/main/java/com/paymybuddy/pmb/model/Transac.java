package com.paymybuddy.pmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transac")
public class Transac {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TRANSACTION", nullable = false, unique = true)
	private Long idTransaction;
	@Column(name = "DESCRIPTION", length = 60)
	private String description;
	@Column(name = "AMOUNT")
	private Integer amount;
	@Column(name = "GIVER", length = 50)
	private String giver;
	@Column(name = "RECEIVER", length = 50)
	private String receiver;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_UA_FK", updatable = false, insertable = false)
	private UserAccount userAccount;

	public Transac() {
	}

	public Transac(Long idTransaction, String description, Integer amount, String giver, String receiver,
			UserAccount userAccount) {
		this.idTransaction = idTransaction;
		this.description = description;
		this.amount = amount;
		this.giver = giver;
		this.receiver = receiver;
		this.userAccount = userAccount;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Long getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(Long idTransaction) {
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