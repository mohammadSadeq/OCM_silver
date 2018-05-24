package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebElement;

import com.generic.selector.CartSelectors;
import com.generic.selector.PayPalSelectors;
import com.generic.setup.SelTestCase;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.util.SelectorUtil;

public class PayPal extends SelTestCase {

	public static class keys {

		public static final String invalidCoupon = "invalid";

	}
	
	// done -ocm
	public static void clickLoginLink() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug("clickign on login btn");
			subStrArr.add(PayPalSelectors.loginLink);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));

		}

	}
	
	// done -ocm
	public static void signInAndClickContinue(String userName, String Password) throws Exception {
		try {
			getCurrentFunctionName(true);
			signIn(userName, Password);
			Thread.sleep(15000);
			clickOnContinue();
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done -ocm
	public static void signIn(String userName, String Password) throws Exception {
		try {
			getCurrentFunctionName(true);
			Thread.sleep(3000);
			typeUserName(userName);
			Thread.sleep(3000);
			clickNext();
			Thread.sleep(3000);
			typePassword(Password);
			Thread.sleep(3000);
			clickLogin();
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
	
	// done -ocm
	private static void clickNext() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug("clickign on next btn");
			subStrArr.add(PayPalSelectors.nextBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			
		}

	}
	
	// done -ocm
	private static void clickLogin() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug("clickign on login btn");
			subStrArr.add(PayPalSelectors.loginBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
	// done -ocm
	public static void clickOnContinue() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug("clickign on continue btn");
			subStrArr.add(PayPalSelectors.continueBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	//done-ocm
	private static void typePassword(String Password) throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug("type password");
			subStrArr.add(PayPalSelectors.password);
			valuesArr.add(Password);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	//done-ocm
	private static void typeUserName(String userName) throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug("type username");
			subStrArr.add(PayPalSelectors.userName);
			valuesArr.add(userName);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

}
