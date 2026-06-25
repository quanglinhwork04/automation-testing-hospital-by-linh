package test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By btnTimBacSy = By.cssSelector("li#btn-timkiem > a[href='/danh-sach-bac-sy']");
    private By btnDangNhap = By.linkText("Đăng nhập");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get("http://localhost:8084/");
    }

    public DanhSachBacSyPage clickTimBacSy() {
        wait.until(ExpectedConditions.elementToBeClickable(btnTimBacSy)).click();
        return new DanhSachBacSyPage(driver);
    }

    public LoginPage clickDangNhap() {
        wait.until(ExpectedConditions.elementToBeClickable(btnDangNhap)).click();
        return new LoginPage(driver);
    }
}