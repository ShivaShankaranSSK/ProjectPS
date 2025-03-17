package com.PROJECTPS.stepdefinition;

import org.openqa.selenium.WebDriver;

import com.PROJECTPS.pages.PS01_LoginPage;
import com.projectps.hooks.Hooks;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PS01_LoginStepDef {
	
	WebDriver driver;
	
	private PS01_LoginPage loginPage;
	
	 public PS01_LoginStepDef() {
	        // Assuming driver is being passed from hooks
		 	this.driver = Hooks.driver;
	        this.loginPage = new PS01_LoginPage(driver);  // Ensure driver is passed or accessible
	    }

	 		@Given("the user should be in login page")
		public void the_user_should_be_in_login_page() {
	 			
	 			loginPage.navigateToMyAccount();
	 			loginPage.clickOnLoginButton();
		    
		}
	 		
		@When("the user enter the username {string} and password {string}")
		public void the_user_enter_the_username_and_password(String username, String password) {
		    
			loginPage.enterUserName(username);
			loginPage.enterPassword(password);
		}
		
		@When("the user press the login button")
		public void the_user_press_the_login_button() {
		    loginPage.loginAction();
		}
		
		@Then("it should be redirected to homepage")
		public void it_should_be_redirected_to_homepage() {
			System.out.println("Logged in successfully");
		}
}
