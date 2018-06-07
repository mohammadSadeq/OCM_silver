package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.generic.selector.OrderHistorySelector;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.util.SelectorUtil;

public class OrderHistory extends SelTestCase {
    private static int numberOfOrdersShownInHeader;
    
    public static class keys {
		public static final String caseId = "caseId";
	}
    
    public static void selectSortOptions1ByValue(String sortByTxt) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.SELECTING_ELEMENT_VALUE,OrderHistorySelector.sortOptions1, sortByTxt));
		subStrArr.add(OrderHistorySelector.sortOptions1);
		valuesArr.add(sortByTxt);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String SelectedOptions2Val = getSortOptions2SelectedValue();
		logs.debug(MessageFormat.format(LoggingMsg.PLP_SELECTED_SORT_VALUES,"sortOptions1", sortByTxt, "sortOptions2", SelectedOptions2Val.trim()));
		getCurrentFunctionName(false);
	}
    
    public static String getSortOptions2SelectedValue() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,OrderHistorySelector.sortOptions2));
		subStrArr.add(OrderHistorySelector.sortOptions2);
		valuesArr.add("");
		String sortOptions2SelectedValue = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		sortOptions2SelectedValue = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
    	return sortOptions2SelectedValue;
    }
    
    public static void selectSortOptions2ByValue(String sortByTxt) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.SELECTING_ELEMENT_VALUE,OrderHistorySelector.sortOptions2, sortByTxt));
		subStrArr.add(OrderHistorySelector.sortOptions2);
		valuesArr.add(sortByTxt);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String SelectedOptions1Val = getSortOptions2SelectedValue();
		logs.debug(MessageFormat.format(LoggingMsg.PLP_SELECTED_SORT_VALUES,"sortOptions2", sortByTxt, "sortOptions1", SelectedOptions1Val.trim()));
		getCurrentFunctionName(false);
	}
    
    public static String getSortOptions1SelectedValue() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,OrderHistorySelector.sortOptions1));
		subStrArr.add(OrderHistorySelector.sortOptions1);
		valuesArr.add("");
		String sortOptions2SelectedValue = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		sortOptions2SelectedValue = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
    	return sortOptions2SelectedValue;
    }
    
    public static String getNumberOfOrders() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(OrderHistorySelector.ordersNumberLabel);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_ORDERS, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		String ordersNum = SelectorUtil.textValue.get().split(" ")[0];
		return ordersNum;
	}
    
    public static boolean doesDisplayedOrdersNumTextMatchesOrdersDisplayed() throws Exception {
    	getCurrentFunctionName(true);
    	List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
    	getNumberOfOrders();
		subStrArr.add(OrderHistorySelector.responsiveTableItem);
		valuesArr.add("noClick");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		int numberOfOrdersShownInHeader = Integer.parseInt(getNumberOfOrders());
		logs.debug(MessageFormat.format(LoggingMsg.EXPECTED_TEXT, numberOfOrdersShownInHeader));
		logs.debug(MessageFormat.format(LoggingMsg.ACTUAL_TEXT, SelectorUtil.numberOfFoundElements.get()));
		getCurrentFunctionName(false);
		if (numberOfOrdersShownInHeader == Integer.parseInt(SelectorUtil.numberOfFoundElements.get())) {
    		return true;
    	} else {
    		logs.debug(MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, SelectorUtil.numberOfFoundElements, numberOfOrdersShownInHeader));
    		return false;
    	}
    }
    
    public static String clickNthResponsiveTableItemTableCellAnchor(String nthChild, int index) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		String selText = MessageFormat.format(OrderHistorySelector.nthResponsiveTableItemTableCellAnchor, nthChild);
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, selText));
		subStrArr.add(selText);
		valuesArr.add("index," + index);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}
    // nthChild is the index of all elements (here all td at the same index in all tr in the table)
    // index is the index of the exact element extracted from all elements returned
    public static String getNthResponsiveTableItemColumn(String nthChild, int index) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		String selText = MessageFormat.format(OrderHistorySelector.nthResponsiveTableItemColumn, nthChild);
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, selText));
		subStrArr.add(selText);
		valuesArr.add("index," + index);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

    public static String getNoOrdersMessage() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,OrderHistorySelector.orderHistoryNoOrders));
		subStrArr.add(OrderHistorySelector.orderHistoryNoOrders);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
    	return SelectorUtil.textValue.get();
    }

	//done-ocm
    public static void goToOrderHistoryPage(String OrderId) throws Exception {
		getCurrentFunctionName(true);
		String url = PagesURLs.getHomePage()+PagesURLs.getOrderHistoryPage()+OrderId ; 
		logs.debug("getting order history page: " +url );
		getDriver().get(url);
		getCurrentFunctionName(false);
		
	}

	// done-ocm
	public static String getBillingAddress() throws Exception {
		try {
			getCurrentFunctionName(true);
			String BA =  CheckOut.orderConfirmation.getBillingAddrerss();
			getCurrentFunctionName(false);
			return BA;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-ocm
	public static String getPayment() throws Exception {
		try {
			getCurrentFunctionName(true);
			String BA =  CheckOut.orderConfirmation.getBillingAddrerss();
			getCurrentFunctionName(false);
			return BA;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-ocm
	public static String getOrderItemTotal() throws Exception {
		try {
			getCurrentFunctionName(true);
			String BA =  CheckOut.orderConfirmation.getItemsSubTotal();
			getCurrentFunctionName(false);
			return BA;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-ocm
	public static String getOrderTotal() throws Exception {
		try {
			getCurrentFunctionName(true);
			String BA =  CheckOut.orderConfirmation.getOrderTotal();
			getCurrentFunctionName(false);
			return BA;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
}
