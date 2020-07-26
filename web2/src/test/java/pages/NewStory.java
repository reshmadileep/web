package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewStory {	
	
	
	@FindAll({		
		@FindBy(xpath="//input[@name='name']"),//Desktop Browser
		@FindBy(xpath="(//android.widget.EditText)[2]")//Mobile Android Browser	
	})private WebElement txtName;
	
	@FindAll({		
		  @FindBy(xpath="//span[text()='No associated feature']"),//Desktop Browser
		  @FindBy(xpath="//android.view.View[@text='No associated feature']")//Mobile Android Browser	
	})private WebElement ddFeature;
	
	@FindAll({		
		      @FindBy(xpath="//span[text()='Web Automation']"),
		      @FindBy(xpath="//android.view.View[@text=' Web Automation']")	
	})private WebElement ddSelectFeature;
	
	@FindAll({		
		@FindBy(xpath="//label[@for='feature']"),
	    @FindBy(xpath="//android.view.View[@text='Feature']")	
	})private WebElement labelFeature;
	
	@FindAll({		
	    @FindBy(xpath="//button[@hotkey-description=\"Create and continue\"]"),	
	    @FindBy(xpath="//android.widget.Button[@text='Create and continue']")	
	})private WebElement btnCreateAndContinue;
	
	public WebDriver driver;
	
	public NewStory(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


	public NewStory enterStoryName(String storyName) {
		
		WebDriverWait wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(txtName));
		txtName.sendKeys(storyName);
		return this;
		
	}
	
	public NewStory selectFeature() {
		
		ddFeature.click();
		ddSelectFeature.click();
		return this;
	}
	
	public NewStory clickCreateAndContinue() {
		
		btnCreateAndContinue.click();
		return this;	
	
}
}
