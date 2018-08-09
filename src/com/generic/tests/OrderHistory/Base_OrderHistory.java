package com.generic.tests.OrderHistory;

import java.text.MessageFormat;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.LinkedHashMap;

import com.generic.page.CheckOut;
import com.generic.page.OrderHistory;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.tests.checkout.Base_checkout;
import com.generic.util.dataProviderUtils;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class Base_OrderHistory extends SelTestCase {

	// user types
	public static final String guestUser = "guest";
	public static final String freshUser = "fresh";
	public static final String loggedInUser = "Loggedin";
	public static final String loggedDuringChcOt = "logging During Checkout";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.OrderHistory;

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
	public void OrderHistoryBaseTest(String caseId, String runTest, String desc, String proprties, String products,
			String shippingMethod, String payment, String shippingAddress, String billingAddress, String coupon,
			String email) throws Exception {
		try {
			
			//Important to add this for logging/reporting 
			Testlogs.set(new SASLogger("OrderHistory_"+getBrowserName()));
			setTestCaseReportName("OrderHistory Case");
			logCaseDetailds(MessageFormat.format(LoggingMsg.CHECKOUTDESC, testDataSheet + "." + caseId,
					this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- "), payment, shippingMethod));
			
			LinkedHashMap<String, String> addressDetails = (LinkedHashMap<String, String>) addresses.get(shippingAddress);
			
			Base_checkout checkoutTest = new Base_checkout();
			checkoutTest.initialSetUp(testObject);
			checkoutTest.external = true; //change this value will not pass through logging
			checkoutTest.checkOutBaseTest(caseId, runTest, desc, proprties, products, shippingMethod, payment, shippingAddress, billingAddress, coupon, email);
			
			String orderId = CheckOut.orderConfirmation.getOrderId();

			String OCbillingAddress = CheckOut.orderConfirmation.getBillingAddrerss();

			String OCOrderItemTotal = CheckOut.orderConfirmation.getItemsSubTotal();
			String OCOrderTotal = CheckOut.orderConfirmation.getOrderTotal();

			logs.debug(orderId + OCbillingAddress + OCOrderItemTotal + OCOrderTotal);
			
			OrderHistory.goToOrderHistoryPage(orderId);
			
			String OHbillingAddress = OrderHistory.getBillingAddress();
			String OHPayrment = OrderHistory.getPayment();
			String OHOrderItemTotal = OrderHistory.getOrderItemTotal();
			String OHOrderTotal = OrderHistory.getOrderTotal();
			
			//getDriver().get("file:///C:/Users/Ibatta/Downloads/Order%20Details%20_%20Our%20Campus%20Market.html");
			
			// verifying billing information
			sassert().assertTrue(
					OHbillingAddress.contains(addressDetails.get(CheckOut.shippingAddress.keys.adddressLine)),
					"<font color=#f442cb>Billing addresses is not identical <br> Order confirmtion:"
							+ OCbillingAddress.replace("\n", "<br>") + "<br> order History:"
							+ OHbillingAddress.replace("\n", "<br>") + "</font>");

			// verifying payment
			sassert().assertTrue(OHPayrment.toLowerCase().contains(payment),
					"<font color=#f442cb>Payment is not exist in billing information, <br> order history Billing information:<br> "
							+ OHbillingAddress + "<br> payemnt is: " + payment + "</font>");

			// verifying order item total
			sassert().assertTrue(OHOrderItemTotal.contains(OCOrderItemTotal),
					"<font color=#f442cb>order total is not identical <br>Order history item total: " + OHOrderItemTotal
							+ "<br>order confirmation total:<br> " + OCOrderItemTotal + "</font>");

			// verifying order total
			sassert().assertTrue(OHOrderTotal.contains(OCOrderTotal),
					"<font color=#f442cb>order total is not identical <br>Order history item total:<br> " + OHOrderTotal
							+ "<br>order confirmation total:" + OCOrderTotal + "</font>");

			sassert().assertAll();
			Common.testPass();
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
