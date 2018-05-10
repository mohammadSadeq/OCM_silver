/**
 * this generic test for PDP regression that will pull tests from PDPRegression tab from
 * datasheet.xlsx. 
*/
package com.generic.tests.PDP;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

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

public class Base_PDP extends SelTestCase {

	private static LinkedHashMap<String, Object> invintory;
	private static LinkedHashMap<String, Object> users;

	// user types
	public static final String guestUser = "guest";
	public static final String loggedInUser = "loggedin";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.PDPSheet;

	private static XmlTest testObject;
	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	LinkedHashMap<String, String> productDetails;

	@BeforeClass
	public static void initialSetUp(XmlTest test) throws Exception {
		testCaseRepotId = SheetVariables.PDPCaseId;
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
		invintory = Common.readLocalInventory();
		users = Common.readUsers();
	}

	@DataProvider(name = "PDPs", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency maintenance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@Test(dataProvider = "PDPs")
	public void PDPBaseTest(String caseId, String runTest, String desc, String proprties, String product, String email,
			String ValidationMsg) throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("PDP_" + getBrowserName()));
		setTestCaseReportName("PDP Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.PDPDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- ")));

		String UsedEmail = "";
		LinkedHashMap<String, Object> userdetails = null;
		String socialButtons = "blogger delicious digg email facebook flipboard googleplus linkedin livejournal mailru meneame messenger odnoklassniki pinterest print reddit stumbleupon tumblr twitter vk wechat weibo whatsapp xing";
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
			LinkedHashMap<String, String> productDetails = (LinkedHashMap<String, String>) invintory.get(product);
			String PDPURL = PDP.getPDPUrl(productDetails.get(PDP.keys.url));
			Testlogs.get().debug("productDetails to be visted: " + Arrays.asList(productDetails));
			Testlogs.get().debug("url key " + PDP.keys.url);
			Testlogs.get().debug("url key value " + PDPURL);
			getDriver().get(PDPURL);

			// Apply color and check of the results reflected to PDP
			if (!((String) productDetails.get(PDP.keys.color)).equals("") && proprties.contains("color")) {
				logs.debug("selecting color: " + (String) productDetails.get(PDP.keys.color));
				PDP.selectColor((String) productDetails.get(PDP.keys.color));
				logs.debug("checking PDP selected color");
				ReportUtil.takeScreenShot(getDriver());
			} // variant color

			// Apply size
			if (proprties.contains("size") && !((String) productDetails.get(PDP.keys.size)).equals("")) {

				logs.debug("selecting size: " + (String) productDetails.get(PDP.keys.size));
				PDP.selectSize((String) productDetails.get(PDP.keys.size));
				ReportUtil.takeScreenShot(getDriver());
			} // size check

			// Apply fleece
			if (!((String) productDetails.get(PDP.keys.fleece)).equals("") && proprties.contains("fleece")) {
				logs.debug("selecting fleece: " + (String) productDetails.get(PDP.keys.fleece));
				PDP.selectFleece((String) productDetails.get(PDP.keys.fleece));

				ReportUtil.takeScreenShot(getDriver());
			} // fleece check

			// Apply memory
			if (!((String) productDetails.get(PDP.keys.memory)).equals("") && proprties.contains("memory")) {
				logs.debug("selecting memory: " + (String) productDetails.get(PDP.keys.memory));
				PDP.selectMemory((String) productDetails.get(PDP.keys.memory));

				ReportUtil.takeScreenShot(getDriver());
			} // fleece check

			if (proprties.contains("price")) {
				logs.debug("checking PDP price");
				String price = PDP.getPrice();
				logs.debug(price.contains(productDetails.get(PDP.keys.price).trim()) + "");
				sassert().assertTrue(price.contains(productDetails.get(PDP.keys.price).trim()),
						"<font color=#f442cb>product price is not expected</font>"
								+ productDetails.get(PDP.keys.price).trim() + " not" + price);
				ReportUtil.takeScreenShot(getDriver());
			} // price check

			if (proprties.contains("title")) {
				logs.debug("checking PDP title");
				String title = PDP.getTitle();
				sassert().assertTrue(title.contains(productDetails.get(PDP.keys.title)),
						"<font color=#f442cb>product title is not expected</font>");
				ReportUtil.takeScreenShot(getDriver());
			} // title check

			if (proprties.contains("add to cart button")) {
				logs.debug("checking PDP add to cart button");
				sassert().assertTrue(PDP.checkAddToCartButton(),
						"<font color=#f442cb>product add to button is not expected</font>");
				ReportUtil.takeScreenShot(getDriver());
			} // add to cart button check

			if (proprties.contains("id")) {
				logs.debug("checking PDP ID");
				String Id = PDP.getId();
				sassert().assertTrue(Id.toLowerCase().contains(productDetails.get(PDP.keys.id).toLowerCase()),
						"<font color=#f442cb>product id is not expected site " + Id + " and sheet "
								+ productDetails.get(PDP.keys.id) + "</font>");
				ReportUtil.takeScreenShot(getDriver());
			} // id check

			if (proprties.contains("desc")) {
				logs.debug("checking PDP desc");
				PDP.clickOnDesc();
				String description = PDP.getProductDesc();
				sassert().assertTrue(description.trim().contains(((String) productDetails.get(PDP.keys.desc)).trim()),
						"<font color=#f442cb>product desc is not expected found: " + description + " <br>expected: "
								+ (String) productDetails.get(PDP.keys.desc) + "</font>");
				ReportUtil.takeScreenShot(getDriver());
			} // info check

			if (proprties.contains("social")) {
				logs.debug("checking PDP social");
				Thread.sleep(500);
				PDP.clickShareBtn();
				String social = PDP.getAllsocialMediabuttons();
				if (getBrowserName().contains("mobile")) {
					logs.debug("Detecting mobile site");
					sassert().assertTrue(social.contains("sms"), "Mobile has no sms share button");
					social = social.replace("sms ", "");
				}

				sassert().assertTrue(social.trim().contains((socialButtons).trim()),
						"<font color=#f442cb>social buttons is not expected found: " + social + " <br>expected: "
								+ socialButtons + "</font>");
				ReportUtil.takeScreenShot(getDriver());
				Thread.sleep(500);
				PDP.closeSocialShare();
				Thread.sleep(1000);
				ReportUtil.takeScreenShot(getDriver());
			} // info social share

			if (proprties.contains("bundle")) {
				logs.debug("checking PDP bundle");
				String numberOfProductsInbundleFromSite = PDP.getNumberOfProductsInBundle();
				String countOfProductsInBundleFromSite = PDP.countProductsInBundle();
				String allProductsTitles = PDP.getAllProductsBundle();

				String numberOfProductsSheet = productDetails.get(PDP.keys.bundleProducts).split("\n").length + "";

				sassert().assertTrue(
						numberOfProductsInbundleFromSite.contains(numberOfProductsSheet)
								&& numberOfProductsInbundleFromSite.contains(countOfProductsInBundleFromSite),
						"<font color=#f442cb>bundle products count is not idetical<br> productfrom site: "
								+ numberOfProductsInbundleFromSite + "<br> product count from site: "
								+ countOfProductsInBundleFromSite + "<br> prdcut count from sheet: "
								+ numberOfProductsSheet + "</font>");

				sassert().assertTrue(allProductsTitles.contains(productDetails.get(PDP.keys.bundleProducts)),
						"<font color=#f442cb>products bundle is not as expected: " + "<br>sheet products: "
								+ productDetails.get(PDP.keys.bundleProducts) + "<br>site products: "
								+ allProductsTitles + "</font>");

				ReportUtil.takeScreenShot(getDriver());
			} // bundle check

			if (proprties.contains("reviews")) {
				logs.debug("checking PDP reviews");
				String reviews = PDP.getNumberofReviews();
				String reviewscount = PDP.countReviews();

				sassert().assertTrue(reviews.trim().contains((reviewscount).trim()),
						"<font color=#f442cb>product reviews is not expected found: " + reviews + " <br>counted: "
								+ reviewscount + "</font>");
				ReportUtil.takeScreenShot(getDriver());
			} // reviews

			if (proprties.contains("ratings")) {
				logs.debug("checking PDP rating");
				String starsRating = PDP.getRatingFromStars();
				String secondaryRating = PDP.getSecondaryRating();

				sassert().assertTrue(starsRating.trim().contains((secondaryRating).trim()),
						"<font color=#f442cb>product rating is not expected <br> stars: " + starsRating
								+ " <br>secondary: " + secondaryRating + "</font>");
				ReportUtil.takeScreenShot(getDriver());
			} // info social share

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
