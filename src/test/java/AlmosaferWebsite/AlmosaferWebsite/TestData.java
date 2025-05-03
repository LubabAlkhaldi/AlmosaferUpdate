package AlmosaferWebsite.AlmosaferWebsite;

import java.time.LocalDate;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestData {

    // WebDriver instance for browser automation
    WebDriver driver = new ChromeDriver();
    String URL = "https://www.almosafer.com/en";  // The URL of the website being tested
    Random random = new Random();  // Random number generator for random test data
    int randomIndex = random.nextInt();  // Random index for various selections

    // Test 1: Expected language is English
    String ExpectedLang = "en";  

    // Test 2: Expected contact number
    String ExpectedNum = "+966554400000";  

    // Test 3: Expected currency is SAR
    String ExpectedCurrency = "SAR";  

    // Test 5-11: Expected value for the hotel tab not selected
    String ExpectedValue = "false";  

    // Test 6: Expected departure date, which is tomorrow
    int Tomorrow = LocalDate.now().plusDays(1).getDayOfMonth();
    String ExpectedDeparture = String.format("%01d", Tomorrow);  

    // Test 7: Expected return date, which is the day after tomorrow
    int DayAfterTomorrow = LocalDate.now().plusDays(2).getDayOfMonth();
    String ExpectedReturn = String.format("%02d", DayAfterTomorrow);  

    // Test 9: Random city selection in English
    String[] EnglishCities = { "Dubbai", "Jeddah", "riyadh" };
    int randomEnglishCity = random.nextInt(EnglishCities.length);
    String randomEn = EnglishCities[randomEnglishCity];  

    // Test 9: Random city selection in Arabic
    String[] ArabicCities = { "دبي", "جدة" };
    int randomArabicCity = random.nextInt(ArabicCities.length);
    String randomAr = ArabicCities[randomArabicCity];  

    // Test 12: Expected outcome after finishing hotel search
    boolean ExpectedFinshingSearchHotel = true;  
}