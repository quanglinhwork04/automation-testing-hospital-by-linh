package test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void takeScreenshot(String filename) {
        // Bạn có thể implement hàm chụp ảnh chung ở đây sau
        System.out.println("Screenshot: " + filename);
    }
}