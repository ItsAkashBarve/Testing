package Testing;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Report {
	private WebDriver driver;
	private ExtentReports extent;
	private ExtentTest test;

	@BeforeClass
	public void setup() throws IOException {
		// Set up the ExtentReports HTML reporter
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// Set up the WebDriver
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();


		Properties properties = new Properties();
		FileInputStream inputstream = new FileInputStream("Data.properties");
		properties.load(inputstream);
		driver.get(properties.getProperty("URL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(priority = 1)
	public void testGoogle() throws IOException, InterruptedException {
		System.out.println("Page title is: " + driver.getTitle());
	//	JavascriptExecutor js = (JavascriptExecutor) driver;
	//	js.executeScript("window.scrollBy(0, 300)");
		
	

	}
	
	
	@Test(priority = 2)
	public void DataLoad() throws IOException, InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 300)");
		Properties properties = new Properties();
		FileInputStream inputstream = new FileInputStream("Data.properties");
		properties.load(inputstream);

		String Firstname = properties.getProperty("firstname");
		String LastName = properties.getProperty("lastname");
		String Email = properties.getProperty("email");
		String Mobile = properties.getProperty("mobile");
		String Address = properties.getProperty("address");

		WebElement First = driver.findElement(By.id("firstName"));
		First.sendKeys(Firstname);

		WebElement Last = driver.findElement(By.id("lastName"));
		Last.sendKeys(LastName);

		WebElement mail = driver.findElement(By.id("userEmail"));
		mail.sendKeys(Email);

		Thread.sleep(2000);

		driver.findElement(By.xpath("(//label[@class=\"custom-control-label\"])[1]")).click();

		Thread.sleep(3000);

		WebElement Mob = driver.findElement(By.id("userNumber"));
		Mob.sendKeys(Mobile);
		Thread.sleep(3000);
		js.executeScript("window.scrollBy(0, 400)");

		driver.findElement(By.xpath("(//label[@class=\"custom-control-label\"])[4]")).click();

		WebElement Adres = driver.findElement(By.id("currentAddress"));
		Adres.sendKeys(Address);

		js.executeScript("window.scrollBy(0, 200)");

		driver.findElement(By.id("submit")).click();

	}
	
	

	@Test(priority = 3)
	public void CellData() throws InterruptedException {
		Thread.sleep(3000);

		List<WebElement> allHeaders = driver.findElements(
				By.xpath("//table[@class='table table-dark table-striped table-bordered table-hover']/thead"));
		System.out.println(allHeaders.size());

		for (WebElement ele : allHeaders) {
			String value = ele.getText();
			System.out.println(value);
		}

		// WebElement table = driver.findElement(By.xpath("//table[@class='table
		// table-dark table-striped table-bordered table-hover']/tbody/tr")); // Adjust
		// the selector

		List<WebElement> alldata = driver.findElements(
				By.xpath("//table[@class='table table-dark table-striped table-bordered table-hover']/tbody/tr"));
		System.out.println("Total Rows are- "  +alldata.size());

		if (alldata.size() >= 1) {

			WebElement Row = alldata.get(4);

			List<WebElement> cells = driver.findElements(By
					.xpath("//table[@class='table table-dark table-striped table-bordered table-hover']/tbody/tr/td"));
			System.out.println("Total Cells are- " +cells.size());

			if (cells.size() >= 1) {
				// Get the third cell (index 2)
				String cellText = cells.get(9).getText();
				System.out.println("The data is: " + cellText);

			} else {
				System.out.println("Row does not contain enough columns.");
			}
		} else {
			System.out.println("The table does not contain enough rows.");
		}

		
	}
	
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
