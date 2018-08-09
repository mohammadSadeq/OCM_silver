package com.generic.tests.Search_PLP;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.generic.page.PLP;
import com.generic.page.Registration;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.util.dataProviderUtils;

public class PLP_Base extends SelTestCase {

	// used sheet in test
	public static final String testDataSheet = SheetVariables.plpSheet;

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
	private static LinkedHashMap<String, Object> users;

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
		users = Common.readUsers();
	}
	
	@DataProvider(name = "PLP", parallel = true)
	// concurrency maintenance on sheet reading
	public static Object[][] loadTestData() throws Exception {
		getBrowserWait(testObject.getParameter("browserName"));

		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "PLP")
	public void verifyPLP(String caseId, String runTest, String desc, String Proprties,	String numberOfProducts,String email) throws Exception {
		
		Testlogs.set(new SASLogger("PLP " + getBrowserName()));
		// Important to add this for logging/reporting
		setTestCaseReportName("PLP Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.TEST_CASE_DESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));
		LinkedHashMap<String, Object> userDetails = (LinkedHashMap<String, Object>) users.get(email);

		String emailSubmail = getSubMailAccount((String) userDetails.get(Registration.keys.email));

		try {
			
			if (Proprties.contains("Loggedin"))
			{
				SignIn.logIn(emailSubmail, (String) userDetails.get(Registration.keys.password));

			}
			String url = PagesURLs.getHomePage()+ PagesURLs.getPLP();
			getDriver().get(url);
			Thread.sleep(1000);

			
			
			if (Proprties.contains("sort") && !getBrowserName().contains("mobile"))
				sassert().assertTrue(PLP.sortAndValidate(Proprties.split("sort")[1].split("\n")[0]),"The sorting is not OK");
			
			if (Proprties.contains("Pagination"))
			{
				String countOfProductsInPLP = String.valueOf(PLP.countProductsInPage());
				sassert().assertEquals( numberOfProducts, countOfProductsInPLP,"The pagination is not OK");
			}			
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
		}

	}

}
