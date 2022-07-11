package com.paymybuddy.pmb.TU.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.pmb.controller.TransacController;
import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.impl.MyUserDetailsService;
import com.paymybuddy.pmb.service.impl.TransacService;
import com.paymybuddy.pmb.service.impl.UserAccountService;

@ExtendWith(SpringExtension.class)
class TransacControllerTest {
	public static final Logger logger = LogManager.getLogger(TransacControllerTest.class);

	@InjectMocks
	TransacController transacController;

	@Mock
	private MyUserDetailsService myUserDetailsService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserAccountService userAccountService;

	@Mock
	private TransacService transacService;

//	@Mock
//	private SecurityContextHolder securityContextHolder;

	@BeforeEach
	void SetUpApplicationContext() {
	}

	@Test
	@DisplayName("Get all connections")
	void whenGetAllConnections_thenReturnsNotNull() throws Exception {
		// ARRANGE
		UserAccount ua1 = new UserAccount(null, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 12D);
		UserAccount ua2 = new UserAccount(null, "c@gmail.com",
				"$2a$10$5uEcL2Kn6I0ZurSun1Vzu.yxDJQjJsr0B7zKo/GgtGyMX18AAC7gi", "Gustave", "Caillebotte", 14D);
		List<UserAccount> lua = new ArrayList<>();
		lua.add(ua1);
		lua.add(ua2);
		when(userAccountService.findAllUserAccounts()).thenReturn(lua);
		// ACT
		Set<String> ret = transacController.getAllConnections();
		// ASSERT
		assertThat(ret).isNotNull();

	}

	@ParameterizedTest
	@MethodSource("ConnectionsOfOneUserSource")
	@DisplayName("Get connections of one user")
	void whenGetConnectionsOfOneUser_thenReturnsNotNull(Principal principal) throws Exception {
		// ARRANGE
		UserAccount ua1 = new UserAccount(null, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 12D);
		UserAccount ua2 = new UserAccount(null, "c@gmail.com",
				"$2a$10$5uEcL2Kn6I0ZurSun1Vzu.yxDJQjJsr0B7zKo/GgtGyMX18AAC7gi", "Gustave", "Caillebotte", 14D);
		UserAccount ua3 = new UserAccount(null, "u@gmail.com",
				"$2a$10$NS5VOwqQjTWYrRbEBCp/cekXxd.B5I3.9MgxV69nN/NoOOo.nbQ4e", "Maurice", "Utrillo", 16D);
		List<UserAccount> lua = new ArrayList<>();
		lua.add(ua1);
		lua.add(ua2);
		lua.add(ua3);
		when(userAccountService.findByLoginMail(Mockito.anyString())).thenReturn(ua1);
		when(userAccountService.retrieveConxUserAccount(Mockito.any(UserAccount.class))).thenReturn(lua);

		// ACT
		List<String> connections = transacController.getConnectionsOfOneUser(principal);
		// ASSERT
		assertThat(connections).isNotNull();

	}

	// with its data
	private static Stream<Arguments> ConnectionsOfOneUserSource() {
		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();
		return Stream.of(Arguments.of(p));
	}

	@ParameterizedTest
	@MethodSource("GetTransferSource")
	@DisplayName("Get transfer")
	void whenGetTransfer_thenReturnsNotNull(Model model) throws Exception {
		// ARRANGE
		Transac t1 = new Transac(null, "description1", 12D, "j@gmail.com", "q@gmail.com");
		Transac t2 = new Transac(null, "description2", 22D, "j@gmail.com", "c@gmail.com");
		Transac t3 = new Transac(null, "description3", 24D, "u@gmail.com", "c@gmail.com");
		List<Transac> lt = new ArrayList<>();
		lt.add(t1);
		lt.add(t2);
		List<Transac> ltOfReceiver = new ArrayList<>();
		ltOfReceiver.add(t3);

		class authentic implements Authentication {
			private static final long serialVersionUID = 3751886761975705356L;

			@Override
			public String getName() {
				return "j@gmail.com";
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getCredentials() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getDetails() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isAuthenticated() {
				return true;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				// TODO Auto-generated method stub

			}
		}
		authentic auth = new authentic();
		auth.setAuthenticated(true);

		class secuCtx implements SecurityContext {
			private static final long serialVersionUID = 1L;

			@Override
			public Authentication getAuthentication() {
				// TODO Auto-generated method stub
				return auth;
			}

			@Override
			public void setAuthentication(Authentication authentication) {
				// TODO Auto-generated method stub

			}
		}
		secuCtx sc = new secuCtx();

		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);
		when(transacService.findAllTransactionsByGiver(Mockito.anyString())).thenReturn(lt);
		when(transacService.findAllTransactionsByReceiver(Mockito.anyString())).thenReturn(ltOfReceiver);

		// ACT
		String ret = transacController.transferGet(model);

		// ASSERT
		assertThat(ret).hasToString("transfer_page");

	}

	// with its data
	private static Stream<Arguments> GetTransferSource() {
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
		return Stream.of(Arguments.of(m));
	}

