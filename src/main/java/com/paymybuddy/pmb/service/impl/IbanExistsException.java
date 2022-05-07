package com.paymybuddy.pmb.service.impl;

public class IbanExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public IbanExistsException(String errorMessage) {
		super(errorMessage);
	}
}
