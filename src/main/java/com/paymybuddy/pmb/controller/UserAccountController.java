package com.paymybuddy.pmb.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.paymybuddy.pmb.service.impl.TransacService;
import com.paymybuddy.pmb.service.impl.UserAccountService;

/**
 * 
 * This class realize all the controller operation on UserAccount.
 * 
 * @author jean-noel.chambe
 * 
 */
@Controller
public class UserAccountController {

	public static final Logger logger = LogManager.getLogger(UserAccountController.class);

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	TransacService transacService;

}