package com.paymybuddy.pmb.service.impl;

public class EmailExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailExistsException(String errorMessage) {
		super(errorMessage);
	}
}
