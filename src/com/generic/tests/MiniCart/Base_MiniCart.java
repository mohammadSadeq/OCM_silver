package com.generic.tests.MiniCart;

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
import com.generic.page.MiniCart;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.dataProviderUtils;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class Base_MiniCart extends SelTestCase {

	private static LinkedHashMap<String, Object> addresses = null;
	private static LinkedHashMap<String, Object> invintory = null;
	private static LinkedHashMap<String, Object> paymentCards = null;
	private static LinkedHashMap<String, Object> users = null;

	// user types
	public static final String guestUser = "guest";
	public static final String freshUser = "fresh";
	public static final String loggedInUser = "loggedin";
	public static final String loggedDuringChcOt = "logging During Checkout";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.MiniCartSheet;

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
	LinkedHashMap<String, String> productDetails;

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger("checkout_setup"));
		testObject = test;
		addresses = Common.readAddresses();
		invintory = Common.readLocalInventory();
		paymentCards = Common.readPaymentcards();
		users = Common.readUsers();
	}

	@DataProvider(name = "miniCarts", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency maintenance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));

		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "miniCarts")
	public void miniCartRegression(String caseId, String runTest, String desc, String proprties, String products,
			String email, String itemSubtotal) throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("miniCart_" + getBrowserName()));
		setTestCaseReportName("miniCart Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.CHECKOUTDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- "), email,
				itemSubtotal));
		try {
			if (!getBrowserName().contains("mobile")) {

				String CaseMail = "";
				LinkedHashMap<String, Object> userdetails = null; 
				if (!email.equals(""))
				{
					userdetails = (LinkedHashMap<String, Object>) users.get(email);
					CaseMail = (String) userdetails.get(Registration.keys.email);
					Testlogs.get().debug("Mail will be used is: " + CaseMail);
				}
				
				if (proprties.contains("Loggedin"))
				{
					for (String product : products.split("\n"))
						prepareCartLoggedInUser(userdetails, product);
				}
				else
					for (String product : products.split("\n"))
						prepareCartNotLoggedInUser(product);
				
				//goto home
				getDriver().get(PagesURLs.getHomePage());				
				
				//click on minicart
				MiniCart.clickMiniCartButton();
				
				//wait 3 sec
				Thread.sleep(3000);
				
				//get information
				String MiniCartInformation  = MiniCart.getCartInformation();
				

				// check name
				if (proprties.toLowerCase().contains("Verify name items"))
					sassert().assertTrue(MiniCartInformation.contains(productDetails.get(PDP.keys.title)),
							"Product name is not in mini cart info: " + MiniCartInformation);

				//check image
				if (proprties.toLowerCase().contains("Verify ItemImage"))
					sassert().assertTrue(MiniCart.checkMinicartImage(), "item image is not displayed in mini cart");
				
				//check checkout button
				if (proprties.toLowerCase().contains("Verify checkoutButton"))
					sassert().assertTrue(MiniCart.checkCheckoutButton(), "checkout is not displayed in mini cart");
				
				
				sassert().assertAll();
			}
			else
			{
				logs.debug("<font size=\"3\" color=\"red\">MiniRegression is not Valid on Mobile</font>");
			}

			Common.testPass();
		} catch (Throwable t) {
			setTestCaseDescription(getTestCaseDescription());
			Testlogs.get().debug(MessageFormat.format(LoggingMsg.DEBUGGING_TEXT, t.getMessage()));
			t.printStackTrace();
			String temp = getTestCaseReportName();
			Common.testFail(t, temp);
			ReportUtil.takeScreenShot(getDriver());
			Assert.assertTrue(false, t.getMessage());
		} // catch
	}// test
	
	@SuppressWarnings("unchecked")
	public void prepareCartNotLoggedInUser(String product) throws Exception {
		logs.debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
		productDetails = (LinkedHashMap<String, String>) invintory.get(product);
		PDP.addProductsToCart(productDetails);
	}

	public void prepareCartLoggedInUser(LinkedHashMap<String, Object> userdetails, String product) throws Exception {
		logs.debug(MessageFormat.format(LoggingMsg.SEL_TEXT, userdetails));
		logs.debug((String) userdetails.get(Registration.keys.email));
		logs.debug((String) userdetails.get(Registration.keys.password));
		SignIn.logIn((String) userdetails.get(Registration.keys.email),
				(String) userdetails.get(Registration.keys.password));

		prepareCartNotLoggedInUser(product);

	}
	
}// class
