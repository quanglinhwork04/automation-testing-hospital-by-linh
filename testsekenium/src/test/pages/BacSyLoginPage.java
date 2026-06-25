package test.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class BacSyLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By username = By.id("username-doctor");
    private By password = By.id("password-doctor");
    private By loginBtn = By.id("btnlogin-doctor");

    public BacSyLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).clear();
        driver.findElement(username).sendKeys(user);
        
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
        
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }
}