package test.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.time.Duration;

public class DanhSachLichKhamPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By dataTable = By.id("dataTable");
    private By scheduleDropdown = By.id("scheduleDropdown");
    private By viewScheduleLink = By.cssSelector("a[href='/bacsy/danhSachLichKham']");

    public DanhSachLichKhamPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openDanhSachLichKham() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(scheduleDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        dropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(viewScheduleLink)).click();
    }

    public void clickXemLich(int rowIndex) {
        String selector = "#dataTable tbody tr:nth-child(" + rowIndex + ") td:last-child a.btn-success";
        WebElement viewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", viewBtn);
        viewBtn.click();
    }

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
            System.out.println("❌ Không chụp được ảnh: " + e.getMessage());
        }
    }
}