package com.paymybuddy.pmb.service;

public class EmailExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailExistsException(String errorMessage) {
		super(errorMessage);
	}
}
