package com.paymybuddy.pmb.TU.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@Autowired
	HomeController homeController = new HomeController();

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

	@Test
	@DisplayName("Get slash and index")
	void whenSlashInEntry_thenReturnsNotNull() throws Exception {
		// ARRANGE
		// ACT
		String ret = homeController.mainIndex();
		// ASSERT
		assertThat(ret).hasToString("index");

	}

	@Test
	@DisplayName("Get login")
	void whenLoginInEntry_thenReturnsNotNull() throws Exception {
		// ARRANGE
		// ACT
		String ret = homeController.login();
		// ASSERT
		assertThat(ret).hasToString("login_page");

	}

	@Test
	@DisplayName("Get logout")
	void whenLogoutInEntry_thenReturnsNotNull() throws Exception {
		// ARRANGE
		// ACT
		String ret = homeController.logout();
		// ASSERT
		assertThat(ret).hasToString("index");
	}

	@Test
	@DisplayName("Get register")
	void whenRegisterInEntry_thenReturnsNotNull() throws Exception {
		// ARRANGE
		// ACT
		String ret = homeController.register();
		// ASSERT
		assertThat(ret).hasToString("register_page");
	}

	@ParameterizedTest
	@MethodSource("HomeSource")
	@DisplayName("Get home")
	void whenHomeInEntry_thenReturnsNotNull(String mail) throws Exception {
		// ARRANGE
		UserAccount ua1 = new UserAccount(1L, mail, "$2a$10$Yut7ko9yB0pbMMg2GaTMqOp6UtMoyhYWVqdYLk9Rk7/SOkNUA.u/y",
				"admin", "admin", 50D);

		princip p = new princip();

		modl m = new modl();

//		when(m.addAttribute("sold", Mockito.any())).thenReturn(m);
		when(userAccountService.findByLoginMail(Mockito.anyString())).thenReturn(ua1);

		// ACT
		String ret = homeController.home(p, m);
		// ASSERT
		assertThat(ret).hasToString("home_page");
	}

	// with its data
	private static Stream<Arguments> HomeSource() {
		return Stream.of(Arguments.of("admin@gmail.com"), Arguments.of("j@gmail.com"), Arguments.of("toto@gmail.com"));
	}

	@ParameterizedTest
	@MethodSource("RegisterTransferSource")
	@DisplayName("Post register at transfer")
	void whenHomeWithRegister_thenReturnsNotNull(RedirectAttributes redirectAttributes, Model model, String firstName,
			String lastName, String loginMail, String psswrd) throws Exception {
		// ARRANGE
		UserAccount ua1 = new UserAccount(20L, "admin@gmail.com",
				"$2a$10$Yut7ko9yB0pbMMg2GaTMqOp6UtMoyhYWVqdYLk9Rk7/SOkNUA.u/y", "admin", "admin", 150D);
		when(userAccountService.registerNewUserAccount(null, loginMail, psswrd, firstName, lastName, 0D))
				.thenReturn(ua1);
		// ACT
		String ret = homeController.registerUserAccount(redirectAttributes, model, firstName, lastName, loginMail,
				psswrd);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");
	}

	// with its data
	private static Stream<Arguments> RegisterTransferSource() {
		return Stream.of(Arguments.of(null, null, "admin", "admin", "admin@gmail.com",
				"$2a$10$Yut7ko9yB0pbMMg2GaTMqOp6UtMoyhYWVqdYLk9Rk7/SOkNUA.u/y"));
	}

	@ParameterizedTest
	@MethodSource("RegisterIndexSource")
	@DisplayName("Post register at index")
	void whenHomeWithRegister_thenReturnsNull(RedirectAttributes redirectAttributes, Model model, String firstName,
			String lastName, String loginMail, String psswrd) throws Exception {
		// ARRANGE
//		UserAccount ua1 = null;
//		when(userAccountService.registerNewUserAccount(null, loginMail, psswrd, firstName, lastName, 0D))
//				.thenReturn(ua1);
		// ACT
		String ret = homeController.registerUserAccount(redirectAttributes, model, firstName, lastName, loginMail,
				psswrd);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");
	}

	// with its data
	private static Stream<Arguments> RegisterIndexSource() {
		return Stream.of(Arguments.of(null, null, "tito", "tito", "tito@gmail.com", "tito"));
	}

}

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

class princip implements Principal {
	@Override
	public String getName() {
		return "j@gmail.com";
	}
}
