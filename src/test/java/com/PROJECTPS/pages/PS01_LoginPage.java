package com.PROJECTPS.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class PS01_LoginPage {

	// web driver as global variable
	WebDriver driver;
	
	// constructor for initializing web driver 
	public PS01_LoginPage(WebDriver driver) {
		
		this.driver = driver;
	}
	
	//Web Elements Capture
	
	private By myAccount = By.xpath("//a[@title='My Account']");
		
	private By loginButton = By.xpath("//li//a[text()='Login']");
	private By userName = By.xpath("//input[@id='input-email']");
	private By passWord = By.xpath("//input[@id='input-password']");
	private By loginClick = By.xpath("//input[@type='submit']");
	
	public void navigateToMyAccount() {
		
		Actions action = new Actions (driver);
		action.moveToElement(driver.findElement(myAccount)).build().perform();
		
	}
	
 public void clickOnLoginButton() {
	
	 driver.findElement(loginButton).click();
}	

 public void enterUserName(String username) {
	
	 driver.findElement(userName).sendKeys(username);
}
	
public void enterPassword(String password) {
	
	driver.findElement(passWord).sendKeys(password);

}	

public void loginAction() {
	
	driver.findElement(loginClick).click();

}
		
	
}
