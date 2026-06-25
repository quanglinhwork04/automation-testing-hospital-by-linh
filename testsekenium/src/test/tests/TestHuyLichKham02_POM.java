package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestHuyLichKham02_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestHuyLichKham02", "Hủy lịch khám - Không nhập lý do");

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
            chiTietPage.clickHuyLich("");  // Không nhập lý do

            chiTietPage.takeScreenshot("HUY_LICH_KHAM_02.png");
            test.log(Status.PASS, "✅ Test hủy không nhập lý do");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}