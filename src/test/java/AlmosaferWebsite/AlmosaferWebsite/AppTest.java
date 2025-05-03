package AlmosaferWebsite.AlmosaferWebsite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class AppTest extends TestData{

	// Open site and maximize browser
	@BeforeTest
	public void Setup() {
	    driver.get(URL);
	    driver.manage().window().maximize(); 
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // Set implicit wait time
	}

    // Check if the default language is English
	@Test(priority = 1)
	public void EnglishDefaultLanguage() { 
	    String ActualLang = driver.findElement(By.tagName("html")).getDomAttribute("lang"); 
	    Assert.assertEquals(ActualLang, ExpectedLang); // Verify if language is English
	}

    // Check if the displayed contact number is correct
	@Test(priority = 2)
	public void CheckTheContactNum() { 
	    // Check if the displayed contact number is correct
	    WebElement contactNum = driver.findElement(By.cssSelector(".sc-cjHlYL.gdvIKd"));
	    String ActualContactNum = contactNum.getText();
	    Assert.assertEquals(ActualContactNum, ExpectedNum); // Verify contact number
	}

    // Check if the currency is displayed as SAR
	@Test(priority = 3)
	public void CheckTheCurrencyIsSAR() {
	    WebElement Currency = driver.findElement(By.cssSelector(".sc-hUfwpO.kAhsZG"));
	    String ActualCurrency = Currency.getText();
	    Assert.assertEquals(ActualCurrency, ExpectedCurrency); // Verify currency is SAR
	    System.out.println(ActualCurrency);    
	}

    // Check if Qitaf logo is displayed
	@Test(priority = 4)
	public void QitafLogo() throws InterruptedException {
	    driver.findElement(By.xpath("//button[contains(., 'Kingdom of Saudi Arabia')]")).click();
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
	    WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
	    WebElement qitafLogo = footer.findElement(By.xpath(".//*[name()='svg']"));
	  
	    Assert.assertTrue(qitafLogo.isDisplayed()); // Verify Qitaf logo is visible
	}

    // Check if the "Hotels" tab is not selected by default
	@Test(priority = 5)
	public void HotelTab() {
	    WebElement HotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
	    String ActualValue = HotelTab.getDomAttribute("aria-selected");
	    Assert.assertEquals(ActualValue, ExpectedValue); // Verify if the hotel tab is unselected
	    System.out.println(ActualValue);
	}

    // Check if the departure date is correct
	@Test(priority = 6)
	public void FlightDatesDeparture() {
	    String ActualDepature = driver.findElement(By.xpath("//div[@data-testid='FlightSearchBox__FromDateButton']")).getText();
	    Assert.assertTrue(ActualDepature.contains(ExpectedDeparture)); // Verify departure date
	    System.out.println("Departure text: " + ActualDepature);
	}

    // Check if the return date is correct
	@Test(priority = 7)
	public void FlightDatesReturn() {
	    String ActualReturn = driver.findElement(By.xpath("//div[@data-testid='FlightSearchBox__ToDateButton']")).getText();
	    Assert.assertTrue(ActualReturn.contains(ExpectedReturn)); // Verify return date
	    System.out.println("Return text: " + ActualReturn);
	}

    // Change the language randomly between English and Arabic
	@Test(priority = 8)
	public void RandomlyLanguage() {
	    String[] URLs = { "https://www.almosafer.com/en", "https://www.almosafer.com/ar", };
	    int RandomIndex = random.nextInt(URLs.length);
	    driver.get(URLs[RandomIndex]); // Change language randomly
	}

    // Input city name in the hotel search tab
	@Test(priority = 9)
	public void HotelSearchTabCityInput() throws InterruptedException {
	    Thread.sleep(2000);
	    WebElement header = driver.findElement(By.tagName("header"));
	    Thread.sleep(3000);
	    WebElement staysLink = header.findElement(By.xpath("//a[@data-testid='Header__HotelsNavigationTab']"));
	    staysLink.click();
	    Thread.sleep(2000);
	    String lang = driver.findElement(By.tagName("html")).getDomAttribute("lang");
	    WebElement input = driver.findElement(By.xpath("//input[@data-testid='AutoCompleteInput']"));
	    Thread.sleep(1000);
	    if(lang.contains("en")) {
	        input.sendKeys(randomEn); // Enter English city
	    } else {
	        input.sendKeys(randomAr); // Enter Arabic city
	    }
	}
	
	// Select room options randomly between index 0 or 1
	@Test(priority = 10)
	public void selectRoomOptionsRandomly() throws InterruptedException {
	    WebElement rooms = driver.findElement(By.xpath("//select[@data-testid='HotelSearchBox__ReservationSelect_Select']"));
	    rooms.click();
	    Thread.sleep(2000);
	    
	    List<WebElement> options = driver.findElements(By.xpath("//select[@data-testid='HotelSearchBox__ReservationSelect_Select']"));
	    int optionsCount = options.size();
	    
	    // Pick either index 0 or 1 randomly
	    if (optionsCount > 1) {
	        int randomIndex = new Random().nextInt(2); // Choose between index 0 or 1
	        options.get(randomIndex).click(); // Select either index 0 or 1
	    } 
	    else {
	        options.get(0).click(); // Select the first option (index 0) if only one option exists
	    }
	}

    // Click the "Search Hotels" button
	@Test(priority = 11)
	public void clickSearchHotels() throws InterruptedException {
	    WebElement search = driver.findElement(By.xpath("//button[@data-testid='HotelSearchBox__SearchButton']"));
	    Thread.sleep(2000);
	    search.click();
	}
	
    // Wait for the search results to appear
	@Test(priority = 12)
	public void waitForSearchResults() {
	    // Wait for the search result to appear and verify it contains "found" or "مكان"
	    String resultText = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid='srp_properties_found']"))).getText();
	    Assert.assertTrue(resultText.contains("found") || resultText.contains("مكان"));
	}
}