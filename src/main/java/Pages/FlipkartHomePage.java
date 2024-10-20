package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Utilities;

public class FlipkartHomePage {

    private WebDriver driver;

    public FlipkartHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String searchTerm) {
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title,'Search')]"));
        searchBox.sendKeys(searchTerm);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public void applySortOption() {
        WebElement sortBtn = driver.findElement(By.xpath("//div[contains(text(),'Price -- Low to High')]"));
        Utilities.waitForElementToBeClickable(driver, sortBtn, 10);
        sortBtn.click();
    }
}