	@ParameterizedTest
	@MethodSource("addConnectionGetSource")
	@DisplayName("Add connections get")
	void whenAddConnectionsGet_thenReturnsNotNull(Principal principal, Model model) throws Exception {
		// ARRANGE
		UserAccount sender = new UserAccount(null, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 12D);
		UserAccount ua1 = new UserAccount(null, "a@gmail.com",
				"$2a$10$ihxFsA4jcg2rzKg0szzxxezZNH2GdKeOo3PnOtYUx.mbbAcA1kzsq", "Alexis", "Axilette", 11D);
		UserAccount ua2 = new UserAccount(null, "c@gmail.com",
				"$2a$10$5uEcL2Kn6I0ZurSun1Vzu.yxDJQjJsr0B7zKo/GgtGyMX18AAC7gi", "Gustave", "Caillebotte", 14D);
		UserAccount ua3 = new UserAccount(null, "u@gmail.com",
				"$2a$10$NS5VOwqQjTWYrRbEBCp/cekXxd.B5I3.9MgxV69nN/NoOOo.nbQ4e", "Maurice", "Utrillo", 16D);
		List<UserAccount> allUa = new ArrayList<>();
		allUa.add(sender);
		allUa.add(ua1);
		allUa.add(ua2);
		allUa.add(ua3);
		List<UserAccount> listOfConxOfSender = new ArrayList<>();
		listOfConxOfSender.add(ua2);
		when(userAccountService.findAllUserAccounts()).thenReturn(allUa);
		when(userAccountService.findByLoginMail(Mockito.anyString())).thenReturn(sender);

		when(userAccountService.retrieveConxUserAccount(Mockito.any(UserAccount.class))).thenReturn(listOfConxOfSender);

		// ACT
		String ret = transacController.addConnectionGet(principal, model);
		// ASSERT
//		assertThat(connection).isNotNull();
		assertThat(ret).hasToString("/addConnection_page");

	}

	// with its data
	private static Stream<Arguments> addConnectionGetSource() {
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

		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();
		return Stream.of(Arguments.of(p, m));
	}

	@ParameterizedTest
	@MethodSource("addConnectionPostSource")
	@DisplayName("Add connections post")
	void whenAddConnectionsPost_thenReturnsNotNull(Principal principal, String conxMail) throws Exception {
		// ARRANGE
		UserAccount sender = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 12D);
		UserAccount receiver = new UserAccount(2L, "a@gmail.com",
				"$2a$10$ihxFsA4jcg2rzKg0szzxxezZNH2GdKeOo3PnOtYUx.mbbAcA1kzsq", "Alexis", "Axilette", 11D);

		when(userAccountService.findByLoginMail(sender.getLoginMail())).thenReturn(sender);
		when(userAccountService.findByLoginMail(Mockito.anyString())).thenReturn(receiver);
		when(userAccountService.addConxUserAccount(sender, receiver.getId())).thenReturn(sender);

		// ACT
		String ret = transacController.addConnectionPost(principal, conxMail);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");

	}

	// with its data
	private static Stream<Arguments> addConnectionPostSource() {

		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();
		return Stream.of(Arguments.of(p, "a@gmail.com"));
	}

	@ParameterizedTest
	@MethodSource("deleteConnectionPostSource")
	@DisplayName("Delete connections post")
	void whenDeleteConnectionsPost_thenReturnsNotNull(Principal principal, String conxMail) throws Exception {
		// ARRANGE
		UserAccount sender = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 12D);
		UserAccount receiver = new UserAccount(2L, "a@gmail.com",
				"$2a$10$ihxFsA4jcg2rzKg0szzxxezZNH2GdKeOo3PnOtYUx.mbbAcA1kzsq", "Alexis", "Axilette", 11D);

		when(userAccountService.findByLoginMail(Mockito.anyString())).thenReturn(sender);
		when(userAccountService.deleteConxUserAccount(sender, conxMail)).thenReturn(sender);

		// ACT
		String ret = transacController.deleteConnectionGet(principal, conxMail);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");

	}

	// with its data
	private static Stream<Arguments> deleteConnectionPostSource() {

		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();
		return Stream.of(Arguments.of(p, "a@gmail.com"));
	}

	@ParameterizedTest
	@MethodSource("transferPostSource")
	@DisplayName("Transfer post")
	void whenTransferPost_thenReturnsNotNull(RedirectAttributes redirectAttributes, Principal principal,
			String userConx, String description, Double amount) throws Exception {
		// ARRANGE
		Transac t1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");

		when(userAccountService.transferMoneyUserAccount(principal.getName(), userConx, description, amount))
				.thenReturn(t1);

		// ACT
		String ret = transacController.transferPost(redirectAttributes, principal, userConx, description, amount);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");

	}

	// with its data
	private static Stream<Arguments> transferPostSource() {

		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();
		return Stream.of(Arguments.of(null, p, "a@gmail.com", "musée", 26D));
	}
}