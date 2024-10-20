import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FlipkartSortingTest {

    private WebDriver driver;
    private String searchTerm;
    private String sortOption;
    private int pageLimit;

    @BeforeClass
    public void setup() throws IOException {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode data = objectMapper.readTree(new File("src/main/resources/data.json"));
        searchTerm = data.get("searchTerm").asText();
        sortOption = data.get("sortOption").asText();
        pageLimit = data.get("pageLimit").asInt();

        driver.get("https://www.flipkart.com");

        try {
            WebElement closePopup = driver.findElement(By.xpath("//button[contains(text(),'✕')]"));
            closePopup.click();
        } catch (Exception e) {
            System.out.println("Login popup did not appear.");
        }
    }

    @Test
    public void validateSortFunctionality() throws InterruptedException {
        driver.findElement(By.xpath("//input[contains(@title,'Search')]")).sendKeys(searchTerm);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(3000); // Wait for search results to load
        driver.findElement(By.xpath("//div[contains(text(),'Price -- Low to High')]")).click();
        Thread.sleep(3000); // Wait for sorting to apply
        List<Integer> allIntPrices = new ArrayList<>();
        for (int page = 1; page <= pageLimit; page++) {
            List<WebElement> priceElements = driver.findElements(By.xpath("//a[@rel='noopener noreferrer'][2]/div/div[1]"));
            priceElements.stream()
                    .map(priceElement -> priceElement.getText().replace("₹", "").replaceAll("[^0-9]", ""))
                    .filter(priceText -> !priceText.isEmpty())
                    .map(Integer::parseInt)
                    .forEach(allIntPrices::add);
            if (page < pageLimit) {
                try {
                    driver.findElement(By.xpath("//a[contains(@href,'page')]//span[text()='Next']")).click();
                    Thread.sleep(3000); // Wait for next page
                } catch (Exception e) {
                    System.out.println("No more pages available.");
                    break;
                }
            }
        }
        List<Integer> sortedPrices = new ArrayList<>(allIntPrices);
        Collections.sort(sortedPrices);
        Assert.assertEquals( sortedPrices, allIntPrices);
        System.out.println("Prices are sorted in ascending order.");
    }



    @Test
    public void validateAddToCartFunctionality() throws InterruptedException {

        driver.findElement(By.xpath("//input[contains(@title,'Search')]")).sendKeys(searchTerm);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(3000); // Wait for search results to load

        driver.findElement(By.xpath("//div[contains(text(),'Price -- Low to High')]")).click();
        Thread.sleep(3000); // Wait for sorting to apply
        System.out.println("Title: " + driver.getTitle());

        List<WebElement> productElements = driver.findElements(By.xpath("//a[@target='_blank']//img"));
        List<WebElement> priceElements = driver.findElements(By.xpath("//a[@rel='noopener noreferrer'][2]/div/div[1]"));
        for (int i = 0; i < 2; i++) {
            productElements.get(i).click(); // Click the second product
            Thread.sleep(3000); // Wait for product page to load

            Set<String> windowHandles = driver.getWindowHandles();
            Iterator<String> iterator = windowHandles.iterator();
            String parentWindow = iterator.next();
            String childWindow = iterator.next();

            driver.switchTo().window(childWindow);
            Thread.sleep(3000); // Allow time for the new tab to load
            WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(text(),'cart')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton); // Scroll to the button
            addToCartButton.click(); // Click Add to Cart
            Thread.sleep(3000);
            driver.close(); // Close the new tab
            driver.switchTo().window(parentWindow); // Switch back to the original tab
            System.out.println("Switched back to the original tab.");
            Thread.sleep(3000);
        }
        driver.navigate().refresh();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[contains(text(), 'Cart')]")).click();
        Thread.sleep(3000);
        driver.navigate().refresh();
        Thread.sleep(3000);
        List<WebElement> productNames = driver.findElements(By.xpath("//span[contains(text(),'Price details')]//parent::div//*[contains(text(),'₹')]"));
        ArrayList<String> prices = new ArrayList<>();
        for (WebElement product : productNames) {
            String priceText = product.getText().replace("₹", "").replace(",", "").trim();
            prices.add(priceText);
        }
        System.out.println("Prices in the cart: " + prices);

        List<Integer> intList = new ArrayList<>();
        for (String s : prices) {
            try {
                int value = Integer.parseInt(s.replace("−", " ").trim());
                intList.add(value);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + s);
            }
        }

        if (intList.size() < 4) {
            System.out.println("Not enough values for calculation.");
            return;
        }

        int calculatedTotal = intList.get(0) - intList.get(1) + intList.get(2); // 124 + (-18) + 80
        System.out.println("Calculated total: " + calculatedTotal);
        int expectedTotal = intList.get(3); // 186
        Assert.assertEquals(calculatedTotal, expectedTotal,
                "Validation failed! Calculated total: " + calculatedTotal + ", Expected total: " + expectedTotal);
        System.out.println("Validation successful! The total is correct: " + calculatedTotal);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
