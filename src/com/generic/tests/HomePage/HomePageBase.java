package com.generic.tests.HomePage;

import java.text.MessageFormat;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.generic.page.HomePage;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.util.dataProviderUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import javax.imageio.ImageIO;
import java.io.File;

public class HomePageBase extends SelTestCase {

	// possible scenarios
	public static final String update = "update";
	public static final String verify = "verify";
	public static final String header = "header";
	public static final String footer = "footer";
	public static final String body = "body";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.VisualTestingHPRegressionsheet;

	private static XmlTest testObject;
	
	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>() ; 
	
	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
	}

	@DataProvider(name = "HP_SC", parallel = true)
	//concurrency maintenance on sheet reading 
	public static Object[][] loadTestData() throws Exception {
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}


	@Test(dataProvider = "HP_SC")
	public void HomePageRegressionTest(String caseId, String runTest, String desc, String proprties, String baseline) throws Exception {
		Testlogs.set(new SASLogger("HP_SC "+getBrowserName()));
		//Important to add this for logging/reporting 
		setTestCaseReportName(SheetVariables.VisualTestingHPTestCaseId);
		//Testlogs.get().debug("Case Browser: "  + testObject.getParameter("browserName") );
		logCaseDetailds(MessageFormat.format(LoggingMsg.TEST_CASE_DESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));
		
//		String baseline_browser = baseline+"_"+getBrowserName().replace(" ", "_");
		
		try {
			String url = PagesURLs.getHomePage();
			getDriver().get(url);
			Thread.sleep(1500);
			
			if (proprties.contains(this.update))
			{
				if (proprties.contains(this.header))
					HomePage.updateHeaderBaseline(baseline);
				if (proprties.contains(this.footer))
					HomePage.updateFooterBaseline(baseline);
				if (proprties.contains(this.body))
					HomePage.updateBodyBaseline(baseline);
				
				HomePage.prepareBaselineforLogs(baseline);
			}
			else if (proprties.contains(this.verify))
			{
				if (proprties.contains(this.header))
					sassert().assertTrue(HomePage.verifyHeader(baseline),"headerbase line is not same site header");
				if (proprties.contains(this.footer))
					sassert().assertTrue(HomePage.verifyFooter(baseline),"headerbase line is not same site footer");
				if (proprties.contains(this.body))
					sassert().assertTrue(HomePage.verifyBody(baseline),"headerbase line is not same site body");
				
				HomePage.prepareBaselineforLogs(baseline);
			}
			else {
				Testlogs.get().debug("please check proprties provided in excel sheet");
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
