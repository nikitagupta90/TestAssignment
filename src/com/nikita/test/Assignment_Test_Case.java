/**
 * 
 */
package com.nikita.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



/**
 * @author Nikita Gupta
 *
 */
public class Assignment_Test_Case {
	WebDriver driver;
	private static final String USERNAME="UPDATE_ME";
	private static final String PASSWORD="UPDATE_ME";
	private static final String PORTAL_USER="UPDATE_ME";
	
	@Test
	public void assignment_test_case() throws Exception {
		
		/*Open the URL*/
		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append("https://").append(USERNAME).append(":").append(PASSWORD).append("@bgp-qa.gds-gov.tech");
		
		String url = urlBuffer.toString();
		System.setProperty("webdriver.chrome.driver","driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		
		/*User- login */
		
		WebElement login = driver.findElement(By.id("login-button"));
		login.click();
		
		WebElement username = driver.findElement(By.name("CPUID"));
		username.clear();
		username.sendKeys(PORTAL_USER);
		
		//WebElement user_login = driver.findElement(By.cssSelector("Button"));
		username.submit();
		Thread.sleep(15000);
		
		/* Getting a new Grant */
		
		WebElement grant = driver.findElement(By.cssSelector("#grants a:nth-child(2) .dashboard-action-title"));
		grant.click();
		Thread.sleep(10000);
		
		/* Options in Grant selection */
		
		WebElement building_construction = driver.findElement(By.id("Building & Construction"));
		building_construction.click();
		
		/* Drop down selection for Building and construction */
		
		WebElement building_dropdown=driver.findElement(By.id("Builders (Contractors)"));
		building_dropdown.click();
		
		/* Selection of purpose of the grant */
		
		
		selectGrantPurpose();
		
		/* Selection of area for the grant */
		Thread.sleep(3000);
		WebElement grant_area = driver.findElement(By.id("Pre-scoped Productivity Solutions"));
		grant_area.click();
		
		/* Apply for the grant */
		Thread.sleep(3000);
		WebElement apply = driver.findElement(By.id("go-to-grant"));
		apply.click();
		
		/* Finalize the application */
		Thread.sleep(3000);
		WebElement finalize = driver.findElement(By.id("keyPage-form-button"));
		finalize.click();
		
		/* Acceptance criteria - 1-1 */
		Thread.sleep(7000);
		WebElement next_button = driver.findElement(By.id("next-btn"));
		
		WebElement radio_true = driver.findElement(By.id("react-eligibility-user_agreement_check-true"));
		
		List<WebElement> side_menus =  (List<WebElement>) driver.findElements(By.xpath("//*[@id=\"js-app\"]/div/div/div[2]/div[1]/div/div/ul/li"));
		
		acceptance_criteria_1(side_menus, next_button);
		
		radio_true.click();
		
		/* Acceptance criteria - 1-2 */
		acceptance_criteria_2(side_menus, next_button);
		
		WebElement radio_false = driver.findElement(By.id("react-eligibility-user_agreement_check-false"));
		radio_false.click();
		
		//check items in AC 1-1 are disabled.
		acceptance_criteria_1(side_menus, next_button);
		
		WebElement warning_message = driver.findElement(By.className("eligibility-advice"));
		
		checkWarningMessage(warning_message);
		
		WebElement sme_portal_href = driver.findElement(By.xpath("//*[@id=\"js-app\"]/div/div/div[2]/div[2]/div/div/div[1]/span/div/span/a"));
		sme_portal_href.click();
		//driver.switchTo().window(arg0)
		
//		WebElement next_button = driver.findElement(By.id("next-btn"));
		next_button.click();
	
		driver.close();
		driver.quit();
		
	}

	private void selectGrantPurpose() throws Exception {
		Thread.sleep(3000);
		WebElement grant_purpose = driver.findElement(By.id("Capability Development"));
		System.out.println("Grant Purpose element " + grant_purpose.getText());
		grant_purpose.click();
		
	}
	
	private void acceptance_criteria_1(List<WebElement> side_menus, WebElement next_button ) {
		assertFalse("Next button is disabled.", next_button.isEnabled());
		assertEquals("active", side_menus.get(0).getAttribute("class"));
		
		for(int i = 1; i< side_menus.size(); i++) {
			assertEquals("disabled", side_menus.get(i).getAttribute("class"));
		}
	}
	
	private void acceptance_criteria_2(List<WebElement> side_menus, WebElement next_button ) {
		assertTrue("Next button is not disabled.", next_button.isEnabled());
		assertEquals("active", side_menus.get(0).getAttribute("class"));
		for(int i = 0; i< side_menus.size(); i++) {
			assertNotEquals("disabled", side_menus.get(i).getAttribute("class"));
		}
	}
	
	private void checkWarningMessage(WebElement warning_message) {
		System.out.println("Warning message is " + warning_message.getText());
		assertEquals("Visit Smart Advisor on the SME Portal for more information on other government assistance.", warning_message.getText());
		
	}
}
