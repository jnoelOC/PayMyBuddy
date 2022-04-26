package com.paymybuddy.pmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "IBAN")
	private String iban;
	@Column(name = "BIC")
	private String bic;
	@Column(name = "BANK_NAME")
	private String bankName;
	@Column(name = "LOGIN_MAIL")
	private String loginMail;

	public BankAccount() {
	}

	public BankAccount(Long id, String bankName, String iban, String bic, String loginMail) {
		super();
		this.id = id;
		this.iban = iban;
		this.bic = bic;
		this.bankName = bankName;
		this.loginMail = loginMail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getLoginMail() {
		return loginMail;
	}

	public void setLoginMail(String loginMail) {
		this.loginMail = loginMail;
	}

}