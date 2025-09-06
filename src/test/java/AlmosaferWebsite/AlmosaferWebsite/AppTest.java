package AlmosaferWebsite.AlmosaferWebsite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	public void setup(){
		driver.manage().deleteAllCookies();
	    driver.get(URL);
	    driver.manage().window().maximize(); 
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // Set implicit wait time
	}

    // Check if the default language is English
	@Test(priority = 1)
	public void englishDefaultLanguage(){ 
	    String ActualLang = driver.findElement(By.tagName("html")).getDomAttribute("lang"); 
	    Assert.assertEquals(ActualLang, ExpectedLang); // Verify if language is English
	}

    // Check if the displayed contact number is correct
	@Test(priority = 2)
	public void checkTheContactNum(){ 
	    // Check if the displayed contact number is correct
	    WebElement contactNum = driver.findElement(By.cssSelector(".alm-desktop-h0bow9"));
	    String ActualContactNum = contactNum.getText();
	    Assert.assertEquals(ActualContactNum, ExpectedNum); // Verify contact number
	}

    // Check if the currency is displayed as SAR
	@Test(priority = 3)
	public void checkTheCurrencyIsSAR(){
	    WebElement Currency = driver.findElement(By.xpath("//div[@data-testid='Header__CurrencySelector']"));
	    String ActualCurrency = Currency.getText();
	    Assert.assertEquals(ActualCurrency, ExpectedCurrency); // Verify currency is SAR
	    System.out.println(ActualCurrency);    
	}

    // Check if Qitaf logo is displayed
	@Test(priority = 4)
	public void qitafLogo() throws InterruptedException{
	    driver.findElement(By.xpath("//button[contains(., 'Kingdom of Saudi Arabia')]")).click();
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    
	    WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
	    WebElement qtfLogo = footer.findElement(By.xpath(".//*[name()='svg']"));
	  
	    Assert.assertTrue(qtfLogo.isDisplayed()); // Verify Qitaf logo is visible
	}

    // Check if the "Hotels" tab is not selected by default
	@Test(priority = 5)
	public void hotelTab(){
	    WebElement stays = driver.findElement(By.cssSelector("#tab-hotels"));
	    String ActualValue = stays.getDomAttribute("aria-selected");
	    Assert.assertEquals(ActualValue, ExpectedValue); // Verify if the hotel tab is unselected
	    System.out.println(ActualValue);
	    Assert.assertEquals(stays.getText(), "Stays"); // Verify text match

	}

    // Check if the departure date is correct
	@Test(priority = 6)
	public void FlightDatesDeparture(){
		WebElement departureInput = driver.findElement(By.id("testIdPickerPrefix__DatePicker__DepartureDate"));
		String actualDeparture = departureInput.getAttribute("value");  // get the date from input
		Assert.assertTrue(actualDeparture.contains(ExpectedDeparture)); // Verify departure date
		System.out.println("Departure text: " + actualDeparture);
	}

    // Check if the return date is correct
	@Test(priority = 7)
	public void FlightDatesReturn() throws InterruptedException{
	    WebElement dateReturn = driver.findElement(By.id("testIdPickerPrefix__DatePicker__ArrivalDate"));
	    
	    String actualReturn = dateReturn.getAttribute("value");
	    String dayOnly = actualReturn.split(",")[1].trim().split(" ")[0];
	    String dayFormatted = String.format("%02d", Integer.parseInt(dayOnly)); 

	    Assert.assertEquals(dayFormatted, ExpectedReturn);  
	    System.out.println("Departure text: " + actualReturn);
	}

    // Change the language randomly between English and Arabic
	@Test(priority = 8)
	public void RandomlyLanguage(){
	    String[] URLs = {"https://www.almosafer.com/en", "https://www.almosafer.com/ar"};
	    int RandomIndex = random.nextInt(URLs.length);
	    driver.get(URLs[RandomIndex]); // Change language randomly
	}

    // Input city name in the hotel search tab
	@Test(priority = 9)
	public void HotelSearchTabCityInput() throws InterruptedException{
	    WebElement staysBtn = driver.findElement(By.id("tab-hotels"));
	    staysBtn.click();

	    WebElement locationInput = driver.findElement(By.id("DesktopSearchWidget_Destination_InputField_Test_Id"));
	    locationInput.click();

	    String pageLang = driver.findElement(By.tagName("html")).getAttribute("lang");

	    String randomCity = CitiesData.getRandomCity(pageLang);

	    locationInput.sendKeys(randomCity);
	    Thread.sleep(2000);
	    locationInput.sendKeys(Keys.ARROW_DOWN);
	    locationInput.sendKeys(Keys.ENTER);     
	}
	
	// Select room options randomly between index 0 or 1
	@Test(priority = 10)
	public void selectRoomOptionsRandomly() throws InterruptedException{
	    WebElement rooms = driver.findElement(By.cssSelector("div[class='__ds__comp undefined MuiBox-root alm-desktop-18m64ko'] div[class='MuiFormControl-root MuiTextField-root __ds__comp alm-desktop-72qxmm'] div[class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-formControl MuiInputBase-adornedStart alm-desktop-116o6k1']"));
	    rooms.click();
	    Thread.sleep(2000);
	    
	    List<WebElement> options = driver.findElements(By.cssSelector("body > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1)"));
	    int optionsCount = options.size();
	    
	    // Pick either index 0 or 1 randomly
	    if (optionsCount > 1){
	        int randomIndex = new Random().nextInt(2); // Choose between index 0 or 1
	        options.get(randomIndex).click(); // Select either index 0 or 1
	    } 
	    else{
	        options.get(0).click(); // Select the first option if only one option exists
	    }
	}

    // Click the "Search Hotels" button
	@Test(priority = 11)
	public void clickSearchHotels() throws InterruptedException{
		WebElement searchBtn = driver.findElement(By.cssSelector("[data-testid='DesktopSearchWidget_Button_Test_Id']"));
	    Thread.sleep(2000);
	    searchBtn.click();
	    Thread.sleep(2000);
	}
	
    // Wait for the search results to appear
	@Test(priority = 12)
	public void waitForSearchResults(){
	    // Wait for the search result to appear and verify it contains "found" or "مكان"
		String resultText = new WebDriverWait(driver, Duration.ofSeconds(100)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-testid='srp_properties_found']"))).getText();
		Assert.assertTrue(resultText.contains("found") || resultText.contains("مكان"));
	}
}