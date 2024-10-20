package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class Utilities {

    public static void closeLoginPopup(WebDriver driver) {
        WebElement closePopup = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'✕')]")));
        closePopup.click();
    }

    public static void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public static void switchToNewWindow(WebDriver driver) {
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();
        String parentWindow = iterator.next();
        String childWindow = iterator.hasNext() ? iterator.next() : parentWindow;
        driver.switchTo().window(childWindow);
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void switchToParentWindow(WebDriver driver) {
        String parentWindow = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(parentWindow);
    }

    public boolean waitForTextToBePresentInElement(WebDriver driver, WebElement element, String text, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            System.out.println("Text '" + text + "' was not found in the element within " + timeout + " seconds.");
            return false;
        }
    }

    public static void waitForElementLoad(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOf(element)
        );
    }

    public static boolean waitForElementToBeClickable(WebDriver driver, WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            return wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
        } catch (Exception e) {
            System.out.println("Element was not clickable within " + timeout + " seconds.");
            return false;
        }
    }
}
