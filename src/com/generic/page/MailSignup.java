package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import com.generic.selector.mailSignupSelectors;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.util.SelectorUtil;

public class MailSignup extends SelTestCase {
	public static class keys {
		public static final String caseId = "caseId";
		public static final String errorMsg = "errorMsg";
		public static final String mail = "mail";

	}
	public static void emailSignup(String mail) throws Exception {
		writeEmail(mail);
		clickSignUp();
	}

	private static void clickSignUp() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(mailSignupSelectors.signUpBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		
	}

	public static void writeEmail(String mail) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, mail, "mail "));
		subStrArr.add(mailSignupSelectors.mailField);
		valuesArr.add(mail);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static String getResultMsg() {
		// TODO Auto-generated method stub
		return "";
	}
}
