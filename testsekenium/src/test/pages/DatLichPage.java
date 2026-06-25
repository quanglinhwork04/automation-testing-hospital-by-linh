package test.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class DatLichPage extends BasePage {

    private By btnDatLich = By.id("btn-datlich");
    private By selectChuyenKhoa = By.name("chuyenKhoaId");
    private By selectBacSy = By.name("bacSyId");
    private By inputNgayKham = By.name("ngayKham");
    private By selectKhungGio = By.name("khungGioKham");
    private By inputTrieuChung = By.name("moTaTrieuChung");
    private By btnSubmit = By.id("btn_submitdatlich");
    private By resultMessage = By.id("result");        // hoặc #message.alert-danger
    private By errorMessage = By.cssSelector("#message.alert-danger");

    public DatLichPage(WebDriver driver) {
        super(driver);
    }

    public void clickDatLichButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btnDatLich)).click();
    }

    public void chonChuyenKhoa(int index) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(selectChuyenKhoa)))
                .selectByIndex(index);
    }

    public void chonBacSy(int index) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(selectBacSy)))
                .selectByIndex(index);
    }

    public void nhapNgayKham(String ngay) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputNgayKham)).sendKeys(ngay);
    }

    public void chonKhungGio(int index) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(selectKhungGio)))
                .selectByIndex(index);
    }

    public void nhapTrieuChung(String trieuChung) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputTrieuChung)).sendKeys(trieuChung);
    }

    public void clickDatNgay() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public String getResultMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(resultMessage)).getText();
        } catch (Exception e) {
            try {
                return driver.findElement(errorMessage).getText();
            } catch (Exception ex) {
                return "Không tìm thấy thông báo";
            }
        }
    }
}