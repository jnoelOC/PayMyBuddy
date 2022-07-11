package com.paymybuddy.pmb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * This class realize an entity of UserAccount.
 * 
 * @author jean-noel.chambe
 * 
 */
@Entity
@Table(name = "user_account")
@JsonIgnoreProperties(value = { "connections" })
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	@Column(name = "LOGIN_MAIL", length = 50, nullable = false, unique = true)
	@NotBlank(message = "the email cannot be empty or null")
	@Email(message = "please entre email address")
	private String loginMail;
	@Column(name = "PSSWRD", length = 60)
	@NotBlank(message = "the password cannot be empty or null")
	private String psswrd;
	@Column(name = "FIRSTNAME", length = 30)
	private String firstName;
	@Column(name = "LASTNAME", length = 30)
	private String lastName;
	@Column(name = "SOLDE", nullable = false)
	private Double solde;

	@ManyToMany(fetch = FetchType.LAZY)
	// @Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "connection", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "connection_id"))
	public List<UserAccount> connections = new ArrayList<>();

	public UserAccount() {
	}

	public UserAccount(Long id, String loginMail, String psswrd, String firstName, String lastName, Double solde) {
		this.id = id;
		this.loginMail = loginMail;
		this.psswrd = psswrd;
		this.firstName = firstName;
		this.lastName = lastName;
		this.solde = solde;
	}

	public List<UserAccount> getConnections() {
		return connections;
	}

	public void setConnections(List<UserAccount> connections) {
		this.connections = connections;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}

}
