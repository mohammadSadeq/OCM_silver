package com.generic.page;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.generic.selector.SearchResultSelectors;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.util.SelectorUtil;

public class SearchResults extends SelTestCase {
    
    public static class keys {
		public static final String caseId = "caseId";
	}
    public static void typeSearchText(String searchValue) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,SearchResultSelectors.searchInput, searchValue));
		subStrArr.add(SearchResultSelectors.searchInput);
		valuesArr.add(searchValue);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}
    
    public static void typeSearchTextAndPressEnter(String searchValue) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,SearchResultSelectors.searchInput, searchValue));
		subStrArr.add(SearchResultSelectors.searchInput);
		valuesArr.add(searchValue + ",pressEnter");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}
    public static String getNumberOfMenuItems() throws Exception {
    	getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(SearchResultSelectors.searchMenuItems);
		valuesArr.add("noClick");
		try{
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_MENUE_ITEMS, SelectorUtil.numberOfFoundElements.get()));
		getCurrentFunctionName(false);
		return (SelectorUtil.numberOfFoundElements.get());
        } catch (Exception e) {
        	return "0";
		}
    }
    
    public static String getNthResponsiveListItemColumn(int nthChild, int index) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();		
		String selText = MessageFormat.format(SearchResultSelectors.nthResponsiveListItemColumn, nthChild);
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, selText));
		subStrArr.add(selText);
		valuesArr.add("index," + index);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		logs.debug(SelectorUtil.textValue.get());
		return SelectorUtil.textValue.get();
	}
    
    public static boolean checkNthResponsiveListItemImg(int index) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();		
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, SearchResultSelectors.nthResponsiveListItemListImg));
		subStrArr.add(SearchResultSelectors.nthResponsiveListItemListImg);
		boolean isDisplayed = SelectorUtil.isDisplayed(subStrArr, index);
		logs.debug("images check result is " + isDisplayed);
		getCurrentFunctionName(false);
		return isDisplayed;
		
	}
    
    public static String getNthResponsiveListItemImg(int index) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();		
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, SearchResultSelectors.nthResponsiveListItemListImg));
		subStrArr.add(SearchResultSelectors.nthResponsiveListItemListImg);
		String src = SelectorUtil.getAttr(subStrArr, "src", index);
		logs.debug("image src is: " + src);
		getCurrentFunctionName(false);
		return src;
		
	}
    
    
    public static void clickSearchBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, SearchResultSelectors.searchBtn));
		subStrArr.add(SearchResultSelectors.searchBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

    public static String getSearchResultsHeader() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,SearchResultSelectors.searchResultsHeader));
		subStrArr.add(SearchResultSelectors.searchResultsHeader);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
    	return SelectorUtil.textValue.get();
    }
    public static String getSearchResultsHeaderSearchEmpty() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,SearchResultSelectors.searchResultsHeaderSearchEmpty));
		subStrArr.add(SearchResultSelectors.searchResultsHeaderSearchEmpty);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
    	return SelectorUtil.textValue.get();
    }
    public static void clickAddToCart() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, SearchResultSelectors.addToCartForm));
		subStrArr.add(SearchResultSelectors.addToCartForm );
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}
    
    public static void clickProductPickupInStoreButton() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, SearchResultSelectors.productPickupInStoreButton));
		subStrArr.add(SearchResultSelectors.productPickupInStoreButton );
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}
    public static void clickPickUpInStoreAddToBagBtn() throws Exception {
  		getCurrentFunctionName(true);
  		List<String> subStrArr = new ArrayList<String>();
  		List<String> valuesArr = new ArrayList<String>();
  		subStrArr.add(SearchResultSelectors.pickupAddToBagBtn);
  		valuesArr.add("");
  		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
  		getCurrentFunctionName(false);
  	}
      
      public static void clickPickUpInStoreDecreaseQtyBtn() throws Exception {
  		getCurrentFunctionName(true);
  		List<String> subStrArr = new ArrayList<String>();
  		List<String> valuesArr = new ArrayList<String>();
  		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, SearchResultSelectors.pickupDecreaseQtyBtn));
  		subStrArr.add(SearchResultSelectors.pickupDecreaseQtyBtn);
  		valuesArr.add("");
  		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
  		getCurrentFunctionName(false);
  	}
      
      public static void clickPickUpInStoreIncreaseQtyBtn() throws Exception {
  		getCurrentFunctionName(true);
  		List<String> subStrArr = new ArrayList<String>();
  		List<String> valuesArr = new ArrayList<String>();
  		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL,SearchResultSelectors.pickupIncreaseQtyBtn));
  		subStrArr.add(SearchResultSelectors.pickupIncreaseQtyBtn);
  		valuesArr.add("");
  		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
  		getCurrentFunctionName(false);
  	}
      
      public static void typePickUpInStoreQty(String qty) throws Exception {
  		getCurrentFunctionName(true);
  		List<String> subStrArr = new ArrayList<String>();
  		List<String> valuesArr = new ArrayList<String>();
  		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, SearchResultSelectors.pickupQtyInput, qty));
  		subStrArr.add(SearchResultSelectors.pickupQtyInput);
  		valuesArr.add(qty);
  		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
  		getCurrentFunctionName(false);
  	}
      public static String getPLPProductPrice() throws Exception {
  		getCurrentFunctionName(true);
  		List<String> subStrArr = new ArrayList<String>();
  		List<String> valuesArr = new ArrayList<String>();
  		logs.debug(MessageFormat.format(LoggingMsg.GETTING_SEL, SearchResultSelectors.plpProductPriceLabel));
  		subStrArr.add(SearchResultSelectors.plpProductPriceLabel);
  		valuesArr.add("noClick");
  		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
  		getCurrentFunctionName(false);
  		return SelectorUtil.textValue.get();
  	}
      
      public static String getPLPProductPriceFromCartBag() throws Exception {
  		getCurrentFunctionName(true);
  		List<String> subStrArr = new ArrayList<String>();
  		List<String> valuesArr = new ArrayList<String>();
  		logs.debug(MessageFormat.format(LoggingMsg.GETTING_SEL, SearchResultSelectors.addToCartItemPrice));
  		subStrArr.add(SearchResultSelectors.addToCartItemPrice);
  		valuesArr.add("");
  		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
  		getCurrentFunctionName(false);
  		return SelectorUtil.textValue.get();
  	}
}