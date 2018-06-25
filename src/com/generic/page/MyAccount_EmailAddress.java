package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.generic.selector.MyAccount_EmailAddressSelectors;
import com.generic.setup.SelTestCase;
import com.generic.setup.LoggingMsg;
import com.generic.util.SelectorUtil;

public class MyAccount_EmailAddress extends SelTestCase {

	public static class keys {
		public static final String caseId = "caseId";
	}

	public static String getEmailValue() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_EmailAddressSelectors.emailInput));
		subStrArr.add(MyAccount_EmailAddressSelectors.emailInput);
		valuesArr.add("getCurrentValue");
		String email = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		email = SelectorUtil.textValue.get();
		logs.debug("the email address found in my account page: " + email);
		getCurrentFunctionName(false);
		return email;
	}

	public static void clickCancelBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_EmailAddressSelectors.cancelBtn));
		subStrArr.add(MyAccount_EmailAddressSelectors.cancelBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void clickUpdateBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_EmailAddressSelectors.updateBtn));
		subStrArr.add(MyAccount_EmailAddressSelectors.updateBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void typeEmail(String email) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, MyAccount_EmailAddressSelectors.emailInput,
				email));
		subStrArr.add(MyAccount_EmailAddressSelectors.emailInput);
		valuesArr.add(email);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void typeConfirmEmail(String checkEmail) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
				MyAccount_EmailAddressSelectors.confirmEmailInput, checkEmail));
		subStrArr.add(MyAccount_EmailAddressSelectors.confirmEmailInput);
		valuesArr.add(checkEmail);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void typePassword(String password) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, MyAccount_EmailAddressSelectors.passwordInput,
				password));
		subStrArr.add(MyAccount_EmailAddressSelectors.passwordInput);
		valuesArr.add(password);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void fillInNewValuesAndClickUpdateOrCancel(String newEmail, String confirmEmail, String password,
			boolean doUpdate, boolean doCancel) throws Exception {
		getCurrentFunctionName(true);
		typeEmail(newEmail);
		typeConfirmEmail(confirmEmail);
		typePassword(password);
		if (doUpdate) {
			clickUpdateBtn();
		}
		if (doCancel) {
			clickCancelBtn();
		}
		getCurrentFunctionName(false);
	}

	public static String getEmailErrorMsg() throws Exception {
		getCurrentFunctionName(true);
		Thread.sleep(1000);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_EmailAddressSelectors.emailError));
		subStrArr.add(MyAccount_EmailAddressSelectors.emailError);
		valuesArr.add("noClick");
		String emailErrorMsg = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		emailErrorMsg = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return emailErrorMsg;
	}

	public static String getConfirmEmailErrorMsg() throws Exception {
		getCurrentFunctionName(true);
		Thread.sleep(1000);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
				MyAccount_EmailAddressSelectors.confirmEmailError));
		subStrArr.add(MyAccount_EmailAddressSelectors.confirmEmailError);
		valuesArr.add("noClick");
		String confirmEmailErrorMsg = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		confirmEmailErrorMsg = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return confirmEmailErrorMsg;
	}

	public static String getPasswordErrorMsg() throws Exception {
		getCurrentFunctionName(true);
		Thread.sleep(1000);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(
				MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_EmailAddressSelectors.passwordError));
		subStrArr.add(MyAccount_EmailAddressSelectors.passwordError);
		valuesArr.add("noClick");
		String passwordErrorMsg = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		passwordErrorMsg = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return passwordErrorMsg;
	}

	public static String getGlobalAlertsMsg() throws Exception {
		getCurrentFunctionName(true);
		Thread.sleep(1000);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(
				MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_EmailAddressSelectors.globalAlerts));
		subStrArr.add(MyAccount_EmailAddressSelectors.globalAlerts);
		valuesArr.add("");
		String globalAlertMsg = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		globalAlertMsg = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return globalAlertMsg;
	}
}
