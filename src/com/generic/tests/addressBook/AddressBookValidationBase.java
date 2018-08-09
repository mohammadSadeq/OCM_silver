package com.generic.tests.addressBook;

import java.text.MessageFormat;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;
import java.util.LinkedHashMap;

import com.generic.page.Registration;
import com.generic.page.AddressBook;
import com.generic.page.CheckOut;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.dataProviderUtils;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class AddressBookValidationBase extends SelTestCase {
	// used sheet in test
	public static final String testDataSheet = SheetVariables.AddressBookSheet;

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
	}

	@DataProvider(name = "AddressBook", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency maintenance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		return data;

	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "AddressBook")
	public void BookAddressTest(String caseId, String runTest, String desc, String prop, String email,
			String newaddress, String globalAlerts) throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("Address_Book " + getBrowserName()));
		setTestCaseReportName("Address Book Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.ADDRESSPOOKDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));

		String caseMail = "";
		LinkedHashMap<String, Object> userdetails = null;

		try {

			if (!email.equals("")) {
				userdetails = (LinkedHashMap<String, Object>) users.get(email);
				caseMail = (String) userdetails.get(Registration.keys.email);
				caseMail = getSubMailAccount(caseMail);
			} else {
				throw new Exception("Email is missing");
			}
			Testlogs.get().debug("Mail will be used is: " + caseMail);
			Testlogs.get().debug("Password will be used is: " + (String) userdetails.get(Registration.keys.password));
			SignIn.logIn(caseMail, (String) userdetails.get(Registration.keys.password));
			String url = PagesURLs.getHomePage()+PagesURLs.getAddressBookPage();
			getDriver().get(url);
			AddressBook.clickAddNewAddress();
			LinkedHashMap<String, String> addressDetails = (LinkedHashMap<String, String>) addresses.get(newaddress);
			AddressBook.fillAndClickSave(addressDetails.get(CheckOut.shippingAddress.keys.firstName),
					addressDetails.get(CheckOut.shippingAddress.keys.lastName),
					addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
					addressDetails.get(CheckOut.shippingAddress.keys.city),
					addressDetails.get(CheckOut.shippingAddress.keys.city),
					addressDetails.get(CheckOut.shippingAddress.keys.zipcode),
					addressDetails.get(CheckOut.shippingAddress.keys.phone));
			if (prop.contains("new")) {
				String siteAddress = AddressBook.getAddressDetails(1);
				Testlogs.get().debug(siteAddress);
				sassert().assertTrue(siteAddress.toLowerCase().contains((String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine).toLowerCase()),
								"Address " + siteAddress + " doesnt contain line1: " + (String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine));
			}

			if (prop.contains("edit")) {
				String addressbook = AddressBook.getAddressDetails(0);
				AddressBook.clickEditAddress();
				AddressBook.fillAndClickUpdate(RandomStringUtils.randomAlphabetic(5), "", "", "", "", "", "");
				String newAddress = AddressBook.getAddressDetails(0);
				sassert().assertNotEquals(addressbook, newAddress, "Address is not updated correctely : new " + newAddress + " <br> old one: " + addressbook);
			}
			if (prop.contains("default")) {
				String oldDefaultAddress = null;
				String expectedNewDefaultAddress = null;
				if (AddressBook.getNumberOfAddresses() > AddressBook.getNumberOfNonDefaultAddresses()) {
					oldDefaultAddress = AddressBook.getAddressDetails(0);
					expectedNewDefaultAddress = AddressBook.getAddressDetails(1);
				} else {
					expectedNewDefaultAddress = AddressBook.getAddressDetails(0);
				}
				AddressBook.checkSetAsDefaultAddress(); //
				String newDefaultAddress = AddressBook.getAddressDetails(0);
				String alertInfo = AddressBook.getAlertInfo();
				String expectedAlertInfo = globalAlerts.split("UpdateAddressMsg:")[1].split("\n")[0];
				sassert().assertEquals(expectedNewDefaultAddress, newDefaultAddress, "Default address is not updated correctely : new " + newDefaultAddress + " <br> old one: "
								+ oldDefaultAddress);
				sassert().assertEquals(alertInfo, expectedAlertInfo, "Default address is not updated correctely : Expected message: " + expectedAlertInfo
								+ " <br> Actual message: " + alertInfo);
			}

			// Remove the created address.
			if (prop.contains("delete")) {
				AddressBook.removeNonDefaultAddress(0);
	        	String alertInfo = AddressBook.getAlertInfo();
		    	String expectedAlertInfo = globalAlerts.split("DeleteAddressMsg:")[1].split("\n")[0];
		    	sassert().assertEquals(alertInfo, expectedAlertInfo, "Default address is not removed correctely : Expected message: " + expectedAlertInfo
							+ " <br> Actual message: " + alertInfo);	
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
		} // catch
	}// test
}// class
