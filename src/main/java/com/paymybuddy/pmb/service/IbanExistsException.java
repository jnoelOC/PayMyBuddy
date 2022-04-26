package com.paymybuddy.pmb.service;

public class IbanExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public IbanExistsException(String errorMessage) {
		super(errorMessage);
	}
}
