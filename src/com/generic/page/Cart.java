package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebElement;

import com.generic.selector.CartSelectors;
import com.generic.setup.SelTestCase;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.util.SelectorUtil;

public class Cart extends SelTestCase {

	public static class keys {

		public static final String invalidCoupon = "invalid";

	}

	
	public static void clickCheckout() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_CART_BUTTON, "checkout"));
			subStrArr.add(CartSelectors.checkoutBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	
	public static void clickContinueShoping() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_CART_BUTTON, "continue shopping"));
			subStrArr.add(CartSelectors.continueShopping);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-ocm
	public static String getNumberOfproducts() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			subStrArr.add(CartSelectors.numberOfProducts);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			String products = SelectorUtil.textValue.get();
			logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_PRODUCTS, products));
			getCurrentFunctionName(false);
			return products.split(" ")[3].split("[(]")[1];
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
	
	//TODO: delete
	public static String getProductQty(String browser, int lineOrder) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		if (browser.contains("mobile"))
			subStrArr.add(CartSelectors.productQtyBoxMobile+lineOrder);
		else
			subStrArr.add(CartSelectors.productQtyBox+lineOrder);
		valuesArr.add("getCurrentValue");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_PRODUCTS, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}
	
	//TODO: delete
	public static String checkProductsExsist() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.numberOfProducts);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_PRODUCTS, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}
	
	// done-ocm
	public static String getProductUnitPrice() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			subStrArr.add(CartSelectors.unitPrice);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			logs.debug("product unit price is: " + SelectorUtil.textValue.get());
			String totals = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return totals;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
	
	// done-ocm
	public static String getProductItemSubtotal() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			if (!getBrowserName().contains("mobile"))
				subStrArr.add(CartSelectors.productSubtotal);
			else
				subStrArr.add(CartSelectors.productSubtotalMobile);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			logs.debug("product subtotal is: " + SelectorUtil.textValue.get());
			String totals = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return totals.replace("Total: ", "");
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}	
	
	public static String getTotals() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.totals);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.totalsMsg, SelectorUtil.textValue.get()));
		String totals = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return totals;
	}

	
	// done-ocm
	public static String getOrderSubTotal() throws Exception {
		try {
			getCurrentFunctionName(true);
			String cartTotals = getCartTotals();
			String totals = cartTotals.split("\n")[5].trim();
			logs.debug("order subtotal is: " + totals);
			getCurrentFunctionName(false);
			return totals;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-ocm
	public static String getCartTotals() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			subStrArr.add(CartSelectors.cartTotals);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			logs.debug("cart totls: " + SelectorUtil.textValue.get());
			String totals = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return totals;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
	// done-ocm
	public static String getItemSubTotal() throws Exception {
		try {
			getCurrentFunctionName(true);
			String cartTotals = getCartTotals();
			String totals = cartTotals.split("\n")[1].trim();
			logs.debug("item subtotal: " + totals);
			getCurrentFunctionName(false);
			return totals;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
	
	public static String getPromotionalDiscount() throws Exception {
		getCurrentFunctionName(true);
		String totals = getTotals();
		getCurrentFunctionName(false);
		return totals.split("\n")[5].trim();

	}
	
	// done-ocm
	public static String getOrderDiscount() throws Exception {
		try {
			getCurrentFunctionName(true);
			String cartTotals = getCartTotals();
			String totals = cartTotals.split("\n")[3].trim();
			logs.debug("order discounts is: " + totals);
			getCurrentFunctionName(false);
			return totals;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
	
		public static String getOrderClubDicount() throws Exception {
			getCurrentFunctionName(true);
			String totals = getTotals();
			getCurrentFunctionName(false);
			return totals.split("\n")[7].trim();

		}
	
	
	public static void applyPromotion(String promotion) throws Exception {
		getCurrentFunctionName(true);
		if (!"".equals(promotion)) {
			logs.debug(MessageFormat.format(LoggingMsg.APPLYING_COUPON, "Applying", promotion));
			writeCoupon(promotion);
			clickApplycoupon();
		} else {
			logs.debug(MessageFormat.format(LoggingMsg.APPLYING_COUPON, "cannot apply", promotion));
		}
		getCurrentFunctionName(false);
	}
	
	//Not included 
	public static void removeCoupon() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.removeCoupon);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	
	private static void clickApplycoupon() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.applyCouponButton);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}
	
	
	private static void writeCoupon(String coupon) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.couponField);
		valuesArr.add(coupon);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	
	public static String validateCoupon() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		// subStrArr.add(CartSelectors.couponErrorMessage);
		subStrArr.add(CartSelectors.couponMessage);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		logs.debug(MessageFormat.format(LoggingMsg.COUPON_MSG, SelectorUtil.textValue.get()));
		return SelectorUtil.textValue.get();
	}

	public static String getCouponGlobalMessage() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.couponGlobalMessage);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		logs.debug(MessageFormat.format(LoggingMsg.COUPON_MSG, SelectorUtil.textValue.get()));
		return SelectorUtil.textValue.get();
	}
	 
	public static void updateQuantityValue(String browser, String lineOrder, String qty) throws Exception {
		// Limited to edit first product qty
		getCurrentFunctionName(true);
		writeNewQunatity(browser, lineOrder, qty);
		clickUpdateBtn();
		getCurrentFunctionName(false);
	}
	
	
	private static void clickUpdateBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.updateBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	
	private static void writeNewQunatity(String browser, String lineOrder, String qty) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.productQtyBox+lineOrder);
		valuesArr.add(qty);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	
	public static String getErrorMsg() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		try {
			subStrArr.add(CartSelectors.errorMessage);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		} catch (Exception e) {
			// to make sure the application is throwing the correct exception
			if (ExceptionMsg.noValidSelector.contains(e.getMessage()))
				throw new Exception(ExceptionMsg.noErrorMsg);
			else
				throw new Exception(e);
		}
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	
	public static String getCartMsg() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		try {
			subStrArr.add(CartSelectors.postitiveMsg);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		} catch (Exception e) {
			// to make sure the application is throwing the correct exception
			if (ExceptionMsg.noValidSelector.contains(e.getMessage()))
				throw new Exception(ExceptionMsg.noErrorMsg);
			else
				throw new Exception(e);
		}
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	
	
	public static boolean isCartEmpty() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.cartContent);
		valuesArr.add("");
		try {
			SelectorUtil.textValue.set("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		} catch (Exception e) {
			logs.debug(MessageFormat.format(LoggingMsg.EMPTY_CART_ERROR_MSG, e.getMessage()));
		}
		getCurrentFunctionName(false);
		return (SelectorUtil.textValue.get().contains("empty") ? true : false);
	}

	
	public static void removeAllItemsFromCart() throws Exception {
		getCurrentFunctionName(true);

		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		
		subStrArr.add(CartSelectors.numberOfProducts);
		valuesArr.add("");
		
		List<WebElement> removeButtons = SelectorUtil.getAllElements(subStrArr);
		
		int numberOfItems = removeButtons.size();
		logs.debug(LoggingMsg.REMOVE_ALL_ITEMS_FROM_CART);
		for (int itemIndex = numberOfItems; itemIndex > 0; itemIndex--)
		{
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		}

		getCurrentFunctionName(false);
	}

	
	public static void removeItemFromCart(int itemIndex) throws Exception {
		getCurrentFunctionName(true);
		clickOnactionMenue(itemIndex);
		clickOnRemove(itemIndex);
		getCartMsg();
		getCurrentFunctionName(false);
	}

	
	private static void clickOnRemove(int itemIndex) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.removeItem + itemIndex);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	
	private static void clickOnactionMenue(int itemIndex) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.actionMenue + itemIndex);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	
	public static String getOrdarshipping() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.cartOrderShipping);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug("Order shipping: " + SelectorUtil.textValue.get());
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}
	
	
	public static boolean checkItemImage() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.itemImages);
		
		boolean isDisplayed = SelectorUtil.isDisplayed(subStrArr);
		logs.debug("images check result is " + isDisplayed);
		getCurrentFunctionName(false);
		return isDisplayed;
	}
	
	
	public static boolean checkProductLink(String PLink) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.itemLink);
		String attrValue =SelectorUtil.getAttr(subStrArr, "href");
		logs.debug("links check result is: " + attrValue);
		logs.debug("links is: " + PLink);
		getCurrentFunctionName(false);
		return PLink.contains(attrValue);
	}

	//done-ocm
	public static String getCartUrl() {
		try {
			getCurrentFunctionName(true);
			logs.debug("heading to Cart page");
			String PDPurl = getCONFIG().getProperty("HomePage") + PagesURLs.getShoppingCartPage();
			getCurrentFunctionName(false);
			return PDPurl;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
}
