package com.paymybuddy.pmb.TI.Controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BankAccountControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "j@gmail.com")
	@DisplayName("Add sold")
	void addSoldPost_thenRedirectToTransfer() throws Exception {
		mockMvc.perform(post("/bank/addSold").param("redirectAttributes", "").param("principal", "j@gmail.com")
				.param("model", "").param("sold", "15").contentType("application/json").with(csrf()))
				.andExpect(status().isFound()).andExpect(view().name("redirect:/transfer")).andReturn();
	}

	@Test
	@WithMockUser(username = "j@gmail.com")
	@DisplayName("Substract sold")
	void substractSoldPost_thenRedirectToTransfer() throws Exception {
		mockMvc.perform(post("/bank/substractSold").param("principal", "j@gmail.com").param("sold", "15")
				.contentType("application/json").with(csrf())).andExpect(status().isFound())
				.andExpect(view().name("redirect:/transfer")).andReturn();
	}

	@Test
	@WithMockUser(username = "j@gmail.com")
	@DisplayName("Get bank")
	void getBank_thenReturnsBank_page() throws Exception {
		mockMvc.perform(get("/bank").param("principal", "j@gmail.com").param("model", "banks")
				.contentType("application/json").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("bank_page")).andReturn();
	}

	@Test
	@WithMockUser(username = "j@gmail.com")
	@DisplayName("Delete bank")
	void deleteBank_thenReturnsBank_page() throws Exception {
		mockMvc.perform(
				get("/bank/delete").param("userBank", "j@gmail.com").contentType("application/json").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("bank_page")).andReturn();
	}
}