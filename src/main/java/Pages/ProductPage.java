package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductPage {

    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getAddToCartButton() {
        return driver.findElement(By.xpath("//button[contains(text(),'cart')]"));
    }

    public WebElement cartIcon() {
        return driver.findElement(By.xpath("//*[contains(text(), 'Cart')]"));
    }

    public List<Integer> getProductName() {
        List<WebElement> productNames = driver.findElements(By.xpath("//span[contains(text(),'Price details')]//parent::div//*[contains(text(),'₹')]"));
        List<Integer> intList = new ArrayList<>();

        // Extract prices and convert to integers
        for (WebElement product : productNames) {
            String priceText = product.getText().replace("₹", "").replace(",", "").trim();
            priceText = priceText.replace("−", ""); // Removing the negative sign
            try {
                int value = Integer.parseInt(priceText.trim()); // No need for replace if removing the sign
                intList.add(value);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + priceText);
            }
        }
        return intList;
    }
}
