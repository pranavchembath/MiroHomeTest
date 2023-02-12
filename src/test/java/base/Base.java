package base;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	
	private WebDriver driver;
	public Properties prop;
	private File propFile;

	public WebDriver openBrowserAndApplicationBaseURI(String browser) {
		
		prop = new Properties();
		propFile = new File("src\\test\\resources\\data.properties");
		try {
			FileInputStream fis = new FileInputStream(propFile);
			prop.load(fis);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		
		switch(browser.toLowerCase()) {
	    case "chrome":
	        driver = WebDriverManager.chromedriver().create();
	        break;
	    case "firefox":
	    	driver = WebDriverManager.firefoxdriver().create();
	        break;
	    case "edge":
	    	driver = WebDriverManager.edgedriver().create();
	        break;
	    default:
	        System.out.println("Could not find the brower, running the test in chrome browser");
	        driver = WebDriverManager.chromedriver().create();
	        break;
	}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("implictTimeoutInSec"))));
		driver.get(prop.getProperty("url"));
		return driver;
	}
	
	public void waitForElementToLoad(WebDriver driver, WebElement element) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
}
