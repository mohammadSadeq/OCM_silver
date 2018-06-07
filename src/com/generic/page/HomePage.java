package com.generic.page;


import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.generic.selector.CheckOutSelectors;
import com.generic.selector.HomePageSelectors;
import com.generic.setup.EnvironmentFiles;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.util.SelectorUtil;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
/**
 * The Class HomePage.
 */
public class HomePage extends SelTestCase {
	
	public static void prepareBaselineforLogs(String baselines) throws Exception {
		
		getCurrentFunctionName(true);
		for (String baseline : baselines.split(",")) {
			
		baseline = baseline+"_"+getBrowserName().replace(" ", "_");
		String VTAs =EnvironmentFiles.getVisualTestingAssetsPath();
		String baselineAbsPath = VTAs + "/" + baseline+ ".png";
		String logs_dir = EnvironmentFiles.getLogFilePath();	
    	File baseLineFile = new File(baselineAbsPath);
		FileUtils.copyFileToDirectory(baseLineFile, Paths.get(logs_dir).toFile());
		String baselinePathInLogs =  logs_dir + "/" + baseline + ".png";
		logs.debug("IMAGE:<br><a target=\"_blank\" href="+ baseline + ".png"+"><img src=" + baseline + ".png"+" alt=" + baseline + ".png"+" style=\"width:150px\"></a>");
		}
		getCurrentFunctionName(false);
	}
	
