package com.practicce.amazon.amazon;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class buyHeadPhones {
  @Test
  public void AddBestSellersToCart() throws InterruptedException {
	  System.out.println("Launching Chrome Browser");
	  String driverPath = System.getProperty("user.dir")+"\\src\\drivers\\";
	  ChromeDriver driver;
	  System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.manage().deleteAllCookies();
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	  System.out.println("In test 1");
	  driver.get("http://amazon.com");
	  driver.findElementByCssSelector("input[id=twotabsearchtextbox]").sendKeys("headphones");
	  driver.findElementByCssSelector("input[value='Go']").click();
	  // Xpath for best seller items //div[@class='s-include-content-margin s-border-bottom']//span[contains(@data-component-props,'best-seller')]/parent::*/parent::*/parent::*/parent::*//a[@class='a-link-normal a-text-normal']
	  
	
	 
	  List<WebElement> ele=driver.findElementsByXPath("//div[@class='s-include-content-margin s-border-bottom']//span[contains(@data-component-props,'best-seller')]/parent::*/parent::*/parent::*/parent::*//a[@class='a-link-normal a-text-normal']");
	  int noOfBestSellers = ele.size();
	  for(WebElement element:ele) {
		  String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
		  element.sendKeys(selectLinkOpeninNewTab);
	  }
	  
	  String strCurrentWindow=driver.getWindowHandle();
	  Set<String> allWindowHandles=driver.getWindowHandles();
	  
	  for(String strWindow:allWindowHandles) {
		  if(!strWindow.equals(strCurrentWindow)) {
		  driver.switchTo().window(strWindow);
		  driver.findElementByCssSelector("input[id='add-to-cart-button']").click();
		  
		  driver.navigate().refresh();
		  driver.findElementByCssSelector("span[id='nav-cart-count']").click();
		  
		  driver.close();
		  }
	  }
	  
	  driver.switchTo().window(strCurrentWindow);
	  driver.navigate().refresh();
	  int cartVal=Integer.parseInt(driver.findElementByCssSelector("span[id='nav-cart-count']").getText());
	  Assert.assertEquals(cartVal,noOfBestSellers);
	  driver.quit();
  }
}
