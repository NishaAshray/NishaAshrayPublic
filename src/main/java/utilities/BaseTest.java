package utilities;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected Properties config = new Properties();

    @BeforeClass
    public void setup() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Load properties from config file
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        config.load(fis);
        driver.get(config.getProperty("url"));

        // Close the login popup if it appears
        try {
            Utilities.closeLoginPopup(driver);
        } catch (Exception e) {
            System.out.println("Login popup did not appear.");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
