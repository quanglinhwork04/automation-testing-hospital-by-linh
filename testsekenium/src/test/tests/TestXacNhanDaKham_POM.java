package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestXacNhanDaKham_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestXacNhanDaKham", "Xác nhận đã khám");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            driver.get("http://localhost:8084/bacsy/login/");
            BacSyLoginPage loginPage = new BacSyLoginPage(driver);
            loginPage.login("doctor01", "123");

            DanhSachLichKhamPage dsPage = new DanhSachLichKhamPage(driver);
            dsPage.openDanhSachLichKham();
            dsPage.clickXemLich(14);

            ChiTietLichKhamPage chiTietPage = new ChiTietLichKhamPage(driver);
            chiTietPage.clickXacNhanDaKham();

            chiTietPage.takeScreenshot("XAC_NHAN_DA_KHAM.png");
            test.log(Status.PASS, "✅ Xác nhận đã khám thành công");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}