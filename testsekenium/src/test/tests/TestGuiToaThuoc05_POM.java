package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestGuiToaThuoc05_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestGuiToaThuoc05", "Gửi toa thuốc - Hướng dẫn rất dài");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            driver.get("http://localhost:8084/bacsy/login/");
            BacSyLoginPage loginPage = new BacSyLoginPage(driver);
            loginPage.login("doctor01", "123");

            DanhSachLichKhamPage dsPage = new DanhSachLichKhamPage(driver);
            dsPage.openDanhSachLichKham();
            dsPage.clickXemLich(10);

            ChiTietLichKhamPage chiTietPage = new ChiTietLichKhamPage(driver);
            chiTietPage.clickGuiToaThuoc();
            chiTietPage.nhapToaThuoc("Viêm họng cấp", 1, "10", 
                "Uống 1 viên/ngàyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");

            chiTietPage.takeScreenshot("GuiToaThuoc_05_Success");
            test.log(Status.PASS, "✅ Test hướng dẫn dài");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}