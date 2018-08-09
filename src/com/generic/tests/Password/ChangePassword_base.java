package com.generic.tests.Password;

import java.text.MessageFormat;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.generic.page.Registration;
import com.generic.page.MyAccount_Password;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.dataProviderUtils;
import com.google.common.base.Splitter;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class ChangePassword_base extends SelTestCase {
	// used sheet in test
	public static final String testDataSheet = SheetVariables.PasswordRegressionSheet;
	private static XmlTest testObject;
	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
	private String CurrentPageTitle;

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
	}

	@DataProvider(name = "Password", parallel = true)
	// concurrency maintenance on sheet reading
	public static Object[][] loadTestData() throws Exception {
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "Password")
	public void changePasswordRegressionTest(String caseId, String runTest, String desc, String proprties, String email,
			String newPassword, String confirmNewPassword, String globalAlerts) throws Exception {

		boolean doClickCancelBtn = proprties.contains("click cancel");
		boolean doClickUpdateBtn = proprties.contains("click update");
		boolean revertChanges = proprties.contains("revert changes");

		LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>) users.get(email);

		Testlogs.set(new SASLogger("Password_" + getBrowserName()));
		// Important to add this for logging/reporting
		setTestCaseReportName("Change Password Case");

		logCaseDetailds(MessageFormat.format(LoggingMsg.TEST_CASE_DESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));

		String emailSubmail = getSubMailAccount(userDetails.get(Registration.keys.email));
		Testlogs.get().debug("Mail will be used is: " + emailSubmail);

		try {

			if (emailSubmail.isEmpty())
				throw new NoSuchFieldException("Email is not valid");

			SignIn.logIn(emailSubmail, userDetails.get(Registration.keys.password));

			String url = PagesURLs.getHomePage() + PagesURLs.getPasswordPage();
			getDriver().get(url);
			CurrentPageTitle = getDriver().getTitle();

			if (proprties.contains("without")) {
				MyAccount_Password.fillInNewValuesAndClickUpdateOrCancel("", newPassword, confirmNewPassword,
						doClickUpdateBtn, doClickCancelBtn);
			} else if (proprties.contains("incorrect")) {
				MyAccount_Password.fillInNewValuesAndClickUpdateOrCancel("wrong123456", newPassword, confirmNewPassword,
						doClickUpdateBtn, doClickCancelBtn);
			} else
				MyAccount_Password.fillInNewValuesAndClickUpdateOrCancel(
						(String) userDetails.get(Registration.keys.password), newPassword, confirmNewPassword,
						doClickUpdateBtn, doClickCancelBtn);
			Thread.sleep(3000);

			Map<String, String> Alerts = null;
			if (!globalAlerts.isEmpty())
				Alerts = Splitter.on("\n").withKeyValueSeparator(":").split(globalAlerts);

			if (doClickUpdateBtn) {
				if (globalAlerts.contains("globalAlerts")) {

					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
							MyAccount_Password.getGlobalAlertsMsg(), Alerts.get("globalAlerts"));
					sassert().assertTrue(globalAlertMsg.contains(Alerts.get("globalAlerts")), globalAlertMsg);
				}
				if (globalAlerts.contains("currentPasswordErrors")) {

					String currentMsg = MyAccount_Password.getCurrentPasswordErrorrMsg(proprties.contains("alert"));
					logs.debug("Alert Message/ current Password error message: " + currentMsg);
					String currentPasswordErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentMsg,
							Alerts.get("currentPasswordErrors"));
					sassert().assertTrue(currentMsg.contains(Alerts.get("currentPasswordErrors")),
							currentPasswordErrorMsg);
				}
				if (globalAlerts.contains("newPasswordEerrors")) {

					String currentMsg = MyAccount_Password.getNewPasswordErrorMsg(proprties.contains("alert"));
					logs.debug("Alert Message/ new Password error message: " + currentMsg);
					String newPasswordEerrorsMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentMsg,
							Alerts.get("newPasswordEerrors"));
					sassert().assertTrue(currentMsg.contains(Alerts.get("newPasswordEerrors")), newPasswordEerrorsMsg);
				}

				if (globalAlerts.contains("confirmNewPasswordErrors")) {

					String currentMsg = MyAccount_Password.getConfirmNewPasswordErrorMsg(proprties.contains("alert"));
					logs.debug("Alert Message/ confirm New Password error message: " + currentMsg);
					String confirmNewPasswordErrorsMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
							currentMsg, Alerts.get("confirmNewPasswordErrors"));
					sassert().assertTrue(currentMsg.contains(Alerts.get("confirmNewPasswordErrors")),
							confirmNewPasswordErrorsMsg);
				}
			}

			if (revertChanges && proprties.contains("update")) {
				if (CurrentPageTitle != getDriver().getTitle())
					getDriver().get(url);
				MyAccount_Password.fillInNewValuesAndClickUpdateOrCancel(newPassword,
						(String) userDetails.get(Registration.keys.password),
						(String) userDetails.get(Registration.keys.password), doClickUpdateBtn, doClickCancelBtn);
				if (globalAlerts.contains("globalAlerts")) {
					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
							MyAccount_Password.getGlobalAlertsMsg(), Alerts.get("globalAlerts"));
					sassert().assertTrue(globalAlertMsg.contains(Alerts.get("globalAlerts")), globalAlertMsg);
				}
				Thread.sleep(2000);
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
}// class