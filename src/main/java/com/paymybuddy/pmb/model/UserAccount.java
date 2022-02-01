package com.paymybuddy.pmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_UA_PK", nullable = false)
	private Long idPK;
	@Column(name = "LOGIN_MAIL")
	private String loginMail;
	@Column(name = "PSSWRD")
	private String psswrd;
	@Column(name = "FIRSTNAME")
	private String firstName;
	@Column(name = "LASTNAME")
	private String lastName;
	@Column(name = "SOLDE")
	private Integer solde;
	@Column(name = "FRIENDS_LIST")
	private String friendsList;

	public UserAccount() {
	}

	public UserAccount(Long idPK, String loginMail, String psswrd, String firstName, String lastName, Integer solde,
			String friendsList) {
		this.idPK = idPK;
		this.loginMail = loginMail;
		this.psswrd = psswrd;
		this.firstName = firstName;
		this.lastName = lastName;
		this.solde = solde;
		this.friendsList = friendsList;
	}

	public Long getIdPK() {
		return idPK;
	}

	public void setIdPK(Long idPK) {
		this.idPK = idPK;
	}

	public String getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(String friendsList) {
		this.friendsList = friendsList;
	}

	public String getLoginMail() {
		return loginMail;
	}

	public void setLoginMail(String loginMail) {
		this.loginMail = loginMail;
	}

	public String getPsswrd() {
		return psswrd;
	}

	public void setPsswrd(String psswrd) {
		this.psswrd = psswrd;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getSolde() {
		return solde;
	}

	public void setSolde(Integer solde) {
		this.solde = solde;
	}

}
