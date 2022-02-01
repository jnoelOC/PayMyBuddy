package com.paymybuddy.pmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_BA_PK")
	private Long idPK;
	@Column(name = "IBAN")
	private String iban;
	@Column(name = "BIC")
	private String bic;
	@Column(name = "BANK_NAME")
	private String bankName;
	@Column(name = "LOGIN_MAIL_FK")
	private String loginMailFK;

	public BankAccount() {
	}

	public BankAccount(Long idPK, String iban, String bic, String bankName, String loginMailFK) {
		super();
		this.idPK = idPK;
		this.iban = iban;
		this.bic = bic;
		this.bankName = bankName;
		this.loginMailFK = loginMailFK;
	}

	public Long getIdPK() {
		return idPK;
	}

	public void setIdPK(Long idPK) {
		this.idPK = idPK;
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

	public String getLoginMailFK() {
		return loginMailFK;
	}

	public void setLoginMailFK(String loginMailFK) {
		this.loginMailFK = loginMailFK;
	}

}