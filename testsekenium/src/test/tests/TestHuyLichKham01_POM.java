package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestHuyLichKham01_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestHuyLichKham01", "Hủy lịch khám thành công");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            driver.get("http://localhost:8084/bacsy/login/");
            BacSyLoginPage loginPage = new BacSyLoginPage(driver);
            loginPage.login("doctor01", "123");

            DanhSachLichKhamPage dsPage = new DanhSachLichKhamPage(driver);
            dsPage.openDanhSachLichKham();
            dsPage.clickXemLich(31);

            ChiTietLichKhamPage chiTietPage = new ChiTietLichKhamPage(driver);
            chiTietPage.clickHuyLich("Hủy do lịch bác sĩ thay đổi");

            chiTietPage.takeScreenshot("HUY_LICH_KHAM_01.png");
            test.log(Status.PASS, "✅ Hủy lịch khám thành công");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}