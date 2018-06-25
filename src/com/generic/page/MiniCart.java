package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.generic.selector.MiniCartSelectors;
import com.generic.setup.SelTestCase;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.LoggingMsg;
import com.generic.util.SelectorUtil;

public class MiniCart extends SelTestCase {

	public static class keys {

		public static final String invalidCoupon = "invalid";

	}

	// done -ocm
	public static void clickMiniCartButton() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_CART_BUTTON, "mini cart"));
			subStrArr.add(MiniCartSelectors.miniCartIcon);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-OCM
	public static String getCartInformation() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			subStrArr.add(MiniCartSelectors.cartInfo);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			String itemInfo = SelectorUtil.textValue.get();
			logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_PRODUCTS, itemInfo));
			getCurrentFunctionName(false);
			return itemInfo;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-OCM
	public static boolean checkMinicartImage() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			subStrArr.add(MiniCartSelectors.itemImage);
			boolean exist = SelectorUtil.isDisplayed(subStrArr);
			logs.debug("checking item image from mini cart");
			getCurrentFunctionName(false);
			return exist;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-OCM
	public static boolean checkCheckoutButton() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			subStrArr.add(MiniCartSelectors.checkoutButton);
			boolean exist = SelectorUtil.isDisplayed(subStrArr);
			logs.debug("checking item image from mini cart");
			getCurrentFunctionName(false);
			return exist;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

}// calss
