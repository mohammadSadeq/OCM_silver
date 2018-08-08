package com.generic.tests.checkout;

import java.text.MessageFormat;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.LinkedHashMap;

import com.generic.page.PDP;
import com.generic.page.Registration;
import com.generic.page.Cart;
import com.generic.page.CheckOut;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.GlobalVariables;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.dataProviderUtils;
import com.generic.util.RandomUtilities;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class Base_checkout extends SelTestCase {

	// user types
	public static final String guestUser = "guest";
	public static final String freshUser = "fresh";
	public static final String loggedInUser = "Loggedin";
	public static final String loggedDuringChcOt = "logging During Checkout";

	public static boolean external = false; // change this value will pass through logging

	// used sheet in test
	public static final String testDataSheet = SheetVariables.checkoutSheet;

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger("checkout_setup"));
		testObject = test;
	}

	@DataProvider(name = "Orders", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency maintenance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));

		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "Orders")
	public void checkOutBaseTest(String caseId, String runTest, String desc, String proprties, String products,
			String shippingMethod, String payment, String shippingAddress, String billingAddress, String coupon,
			String email) throws Exception {

		if (!external) { // this logic to avoid passing this block in case you call it from other class
			// Important to add this for logging/reporting
			Testlogs.set(new SASLogger("checkout_" + getBrowserName()));
			setTestCaseReportName("Checkout Case");
			logCaseDetailds(MessageFormat.format(LoggingMsg.CHECKOUTDESC, testDataSheet + "." + caseId,
					this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- "), payment,
					shippingMethod));
		} // if not external

		LinkedHashMap<String, String> addressDetails = (LinkedHashMap<String, String>) addresses.get(shippingAddress);

		String Pemail;
		String orderId;
		String orderTotal;
		String orderSubtotal;
		String orderTax;
		String orderShipping;

		Pemail = "";
		LinkedHashMap<String, String> userdetails = null;
		if (!email.equals("")) {
			userdetails = (LinkedHashMap<String, String>) users.get(email);
			Pemail = getSubMailAccount(userdetails.get(Registration.keys.email));
			Testlogs.get().debug("Mail will be used is: " + Pemail);
		}

		try {
			if (proprties.contains(loggedInUser)) {
				// you need to maintain the concurrency and get the main account information and
				// log in in browser account
				Testlogs.get().debug(Pemail);
				Testlogs.get().debug((String) userdetails.get(Registration.keys.password));
				SignIn.logIn(Pemail, (String) userdetails.get(Registration.keys.password));
			}
			if (proprties.contains(freshUser)) {
				Pemail = RandomUtilities.getRandomEmail();

				// take any user as template
				LinkedHashMap<String, Object> RandomUserdetails = (LinkedHashMap<String, Object>) users.entrySet()
						.iterator().next().getValue();
				
				Registration.goToRegistrationForm();
				if (getBrowserName().equals(GlobalVariables.browsers.IE))
					Thread.sleep(6000);
				Registration.fillAndClickRegister((String) RandomUserdetails.get(Registration.keys.firstName),
						(String) RandomUserdetails.get(Registration.keys.lastName), Pemail, "Elmira College",
						(String) RandomUserdetails.get(Registration.keys.password),
						(String) RandomUserdetails.get(Registration.keys.password), "", addressDetails);
			}
			if (getBrowserName().equals(GlobalVariables.browsers.IE))
				Thread.sleep(6000);
			for (String product : products.split("\n")) {
				Testlogs.get().debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
				LinkedHashMap<String, String> productDetails = (LinkedHashMap<String, String>) invintory.get(product);
				PDP.addProductsToCartAndClickCheckOut(productDetails);
			}

			// flow to support coupon validation
			if (!"".equals(coupon)) {
				Cart.applyPromotion(coupon);
				if (coupon.contains(Cart.keys.invalidCoupon)) {
					Cart.validateCoupon();
				}
			}
			orderSubtotal = Cart.getItemSubTotal();
			Cart.selectShippingMethod(shippingMethod);
			if (getBrowserName().equals("IE"))
				Thread.sleep(1000);

			if (proprties.contains(loggedDuringChcOt)) {
				Testlogs.get().debug("Login during checkout with: " + Pemail);
				Testlogs.get().debug("Using password: " + (String) userdetails.get(Registration.keys.password));
				CheckOut.guestCheckout.clickOnHaveAnAccount();
				CheckOut.guestCheckout.returningCustomerLogin(Pemail,
						(String) userdetails.get(Registration.keys.password));
			} else {
				Cart.clickCheckout();
			}

			Thread.sleep(3000);
			if (getBrowserName().equals(GlobalVariables.browsers.IE) || getBrowserName().equals(GlobalVariables.browsers.firefox))
				Thread.sleep(6000);

			// OCM-custom
			CheckOut.shippingAddress.fillAndClickNext(addressDetails.get(CheckOut.shippingAddress.keys.firstName),
					addressDetails.get(CheckOut.shippingAddress.keys.lastName),
					addressDetails.get(CheckOut.shippingAddress.keys.phone), Pemail,
					addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
					addressDetails.get(CheckOut.shippingAddress.keys.city),
					addressDetails.get(CheckOut.shippingAddress.keys.city),
					addressDetails.get(CheckOut.shippingAddress.keys.zipcode));

			LinkedHashMap<String, String> paymentDetails = (LinkedHashMap<String, String>) paymentCards.get(payment);
			LinkedHashMap<String, String> billAddressDetails = (LinkedHashMap<String, String>) addresses
					.get(billingAddress);

			if (getBrowserName().equals(GlobalVariables.browsers.IE)|| getBrowserName().equals(GlobalVariables.browsers.firefox))
				Thread.sleep(6000);
			// fill billing form and click checkout
			CheckOut.paymentInnformation.fillAndclickNext(payment, "Tester",
					paymentDetails.get(CheckOut.paymentInnformation.keys.number),
					paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
					paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
					paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC),
					billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
					billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
					billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
					billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
					billAddressDetails.get(CheckOut.shippingAddress.keys.city),
					billAddressDetails.get(CheckOut.shippingAddress.keys.zipcode),
					billAddressDetails.get(CheckOut.shippingAddress.keys.phone));

			Thread.sleep(1000);
			// Waiting payment to be processed
			if (getBrowserName().equals(GlobalVariables.browsers.firefox))
				Thread.sleep(2000);
			
			if (!external) { // this logic to avoid passing this block in case you call it from other class
				if (getBrowserName().equals(GlobalVariables.browsers.IE))
					Thread.sleep(10000);
				ReportUtil.takeScreenShot(getDriver(), testDataSheet + "_" + caseId);
				
				String billingAddressDetails = CheckOut.orderConfirmation.getBillingAddrerss();
				sassert()
						.assertTrue(
								billingAddressDetails.toLowerCase()
										.contains(addressDetails.get(CheckOut.shippingAddress.keys.adddressLine).trim()
												.toLowerCase()),
								"Error in billing address from order confirmation page.<br> the actual address: "
										+ billingAddressDetails + "<br>and expected: "
										+ addressDetails.get(CheckOut.shippingAddress.keys.adddressLine));

				if (getBrowserName().equals(GlobalVariables.browsers.IE))
					Thread.sleep(4000);
				String shippinhAddressDetails = CheckOut.orderConfirmation.getShippingAddrerss();
				sassert()
						.assertTrue(
								shippinhAddressDetails.toLowerCase()
										.contains(addressDetails.get(CheckOut.shippingAddress.keys.adddressLine).trim()
												.toLowerCase()),
								"Error in shipping address from order confirmation page.<br> the actual address: "
										+ shippinhAddressDetails + "<br>and expected: "
										+ addressDetails.get(CheckOut.shippingAddress.keys.adddressLine));

				orderId = CheckOut.orderConfirmation.getOrderId();
				orderTotal = CheckOut.orderConfirmation.getOrderTotal();
				String orderSubtotalconfirmationPage = CheckOut.orderConfirmation.getItemsSubTotal();

				sassert().assertTrue(orderSubtotal.contains(orderSubtotalconfirmationPage),
						"order items subtotal is not correct");

				Testlogs.get().debug(MessageFormat.format(LoggingMsg.CHECKOUT_RESULT, Pemail, orderId, orderSubtotal));

				sassert().assertAll();
				Common.testPass();
			} // if not external
		} catch (Throwable t) {
			setTestCaseDescription(getTestCaseDescription());
			Testlogs.get().debug(MessageFormat.format(LoggingMsg.DEBUGGING_TEXT, t.getMessage()));
			t.printStackTrace();
			String temp = getTestCaseReportName();
			Common.testFail(t, temp);
			ReportUtil.takeScreenShot(getDriver(), testDataSheet + "_" + caseId);
			Assert.assertTrue(false, t.getMessage());
		} // catch
	}// test
}// class