	public static void updateHeaderBaseline(String baseline) throws Exception {
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		getCurrentFunctionName(true);
		baseline = baseline+"_"+getBrowserName().replace(" ", "_");
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "guest mail", baseline));
		subStrArr.add(HomePageSelectors.header);
		valuesArr.add("VisualTesting");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String imagePath = EnvironmentFiles.getVisualTestingAssetsPath() + "/" + baseline+".png";
		ImageIO.write(SelectorUtil.screenShot.get().getImage(),"PNG",new File(imagePath));
		logs.debug("IMAGE:<br><a target=\"_blank\" href="+ baseline+".png"+"><img src=" + baseline+".png"+" alt=" + baseline+".png"+" style=\"width:150px\"></a>");
		getCurrentFunctionName(false);
	}

	public static void updateFooterBaseline(String baselines) throws Exception {
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		getCurrentFunctionName(true);
		int i = 0;
		for (String baseline : baselines.split(","))
		{
			baseline = baseline+"_"+getBrowserName().replace(" ", "_");
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "guest mail", baseline));
		if (baseline.contains("Top")){
			subStrArr.add(HomePageSelectors.footerTopSection);
		}
		else {
			subStrArr.add(HomePageSelectors.footerBottomSection);
		}
		valuesArr.add("VisualTesting");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String imagePath = EnvironmentFiles.getVisualTestingAssetsPath() + "/" + baseline+".png";
		ImageIO.write(SelectorUtil.screenShot.get().getImage(),"PNG",new File(imagePath));
		logs.debug("IMAGE:<br><a target=\"_blank\" href="+ baseline+".png"+"><img src=" + baseline+".png"+" alt=" + baseline+".png"+" style=\"width:150px\"></a>");
		subStrArr.clear();
		valuesArr.clear();
		}
		getCurrentFunctionName(false);
	}

	public static void updateBodyBaseline(String baselines) throws Exception {
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		getCurrentFunctionName(true);
		for (String baseline : baselines.split(","))
		{
			baseline = baseline+"_"+getBrowserName().replace(" ", "_");
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "guest mail", baseline));
		if(baseline.contains("TopNavLinks")) {
			subStrArr.add(HomePageSelectors.body_topNavLinks);
		}
		else if(baseline.contains("CategorySlider")) {
			subStrArr.add(HomePageSelectors.body_categorySlider);
		}
		else if(baseline.contains("ValuepropPromo")) {
			subStrArr.add(HomePageSelectors.body_valuepropPromo);
		}
		else if(baseline.contains("NavTabsContainer")) {
			subStrArr.add(HomePageSelectors.body_navTabsContainer);
		}
		else {
				subStrArr.add(HomePageSelectors.body);
		}
		valuesArr.add("VisualTesting");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String imagePath = EnvironmentFiles.getVisualTestingAssetsPath() + "/" + baseline+".png";
		ImageIO.write(SelectorUtil.screenShot.get().getImage(),"PNG",new File(imagePath));
		logs.debug("IMAGE:<br><a target=\"_blank\" href="+ baseline+".png"+"><img src=" + baseline+".png"+" alt=" + baseline+".png"+" style=\"width:150px\"></a>");
		subStrArr.clear();
		valuesArr.clear();
		}
		getCurrentFunctionName(false);
	}

	public static boolean verifyHeader(String baseline) throws Exception {
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		getCurrentFunctionName(true);
		baseline = baseline+"_"+getBrowserName().replace(" ", "_");
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "guest mail", baseline));
		subStrArr.add(HomePageSelectors.header);
		valuesArr.add("VisualTesting");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String imagePath = EnvironmentFiles.getLogFilePath() + "/" + baseline+"_actual.png";
		BufferedImage actualImage = SelectorUtil.screenShot.get().getImage();
		
		ImageIO.write(SelectorUtil.screenShot.get().getImage(),"PNG",new File(imagePath));
		logs.debug("IMAGE:<br><a target=\"_blank\" href="+ baseline+"_actual.png"+"><img src=" + baseline+"_actual.png"+" alt=" + baseline+"_actual.png"+" style=\"width:150px\"></a>");
		
		String BaseImagePath = EnvironmentFiles.getVisualTestingAssetsPath() + "/" + baseline+".png";
		
		BufferedImage expectedImage = ImageIO.read(new File(BaseImagePath));
        
		ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
		
		getCurrentFunctionName(false);
		return !diff.hasDiff();
	}
	
	public static boolean verifyFooter(String baselines) throws Exception {
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		getCurrentFunctionName(true);
		int i = 0;
		for (String baseline : baselines.split(",")) {
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "guest mail", baseline));
			if (baseline.contains("Top")){
				subStrArr.add(HomePageSelectors.footerTopSection);
			}
			else {
				subStrArr.add(HomePageSelectors.footerBottomSection);
			}
		valuesArr.add("VisualTesting");
		baseline = baseline+"_"+getBrowserName().replace(" ", "_");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String imagePath = EnvironmentFiles.getLogFilePath() + "/" + baseline+"_actual.png";
		BufferedImage actualImage = SelectorUtil.screenShot.get().getImage();
		
		ImageIO.write(SelectorUtil.screenShot.get().getImage(),"PNG",new File(imagePath));
		logs.debug("IMAGE:<br><a target=\"_blank\" href="+ baseline+"_actual.png"+"><img src=" + baseline+"_actual.png"+" alt=" + baseline+"_actual.png"+" style=\"width:150px\"></a>");
		
		String BaseImagePath = EnvironmentFiles.getVisualTestingAssetsPath() + "/" + baseline+".png";
		
		BufferedImage expectedImage = ImageIO.read(new File(BaseImagePath));
        
		ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
    	subStrArr.clear();
		valuesArr.clear();
		getCurrentFunctionName(false);
		return !diff.hasDiff();
		}
		return false;
	}

	public static boolean verifyBody(String baselines) throws Exception {
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		getCurrentFunctionName(true);
		for (String baseline : baselines.split(","))
		{
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "guest mail", baseline));
		if(baseline.contains("TopNavLinks")) {
			subStrArr.add(HomePageSelectors.body_topNavLinks);
		}
		else if(baseline.contains("CategorySlider")) {
			subStrArr.add(HomePageSelectors.body_categorySlider);
		}
		else if(baseline.contains("ValuepropPromo")) {
			subStrArr.add(HomePageSelectors.body_valuepropPromo);
		}
		else if(baseline.contains("NavTabsContainer")) {
			subStrArr.add(HomePageSelectors.body_navTabsContainer);
		}
		else {
				subStrArr.add(HomePageSelectors.body);
		}
		valuesArr.add("VisualTesting");
		baseline = baseline+"_"+getBrowserName().replace(" ", "_");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String imagePath = EnvironmentFiles.getLogFilePath() + "/" + baseline+"_actual.png";
		BufferedImage actualImage = SelectorUtil.screenShot.get().getImage();
		
		ImageIO.write(SelectorUtil.screenShot.get().getImage(),"PNG",new File(imagePath));
		logs.debug("IMAGE:<br><a target=\"_blank\" href="+ baseline+"_actual.png"+"><img src=" + baseline+"_actual.png"+" alt=" + baseline+"_actual.png"+" style=\"width:150px\"></a>");
		
		String BaseImagePath = EnvironmentFiles.getVisualTestingAssetsPath() + "/" + baseline+".png";
		
		BufferedImage expectedImage = ImageIO.read(new File(BaseImagePath));
        
		ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
    	subStrArr.clear();
		valuesArr.clear();
		getCurrentFunctionName(false);
		return !diff.hasDiff();
		}
		return false;
	}
    
}
