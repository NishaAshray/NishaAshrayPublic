package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Helper;

public class FlipkartHomePage {

    private WebDriver driver;

    public FlipkartHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String searchTerm) throws InterruptedException {
        Thread.sleep(3000);
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title,'Search')]"));
        searchBox.sendKeys(searchTerm);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public void applySortOption() throws InterruptedException {
        Thread.sleep(3000);
        WebElement sortBtn = driver.findElement(By.xpath("//div[contains(text(),'Price -- Low to High')]"));
        Helper.waitForElementToBeClickable(driver, sortBtn, 10);
        sortBtn.click();
    }
}
