/**
 * this generic test for PDP regression that will pull tests from PDPRegression tab from
 * datasheet.xlsx. 
*/
package com.generic.tests.Favorite;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.generic.page.Favorite;
import com.generic.page.PDP;
import com.generic.page.Registration;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.util.dataProviderUtils;

public class Base_Favorite extends SelTestCase {

	private static LinkedHashMap<String, Object> invintory;
	private static LinkedHashMap<String, Object> users;

	// user types
	public static final String guestUser = "guest";
	public static final String loggedInUser = "loggedin";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.FavoriteSheet;

	private static XmlTest testObject;
	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	LinkedHashMap<String, String> productDetails;

	@BeforeClass
	public static void initialSetUp(XmlTest test) throws Exception {
		testCaseRepotId = SheetVariables.FavoriteSheetTestCaseId;
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
		invintory = Common.readLocalInventory();
		users = Common.readUsers();
	}

	@DataProvider(name = "FAVs", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency maintenance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked")
	@Test(dataProvider = "FAVs")
	public void FavoriteBaseTest(String caseId, String runTest, String desc, String proprties, String email,
			String product) throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("Favorite_" + getBrowserName()));
		setTestCaseReportName("Favorite Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.FAVORITE, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- ")));

		String UsedEmail = "";
		LinkedHashMap<String, Object> userdetails = null;
		if (!email.equals("")) {
			userdetails = (LinkedHashMap<String, Object>) users.get(email);
			UsedEmail = (String) userdetails.get(Registration.keys.email);
			Testlogs.get().debug("Mail will be used is: " + UsedEmail);
		}

		try {

			if (proprties.contains("Loggedin")) {
				Testlogs.get().debug("Used mail to login: " + UsedEmail);
				Testlogs.get().debug("Used Password to login: " + (String) userdetails.get(Registration.keys.password));
				SignIn.logIn(UsedEmail, (String) userdetails.get(Registration.keys.password));
			}
			
			String ProductTitle = "";
			if (!product.equals(""))
			{
				LinkedHashMap<String, String> productDetails = (LinkedHashMap<String, String>) invintory.get(product);
				String PDPURL = PDP.getPDPUrl(productDetails.get(PDP.keys.url));
				Testlogs.get().debug("productDetails to be visted: " + Arrays.asList(productDetails));
				Testlogs.get().debug("url key " + PDP.keys.url);
				Testlogs.get().debug("url key value " + PDPURL);
				getDriver().get(PDPURL);
				ProductTitle =PDP.getTitle();
			}
			else {
				ProductTitle = PDP.getRandomProduct("dryer");
			}

			if (proprties.contains("PDP") || !proprties.contains("PLP"))
				PDP.addToFavorite();
			if (proprties.contains("PLP"))
				PDP.addToFavorite();
				//TODO: add function to handle 
			
			getDriver().get(Favorite.getFavorriteUrl());
			String AllProducts = Favorite.getAllProducts();
			//sassert().assertTrue(AllProducts.contains(ProductTitle), "Product was not added successfully FAV ");
			
			int NumberOfProducts = Favorite.getNumberOfProducts();
			
			for (int index = 0; index <NumberOfProducts; index++) {
				Favorite.removeProduct();
			}
			
			sassert().assertAll();
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

}
