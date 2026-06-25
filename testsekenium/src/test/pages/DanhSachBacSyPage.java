package test.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class DanhSachBacSyPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By searchInput = By.id("searchInput");
    private By submitBtn = By.cssSelector("form#searchForm input[type='submit'], form#searchForm button[type='submit']");
    private By chuyenKhoaSelect = By.id("chuyenKhoaId");

    public DanhSachBacSyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Tìm kiếm theo từ khóa
    public void search(String keyword) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(keyword);
        clickSearch();
    }

    // Chọn chuyên khoa
    public void chonChuyenKhoa(int index) {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(chuyenKhoaSelect));
        new Select(selectElement).selectByIndex(index);
    }

    // Click nút tìm kiếm riêng biệt
    public void clickSearch() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        btn.click();
    }

    // Chụp ảnh màn hình (đã implement đầy đủ)
    public void takeScreenshot(String filename) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File folder = new File("D:\\screenshots\\");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            if (!filename.endsWith(".png")) {
                filename += ".png";
            }
            File destination = new File(folder, filename);
            FileHandler.copy(screenshot, destination);
            System.out.println("📸 Screenshot saved: " + destination.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("❌ Không thể chụp ảnh: " + e.getMessage());
        }
    }
}