package pages;

import java.io.File;import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class Dashboard {

	
	@FindAll({
		@FindBy(xpath ="//h5[@class=\"ng-binding\"][contains(text(),\"QATeam\")]"),
		@FindBy(xpath ="//android.view.View[@text=\" QATeam\"]"),
		@FindBy(xpath ="//XCUIElementTypeStaticText[@name=\" QATeam\"]")
		
	})private WebElement txtVerifyDashboard;

	@FindAll({
		@FindBy(xpath="//a[@class='btn btn-default'][text()='New story']"),
		@FindBy(xpath="//android.view.View[@text=\"New story\"]")
	})private WebElement btnNewStory;

	@FindAll({
		@FindBy(xpath="//*[text()='Test']"),
		@FindBy(xpath="//android.view.View[@text='Test']")
	})private WebElement checkStory;

	@FindAll({
		@FindBy(xpath="//a[@class=\"ng-binding\"][contains(text(),\"Delete\")]"),	
		@FindBy(xpath="//android.view.View[@text=\"Delete\"]")
	})private WebElement selDelete;


	@FindAll({
		@FindBy(xpath="//button[@type=\"submit\"]"),
		@FindBy(xpath="//android.widget.Button[@text=\"Delete\"]")
	})private WebElement btnDelete;

	@FindAll({ 
		@FindBy(xpath="//button/input[@type=\"file\"]"),
		@FindBy(xpath="//android.widget.Button[@text=' Upload a file']")
	})private WebElement fileUpload;


	@FindAll({
		@FindBy(xpath="//a[@class=\"ng-binding\"][text()=\"ab.xlsx\"]"),
		@FindBy(xpath="//android.view.View[@text='ab.xlsx']")
	})private WebElement txtVerifyUpload;


	@FindAll({
		@FindBy(xpath="//button[@type=\"submit\"]"),
		@FindBy(xpath="//android.view.View[@text='Save']")
	})	private WebElement btnSave;

	@FindAll({
		@FindBy(xpath="//div[@class=\"panel-body\"]"),
		@FindBy(xpath="(//android.view.View)[1]")
	})private WebElement panelBody;

	@FindAll({
		@FindBy(xpath="(//div[@class=\"content without-description\"])[3]"),
		@FindBy(xpath="(//android.view.View[@text='Test']")
	})private WebElement selectStory;

	@FindAll({
		@FindBy(xpath="//a[@class='btn btn-default ng-scope']"),
		@FindBy(xpath="//android.view.View[@text=\"New task\"]")
	})private WebElement btnNewTask;


	@FindAll({	
		@FindBy(xpath="(//i[@class='fa fa-caret-down'])[6]"),
		@FindBy(xpath="(//android.widget.Spinner[@text=''])[1]")
	})private WebElement ddDelete;	

	private WebDriver driver;	
	public Dashboard(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public Dashboard verifyTitle() {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	

		if(driver.getTitle().equals("iceScrum - WebAutomation"))			
			System.out.println("Logged in Successfully");

		else			
			System.out.println("Login Unsuccessful");		
		return this;


	}

	public void verifyDashboardPage() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	

		if(txtVerifyDashboard.isDisplayed())
		{ System.out.println("In DashboardPage");
		System.out.println("Logged in Successfully");
		}
		else			
			System.out.println("Dashboard not launched.Login Unsuccessful");	
	}

	public Dashboard clickCreateTask() {	

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(btnNewTask));
		btnNewTask.click();	
		return this;
	}	

	public Dashboard clickNewStory() {		

		if(btnNewStory.isEnabled())
			btnNewStory.click();
		else
			System.out.println("Button New story not found");		
		return this;
	}

	public Dashboard checkCreatedStory(String storyName) {

		if(checkStory.getText().contains(storyName))
			System.out.println("The story "+storyName+" has been created in Dashboard");
		else	
			System.out.println("The story has not been created in Dashboard");
		return this;

	}

	public Dashboard deleteStory() {   	   

		panelBody.click();	
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(selectStory));
		selectStory.click();       
		ddDelete.click();
		selDelete.click();
		btnDelete.click();
		return this;

	}

	public Dashboard uploadFileStory() throws IOException {	

		panelBody.click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(selectStory));
		selectStory.click();	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String path=System.getProperty("user.dir");	
		fileUpload.sendKeys(path+"\\src\\test\\resources\\dataFiles\\ab.xlsx");	
		return this;
	}

	public Dashboard verifyUpload() {		
		panelBody.click();
		btnSave.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		selectStory.click();	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(txtVerifyUpload.isDisplayed())
			System.out.println("File Uploaded");
		else
			System.out.println("File  Not Uploaded");
		return this;

	}

}

