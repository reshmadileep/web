package stepDefinitions.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import stepDefinitions.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;



public class Hooks {
	
    private World world;
    private String testEnv = "dev";
    private WebDriver driver;
    DesiredCapabilities capabilities;

    public Hooks(World world) {
        this.world = world;
        System.out.println("Value of TEST_ENV is " + System.getenv("TEST_ENV"));
        testEnv = (System.getenv("TEST_ENV") == null) ? testEnv : System.getenv("TEST_ENV");
    }

   @Before(order=0)
    public void doSetupBeforeExecution() {
        Properties properties;
        String browser;
        String executionType;
        String gridUrl;
        String testAppUrl;
        String projectPath = System.getProperty("user.dir");
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("./src/test/resources/config/dev.properties")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        browser = properties.getProperty("browser");
        testAppUrl = properties.getProperty("testAppUrl");
        executionType = (System.getenv("EXECUTION_TYPE") == null) ? properties.getProperty("executionType") : System.getenv("EXECUTION_TYPE");
        if(executionType.equalsIgnoreCase("local")) {
            if (browser.equalsIgnoreCase("headless")) {
                System.setProperty("webdriver.chrome.driver",
                        projectPath + "\\src\\test\\resources\\drivers\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("window-size=1920,1080");
                options.addArguments("headless");
                driver = new ChromeDriver(options);
                driver.get(testAppUrl);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            } else {
                if (browser.equalsIgnoreCase("firefox")) {
                    System.setProperty("webdriver.gecko.driver",
                            projectPath + "\\src\\test\\resources\\drivers\\geckodriver.exe");
                    driver = new FirefoxDriver();
                } else if (browser.equalsIgnoreCase("chrome")) {
                    System.setProperty("webdriver.chrome.driver",
                            projectPath + "\\src\\test\\resources\\drivers\\chromedriver.exe");
                    driver = new ChromeDriver();
                } else if (browser.equalsIgnoreCase("internetExplorer")) {
                    System.setProperty("webdriver.ie.driver",
                            projectPath + "\\src\\test\\resources\\drivers\\MicrosoftWebDriver.exe");
                    driver = new InternetExplorerDriver();
                }
                driver.get(testAppUrl);
                driver.manage().window().maximize();
            }
        }
        else{
            gridUrl = properties.getProperty("gridUrl");
            DesiredCapabilities capabilities = null;
            if(browser.equalsIgnoreCase("firefox"))
                capabilities = DesiredCapabilities.firefox();
            else if(browser.equalsIgnoreCase("chrome"))
                capabilities = DesiredCapabilities.chrome();
            else if(browser.equalsIgnoreCase("internetExplorer"))
                capabilities = DesiredCapabilities.internetExplorer();
            else if(browser.equalsIgnoreCase("edge"))
                capabilities = DesiredCapabilities.edge();

            capabilities.setBrowserName(browser);
            try {
                driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
            }catch(Exception e){
                e.printStackTrace();
            }
            ((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
            driver.get(testAppUrl);
            driver.manage().window().maximize();
        }
    
        HashMap<String, String> map= new HashMap<String, String>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put((String) entry.getKey(), (String) entry.getValue());
        }
        this.world.context.put("config", map);
        this.world.context.put("testEnv", testEnv.toLowerCase());
        world.context.put("driver", driver);
    }
        
    
  //Experimenting tagged hooks for single framework  
    
  /*  @Before(order=0,value="@DesktopBrowser")
    public void setUpDesktopBrowser() {
         Properties properties;
          String browser;
          String url;
          String projectPath = System.getProperty("user.dir");
          properties = new Properties();
          try {
              properties.load(new FileInputStream(new File("./src/test/resources/config/dev.properties")));
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }

          browser = properties.getProperty("browser");
          url = properties.getProperty("testAppUrl");
          
          if (browser.equalsIgnoreCase("headless")) {
             System.setProperty("webdriver.chrome.driver",
                      projectPath + "\\src\\test\\resources\\drivers\\chromedriver.exe");
             ChromeOptions options=new ChromeOptions();    
             options.addArguments("window-size=1920,1080");
             options.addArguments("headless");
             driver=new ChromeDriver(options);
             driver.get(url);  
              driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
              
          } else {  
          
          if (browser.equalsIgnoreCase("Firefox")) {
              System.setProperty("webdriver.chrome.driver",
                      projectPath + "\\src\\test\\resources\\drivers\\geckodriver.exe");
              driver = new FirefoxDriver();
          } else if (browser.equalsIgnoreCase("Chrome")) {
              System.setProperty("webdriver.chrome.driver",
                      projectPath + "\\src\\test\\resources\\drivers\\chromedriver.exe");
              driver = new ChromeDriver();
          } else if (browser.equalsIgnoreCase("IE")) {
              System.setProperty("webdriver.ie.driver",
                      projectPath + "\\src\\test\\resources\\drivers\\MicrosoftWebDriver.exe");
              driver = new InternetExplorerDriver();
          }
          driver.get(url);
          driver.manage().window().maximize(); 
          
          }               
      
          HashMap<String, String> map= new HashMap<String, String>();
          for (Map.Entry<Object, Object> entry : properties.entrySet()) {
              map.put((String) entry.getKey(), (String) entry.getValue());
          }
          this.world.context.put("config", map);
          this.world.context.put("testEnv", testEnv.toLowerCase());
          world.context.put("driver", driver);       
            
       
    }
    
    @Before(order=0,value="@MobileBrowser")
    public void setUpMobileBrowser() throws MalformedURLException {
    HashMap<String, String> map;
       File appDir, app;
       capabilities = new DesiredCapabilities();
       Properties properties = new Properties();
       String projectPath = System.getProperty("user.dir");

       try {
              properties.load(new FileInputStream(new File("./src/test/resources/config/dev.properties")));
       } catch (FileNotFoundException e) {
              e.printStackTrace();
       } catch (IOException e) {
              e.printStackTrace();
       }

       map = new HashMap<String, String>();
       for (Map.Entry<Object, Object> entry : properties.entrySet()) {
              map.put((String) entry.getKey(), (String) entry.getValue());
       }

       world.context.put("config", map);
    String url=map.get("testAppUrl");

       capabilities.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);

       if (map.get("DeviceOS").equalsIgnoreCase("Android")) {
              capabilities.setCapability("platformName", "Android");
              if (map.get("Emulator").equalsIgnoreCase("NO")) {
                     capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
              } else {
                     capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
              }
              if (map.get("Executeon").equalsIgnoreCase("browser")) {
                     capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
                     capabilities.setCapability("chromedriverExecutable",
                                  projectPath + "\\src\\test\\resources\\drivers\\chromedriver.exe");
              } else {
                     appDir = new File("src");
                     app = new File(appDir, "ApiDemos-debug.apk");
                     capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
              }
              driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
       } else {
              capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
              capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"12.1");
           
              
              capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone XS");
              capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);

              if (map.get("Emulator").equalsIgnoreCase("NO")) {
                     capabilities.setCapability(MobileCapabilityType.UDID, map.get("IOSDeviceUDID"));
              }
              if (map.get("Executeon").equalsIgnoreCase("browser")) {
                     capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "safari");
                     capabilities.setCapability("safaridriverExecutable",
                                  projectPath + "\\src\\test\\resources\\drivers\\selenium-safari-driver-2.29.1.jar");
            
              } else {

                     capabilities.setCapability(MobileCapabilityType.APP, projectPath + "\\src\\UICatalog.app");
              }
          //    driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
              driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
       }
       driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
       world.context.put("testEnv", testEnv.toLowerCase());
       world.context.put("driver", driver);          
       
    driver.get(url);      
       
    } 
    */


    @After(order=0)
    public void doCleanupAfterExecution(Scenario scenario){
        if (scenario.isFailed()) {
            TakesScreenshot browser = (TakesScreenshot) new Augmenter().augment(driver);
            final byte[] screenshot = browser.getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
            scenario.write("URL: " + driver.getCurrentUrl());
        }
       driver.quit();
    }
}
