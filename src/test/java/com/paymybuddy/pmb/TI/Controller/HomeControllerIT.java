package com.paymybuddy.pmb.TI.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Get login")
	void whenLogin_thenReturnsLogin_page() throws Exception {
		mockMvc.perform(get("/login").contentType("application/json")).andExpect(status().isOk())
				.andExpect(view().name("login_page")).andReturn();
	}

	@Test
//	@WithMockUser(username = "unknown@gmail.com")
	@WithAnonymousUser
	@DisplayName("Get login with anonymous user and unauthenticated")
	void whenLoginWithAnonymousUser_thenReturnsLogin_pageAndUnauthenticated() throws Exception {
		mockMvc.perform(get("/login").contentType("application/json")).andExpect(status().isOk())
				.andExpect(view().name("login_page")).andExpect(unauthenticated()).andReturn();
	}

	@Test
	@DisplayName("Get logout")
	void whenLogout_thenReturnsIndex() throws Exception {
		MvcResult result = mockMvc.perform(get("/logout").contentType("application/json")).andExpect(status().isFound())
				.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("Get slash")
	void whenSlash_thenReturnsIndex() throws Exception {
		MvcResult result = mockMvc.perform(get("/").contentType("application/json")).andExpect(status().isFound())
				.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("Get index")
	void whenIndex_thenReturnsIndex() throws Exception {
		mockMvc.perform(get("/index").contentType("application/json")).andExpect(status().isOk())
				.andExpect(view().name("index")).andReturn();
	}

	@Test
	@WithMockUser(username = "j@gmail.com")
	@DisplayName("Get home")
	void whenHome_thenReturnsNotNull() throws Exception {
		// ARRANGE
		mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home_page")).andReturn();
	}

	@Test
	@DisplayName("Get register")
	void whenRegisterInEntry_thenReturnsNotNull() throws Exception {
		// ARRANGE
		MvcResult result = mockMvc.perform(get("/register").contentType("application/json")).andExpect(status().isOk())
				.andReturn();
		// ACT
		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("Post register")
	void whenRegisterPost_thenReturnsNotNull() throws Exception {
		// ARRANGE
		MvcResult result = mockMvc
				.perform(post("/register").param("redirectAttributes", "attribute").param("firstName", "Max")
						.param("lastName", "Jacob").param("loginMail", "j@gmail.com")
						.param("psswrd", "$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2")
						.contentType("application/json").with(csrf()))
				.andExpect(status().isFound()).andExpect(view().name("redirect:/transfer")).andReturn();
		// ACT
		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
	}
}