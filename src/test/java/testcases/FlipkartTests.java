package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.FlipkartHomePage;
import Pages.ProductPage;
import utilities.BaseTest;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlipkartTests extends BaseTest {

    @Test
    public void validateSortFunctionality() throws InterruptedException {
        FlipkartHomePage homePage = new FlipkartHomePage(driver);
        homePage.searchProduct(config.getProperty("searchTerm"));
        Utilities.waitForPageLoad(driver);

        homePage.applySortOption();
        Thread.sleep(3000);
        Utilities.waitForPageLoad(driver);

        List<Integer> allIntPrices = new ArrayList<>();
        int pageLimit = Integer.parseInt(config.getProperty("pageLimit"));
        Thread.sleep(3000);

        for (int page = 1; page <= pageLimit; page++) {
            List<WebElement> priceElements = driver.findElements
                    (By.xpath("//a[@rel='noopener noreferrer'][2]/div/div[1]"));
            Thread.sleep(3000);

            for (WebElement priceElement : priceElements) {
                String priceText = priceElement.getText().replace("₹", "").
                        replaceAll("[^0-9]", "");
                if (!priceText.isEmpty()) {
                    allIntPrices.add(Integer.parseInt(priceText));
                }
            }
            System.out.println("Prices on page " + page + ": " + allIntPrices);

            if (page < pageLimit) {
                try {
                    WebElement nextButton = driver.findElement
                            (By.xpath("//a[contains(@href,'page')]//span[text()='Next']"));
                    nextButton.click();
                    Thread.sleep(3000);
                    driver.navigate().refresh();
                    Thread.sleep(3000);
                    Utilities.waitForPageLoad(driver);
                } catch (Exception e) {
                    System.out.println("No more pages available.");
                    break;
                }
            }
        }
        System.out.println("All prices: " + allIntPrices);
        List<Integer> sortedPrices = new ArrayList<>(allIntPrices);
        Collections.sort(sortedPrices);
        Assert.assertEquals(sortedPrices, allIntPrices, "Prices are not sorted correctly.");
    }

    @Test
    public void validateAddToCartFunctionality() throws InterruptedException {
        FlipkartHomePage homePage = new FlipkartHomePage(driver);
        homePage.searchProduct(config.getProperty("searchTerm"));
        Utilities.waitForPageLoad(driver);

        homePage.applySortOption();
        Thread.sleep(3000);
        Utilities.waitForPageLoad(driver);

        ProductPage productPage = new ProductPage(driver);

        List<WebElement> productElements = driver.findElements(By.xpath("//a[@target='_blank']//img"));
        for (int i = 0; i < 2; i++) {
            Utilities.waitForElementLoad(driver, productElements.get(i));
            productElements.get(i).click(); // Click the second product
            Utilities.switchToNewWindow(driver);
            Utilities.waitForPageLoad(driver);

            Thread.sleep(2000);
            productPage.getAddToCartButton().click();

            Thread.sleep(2000);
            driver.close();
            Utilities.switchToParentWindow(driver);
            Thread.sleep(3000);
        }
        Thread.sleep(3000);
        driver.navigate().refresh();
        Thread.sleep(3000);

        Utilities.waitForElementToBeClickable(driver, productPage.cartIcon(), 10);
        productPage.cartIcon().click();

        Utilities.waitForPageLoad(driver);
        Thread.sleep(3000);

        driver.navigate().refresh();
        Thread.sleep(3000);

        List<Integer> intList = productPage.getProductName();
        System.out.println("Prices as integers: " + intList);
        int calculatedTotal = intList.get(0) - intList.get(1) + intList.get(2); // 124 + (-18) + 80
        System.out.println("Calculated total: " + calculatedTotal);
        int expectedTotal = intList.get(3); // 186
        Assert.assertEquals(calculatedTotal, expectedTotal,
                "Validation failed! Calculated total: " + calculatedTotal + ", Expected total: " + expectedTotal);
        System.out.println("Validation successful! The total is correct: " + calculatedTotal);
    }
}
