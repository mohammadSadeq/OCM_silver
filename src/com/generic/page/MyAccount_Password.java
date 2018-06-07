package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.generic.selector.MyAccount_PasswordSelectors;
import com.generic.setup.SelTestCase;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.LoggingMsg;
import com.generic.util.SelectorUtil;

public class MyAccount_Password extends SelTestCase {

	public static class keys {
		public static final String caseId = "caseId";
	}

	public static void clickCancelBtn() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_PasswordSelectors.cancelBtn));
			subStrArr.add(MyAccount_PasswordSelectors.cancelBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickUpdateBtn() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_PasswordSelectors.updateBtn));
			subStrArr.add(MyAccount_PasswordSelectors.updateBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void typeCurrentPassword(String currentPassword) throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_PasswordSelectors.currentPassword, currentPassword));
			subStrArr.add(MyAccount_PasswordSelectors.currentPassword);
			valuesArr.add(currentPassword);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void typeNewPassword(String newPassword) throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, MyAccount_PasswordSelectors.newPassword,
					newPassword));
			subStrArr.add(MyAccount_PasswordSelectors.newPassword);
			valuesArr.add(newPassword);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void typeConfirmNewPassword(String confirmNewPassword) throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_PasswordSelectors.confirmNewPassword, confirmNewPassword));
			subStrArr.add(MyAccount_PasswordSelectors.confirmNewPassword);
			valuesArr.add(confirmNewPassword);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void fillInNewValuesAndClickUpdateOrCancel(String currentPassword, String newPassword,
			String confirmNewPassword, boolean doUpdate, boolean doCancel) throws Exception {
		try {
			getCurrentFunctionName(true);
			typeCurrentPassword(currentPassword);
			typeNewPassword(newPassword);
			typeConfirmNewPassword(confirmNewPassword);
			if (doUpdate) {
				clickUpdateBtn();
			}
			if (doCancel) {
				clickCancelBtn();
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static String getCurrentPasswordErrorrMsg(boolean alert) throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
					MyAccount_PasswordSelectors.currentPasswordError));
			if (!alert)
				subStrArr.add(MyAccount_PasswordSelectors.currentPasswordError);
			else
				subStrArr.add(MyAccount_PasswordSelectors.currentPasswordError2);

			valuesArr.add("noClick");
			String currentPasswordErrorMsg = "";
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			currentPasswordErrorMsg = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return currentPasswordErrorMsg;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	public static String getNewPasswordErrorMsg(boolean alert) throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
					MyAccount_PasswordSelectors.newPasswordError));
			if (!alert)

				subStrArr.add(MyAccount_PasswordSelectors.newPasswordError);
			else
				subStrArr.add(MyAccount_PasswordSelectors.newPasswordError2);
			valuesArr.add("noClick");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);

			String newPasswordErrorMsg = "";
			newPasswordErrorMsg = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return newPasswordErrorMsg;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static String getConfirmNewPasswordErrorMsg(boolean alert) throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
					MyAccount_PasswordSelectors.confirmNewPasswordError));
			String confirmNewPasswordErrorMsg = "";
			if (!alert)
				subStrArr.add(MyAccount_PasswordSelectors.confirmNewPasswordError);
			else
				subStrArr.add(MyAccount_PasswordSelectors.confirmNewPasswordError2);
			valuesArr.add("noClick");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);

			confirmNewPasswordErrorMsg = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return confirmNewPasswordErrorMsg;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static String getGlobalAlertsMsg() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(
					MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_PasswordSelectors.globalAlerts));
			subStrArr.add(MyAccount_PasswordSelectors.globalAlerts);
			valuesArr.add("noClick");
			String globalAlertMsg = "";
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			globalAlertMsg = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return globalAlertMsg;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
}
