package com.paymybuddy.pmb.TU.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.pmb.controller.BankAccountController;
import com.paymybuddy.pmb.service.impl.BankAccountService;
import com.paymybuddy.pmb.service.impl.MyUserDetailsService;
import com.paymybuddy.pmb.service.impl.UserAccountService;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = { BankAccountController.class })
class BankAccountControllerTest {
	public static final Logger logger = LogManager.getLogger(BankAccountControllerTest.class);

//	@Autowired
//	private MockMvc mockMvc;

//	@Autowired
//	private WebApplicationContext webApplicationContext;
	@InjectMocks
	BankAccountController bankAccountController;// = new BankAccountController();

	@Mock
	private MyUserDetailsService myUserDetailsService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private BankAccountService bankAccountService;

	@Mock
	private UserAccountService userAccountService;

	@BeforeEach
	void SetUpApplicationContext() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@ParameterizedTest
	@MethodSource("DeleteBankSource")
	@DisplayName("Delete bank")
	void whenDelete_thenReturnsNotNull(String loginMail) throws Exception {
		// ARRANGE

		// ACT
		String ret = bankAccountController.deleteBankGet(loginMail);
		// ASSERT
		assertThat(ret).hasToString("bank_page");

	}

	// with its data
	private static Stream<Arguments> DeleteBankSource() {
		return Stream.of(Arguments.of("j@gmail.com"), Arguments.of("admin@gmail.com"), Arguments.of(""));
	}

	@ParameterizedTest
	@MethodSource("AddBankSource")
	@DisplayName("Add bank")
	void whenAddBank_thenReturnsNotNull(Principal principal, RedirectAttributes ra, String bankName, String iban,
			String bic) throws Exception {
		// ARRANGE

		// ACT
		String ret = bankAccountController.addBankPost(principal, ra, bankName, iban, bic);
		// ASSERT
		assertThat(ret).hasToString("redirect:/bank");

	}

	// with its data
	private static Stream<Arguments> AddBankSource() {
		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();
		class redirAttrib implements RedirectAttributes {

			@Override
			public Model addAllAttributes(Map<String, ?> attributes) {
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

			@Override
			public RedirectAttributes addAttribute(String attributeName, Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RedirectAttributes addAttribute(Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RedirectAttributes addAllAttributes(Collection<?> attributeValues) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RedirectAttributes mergeAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RedirectAttributes addFlashAttribute(String attributeName, Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RedirectAttributes addFlashAttribute(Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, ?> getFlashAttributes() {
				// TODO Auto-generated method stub
				return null;
			}
		}
		redirAttrib ra = new redirAttrib();
		return Stream.of(Arguments.of(p, ra, "BNP", "12345678901234567890", "BNP1234"));
	}

	@ParameterizedTest
	@MethodSource("GetBankSource")
	@DisplayName("Get bank")
	void whenGetBank_thenReturnsNotNull(Principal principal, Model bankModel) {
		// ARRANGE
//		BankAccount ba = new BankAccount(null, "12345678901234567890", "BNP1234", "BNP", "j@gmail.com");
//		when(bankAccountService.findByLoginMail(principal.getName())).thenReturn(ba);
		// ACT
		String ret = bankAccountController.bankGet(principal, bankModel);
		// ASSERT
		assertThat(ret).hasToString("bank_page");
	}

	// with its data
	private static Stream<Arguments> GetBankSource() {
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
		return Stream.of(Arguments.of(p, m));
	}

	@ParameterizedTest
	@MethodSource("AddSoldSource")
	@DisplayName("Add sold")
	void whenAddSold_thenReturnsNotNull(Principal principal, Integer sold) {
		// ARRANGE
		// ACT
		String ret = bankAccountController.addSoldPost(principal, sold);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");
	}

	// with its data
	private static Stream<Arguments> AddSoldSource() {
		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();

		return Stream.of(Arguments.of(p, 0), Arguments.of(p, 10), Arguments.of(p, Integer.MAX_VALUE));
	}

	@ParameterizedTest
	@MethodSource("AddSoldNullSource")
	@DisplayName("Add sold null")
	void whenAddSold_thenReturnsNull(Principal principal, Integer sold) {
		// ARRANGE
		// ACT
		String ret = bankAccountController.addSoldPost(principal, sold);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");
	}

	// with its data
	private static Stream<Arguments> AddSoldNullSource() {
		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();

		return Stream.of(Arguments.of(p, -1), Arguments.of(p, Integer.MIN_VALUE),
				Arguments.of(null, Integer.MIN_VALUE));
	}

	@ParameterizedTest
	@MethodSource("SubstractSoldSource")
	@DisplayName("Substract sold")
	void whenSubstractSold_thenReturnsNotNull(Principal principal, Integer sold) {
		// ARRANGE
		// ACT
		String ret = bankAccountController.substractSoldPost(principal, sold);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");
	}

	// with its data
	private static Stream<Arguments> SubstractSoldSource() {
		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();

		return Stream.of(Arguments.of(p, 0), Arguments.of(p, 10), Arguments.of(p, Integer.MAX_VALUE));
	}

	@ParameterizedTest
	@MethodSource("SubstractSoldNullSource")
	@DisplayName("Substract sold null")
	void whenSubstractSold_thenReturnsNull(Principal principal, Integer sold) {
		// ARRANGE
		// ACT
		String ret = bankAccountController.substractSoldPost(principal, sold);
		// ASSERT
		assertThat(ret).hasToString("redirect:/transfer");
	}

	// with its data
	private static Stream<Arguments> SubstractSoldNullSource() {
		class princip implements Principal {
			@Override
			public String getName() {
				return "j@gmail.com";
			}
		}
		princip p = new princip();

		return Stream.of(Arguments.of(p, -1), Arguments.of(p, Integer.MIN_VALUE),
				Arguments.of(null, Integer.MIN_VALUE));
	}
}