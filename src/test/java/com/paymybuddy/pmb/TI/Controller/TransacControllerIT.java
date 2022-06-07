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
public class TransacControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Get transfer")
	@WithMockUser(username = "j@gmail.com")
	void transferGet() throws Exception {
		mockMvc.perform(get("/transfer")).andExpect(status().isOk()).andExpect(view().name("transfer_page"))
				.andReturn();
	}

	@Test
	@DisplayName("Post transfer")
	@WithMockUser(username = "j@gmail.com")
	void transferPost() throws Exception {
		mockMvc.perform(post("/transfer").contentType("application/json").with(csrf())).andExpect(status().isFound())
				.andExpect(view().name("redirect:/transfer")).andReturn();
	}

	@Test
	@DisplayName("Get transac/addConnection")
	@WithMockUser(username = "j@gmail.com")
	void transacGetAddConnectionGet() throws Exception {
		mockMvc.perform(get("/transac/addConnection").contentType("application/json").with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("/addConnection_page")).andReturn();
	}

	@Test
	@DisplayName("Post transac/addConnection")
	@WithMockUser(username = "j@gmail.com")
	void transacPostAddConnectionGet() throws Exception {
		mockMvc.perform(post("/transac/addConnection").contentType("application/json").with(csrf()))
				.andExpect(status().isFound()).andExpect(view().name("redirect:/transfer")).andReturn();
	}

	@Test
	@DisplayName("Get transac/deleteConnection")
	@WithMockUser(username = "j@gmail.com")
	void transacGetDeleteConnectionGet() throws Exception {
		mockMvc.perform(get("/transac/deleteConnection").contentType("application/json").with(csrf()))
				.andExpect(status().isFound()).andExpect(view().name("redirect:/transfer")).andReturn();
	}
}