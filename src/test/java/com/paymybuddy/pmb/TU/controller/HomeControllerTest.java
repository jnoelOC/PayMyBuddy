package com.paymybuddy.pmb.TU.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.pmb.controller.HomeController;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.IUserAccountRepository;
import com.paymybuddy.pmb.service.impl.MyUserDetailsService;
import com.paymybuddy.pmb.service.impl.UserAccountService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { HomeController.class })
class HomeControllerTest {
	public static final Logger logger = LogManager.getLogger(HomeControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

//	@MockBean
//	private UserDetails userDetails;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private UserAccountService userAccountService;

	@MockBean
	private IUserAccountRepository userAccountRepository;

	@BeforeEach
	void SetUpApplicationContext() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@ParameterizedTest
	@MethodSource("EntrySource")
	@DisplayName("Get slash and index and login")
	void whenSlashInEntry_thenReturnsNotNull(String entry) throws Exception {
		// ARRANGE
		MvcResult result = mockMvc.perform(get(entry).contentType("application/json")).andExpect(status().isOk())
				.andReturn();
		// ACT
		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();

	}

	// with its data
	private static Stream<Arguments> EntrySource() {
		return Stream.of(Arguments.of("/"), Arguments.of("/index"), Arguments.of("/login"));
	}

	@Test
	@DisplayName("Get logout")
	void whenLogoutInEntry_thenReturnsNotNull() throws Exception {
		// ARRANGE
		MvcResult result = mockMvc.perform(get("/logout").contentType("application/json")).andExpect(status().isOk())
				.andReturn();
		// ACT
		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
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
	@DisplayName("Get home")
	void whenHomeInEntry_thenReturnsNotNull() throws Exception {
		// ARRANGE
		UserAccount ua1 = new UserAccount(1L, "admin@gmail.com",
				"$2a$10$Yut7ko9yB0pbMMg2GaTMqOp6UtMoyhYWVqdYLk9Rk7/SOkNUA.u/y", "admin", "admin", 50D);
//		when(userAccountRepository.findByLoginMail("admin@gmail.com")).thenReturn(ua1);
//		UserDetails userDetails = myUserDetailsService.loadUserByUsername("admin@gmail.com");
//		when(userDetails.getUsername()).thenReturn("admin@gmail.com");

//		when(principal.getName()).thenReturn("admin@gmail.com");
		when(userAccountService.findByLoginMail("admin@gmail.com")).thenReturn(ua1);
		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();

		class modl implements Model {

			@Override
			public Model addAttribute(String attributeName, Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Model addAttribute(Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Model addAllAttributes(Collection<?> attributeValues) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Model addAllAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Model mergeAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean containsAttribute(String attributeName) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Object getAttribute(String attributeName) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> asMap() {
				// TODO Auto-generated method stub
				return null;
			}

		}
		modl m = new modl();

		HomeController homeController = new HomeController();
		homeController.home(p, m);

		MvcResult result = mockMvc.perform(get("/home").param("principal", "admin@gmail.com").param("model", "11D")
				.contentType("application/json")).andExpect(status().isOk()).andReturn();
		// ACT
		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
	}
}
