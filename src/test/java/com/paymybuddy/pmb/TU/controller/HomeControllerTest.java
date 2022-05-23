package com.paymybuddy.pmb.TU.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.paymybuddy.pmb.controller.HomeController;
import com.paymybuddy.pmb.service.impl.MyUserDetailsService;
import com.paymybuddy.pmb.service.impl.UserAccountService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { HomeController.class })
class HomeControllerTest {
	public static final Logger logger = LogManager.getLogger(HomeControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@MockBean
	private UserAccountService userAccountService;

	@BeforeEach
	void SetUpApplicationContext() {
		// mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("Get slash")
	void whenIndexInEntry_thenReturnsIndexString() throws Exception {
		// ARRANGE
		// ACT
		MvcResult result = mockMvc.perform(get("/").contentType("application/json")).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isEqualTo("index");

	}

}
