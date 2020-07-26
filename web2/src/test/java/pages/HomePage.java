package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//import io.appium.java_client.android.AndroidDriver;

public class HomePage {

	private WebDriver driver;	

	@FindAll({
		@FindBy(id="credentials.j_username"), //Desktop Browser
		@FindBy(id="credentials.j_username"),//Android Browser
		@FindBy(xpath="(//*[@type='XCUIElementTypeTextField'])[2]")
	})private WebElement txtUsername;		

	@FindAll({
		@FindBy(id="credentials.j_password"),//Desktop Browser
		@FindBy(id="credentials.j_password"),//Android Browser
		@FindBy(xpath="XCUIElementTypeSecureTextField")
	})private WebElement txtPassword;

	@FindAll({
		@FindBy(xpath="//button[@class='btn btn-primary pull-right']"),//Desktop Browser
		@FindBy(xpath="(//android.widget.Button[@text=\"Log in\"])[2]"),	//Android Browser
		@FindBy(xpath="(//*[@type='XCUIElementTypeButton'][@name='Log in'])[2]")
	})private WebElement btnLogin;

	@FindAll({
		@FindBy(xpath="//div[@class='ng-binding notification-error'][contains(text(),'Login error, please check your username and your password')]"),//Desktop Browser
		@FindBy(xpath="//[@text()=\"Login error, please check your username and your password\"")//Android Browser
		//unable to capture notification error in safari
	})private WebElement checkError;


	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


	public HomePage verifyTitle() {
		if(driver.getTitle().equals("iceScrum -"))
			System.out.println("HomePage launched successfully");
		else
			System.out.println("HomePage not launched");
		return this;


	}


	public void verifyHomePage() {

		if(btnLogin.isDisplayed())
			System.out.println("Home Page is launched");

		else
			System.out.println("Home Page is not launched");


	}

	public HomePage enterUsername(String username) {

		txtUsername.clear();
		txtUsername.sendKeys(username);		
		return this;
	}

	public HomePage enterPassword(String password) {

		txtPassword.clear();
		txtPassword.sendKeys(password);
		return this;
	}

	public HomePage clickLogin() {

		btnLogin.click();

		return this;


	}

	public void checkError() {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if(checkError.isDisplayed())
			System.out.println("Error message is displayed for invalid credentials");
		else
			System.out.println("No error message is displayed for invalid credentials");


	} 

	public HomePage loginWithDefaultCredentials() {
		verifyTitle()
		.enterUsername("Admin")
		.enterPassword("Admin123")
		.clickLogin();

		return this;

	}


}

