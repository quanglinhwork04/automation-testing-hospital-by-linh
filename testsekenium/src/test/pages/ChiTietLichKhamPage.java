package test.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class ChiTietLichKhamPage extends BasePage {

    private By btnGuiToaThuoc = By.cssSelector("button[data-target='#toaThuocModal']");
    private By btnHuyLich = By.id("btn-huy");
    private By btnXacNhanDaKham = By.id("btn-dakham");
    private By btnXacNhan = By.id("btn-xacnhan");

    private By chanDoan = By.id("chanDoan");
    private By thuocSelect = By.cssSelector(".thuoc-select");
    private By soLuong = By.cssSelector("input[name='soLuong']");
    private By hdsd = By.cssSelector("textarea[name='hdsd']");
    private By btnGuiToa = By.xpath("//button[contains(text(), 'Gửi Toa Thuốc')]");

    private By lyDoHuy = By.id("lyDoHuy");
    private By confirmCancelBtn = By.id("confirmCancelButton");

    public ChiTietLichKhamPage(WebDriver driver) {
        super(driver);
    }

    public void clickGuiToaThuoc() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnGuiToaThuoc));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        btn.click();
    }

    public void nhapToaThuoc(String chanDoanText, int thuocIndex, String soLuongText, String huongDan) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(chanDoan)).sendKeys(chanDoanText);
        
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(thuocSelect)))
                .selectByIndex(thuocIndex);
        
        driver.findElement(soLuong).sendKeys(soLuongText);
        driver.findElement(hdsd).sendKeys(huongDan);
        
        WebElement guiBtn = wait.until(ExpectedConditions.elementToBeClickable(btnGuiToa));
        guiBtn.click();
    }

    public void clickHuyLich(String lyDo) {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnHuyLich));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        btn.click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(lyDoHuy)).sendKeys(lyDo);
        wait.until(ExpectedConditions.elementToBeClickable(confirmCancelBtn)).click();
    }

    public void clickXacNhanDaKham() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnXacNhanDaKham));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        btn.click();
    }

    public void clickXacNhan() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnXacNhan));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        btn.click();
    }
}