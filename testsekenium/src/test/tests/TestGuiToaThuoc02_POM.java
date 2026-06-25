package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestGuiToaThuoc02_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestGuiToaThuoc02", "Gửi toa thuốc - Test case 02");

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
            chiTietPage.clickGuiToaThuoc();
            chiTietPage.nhapToaThuoc("Viêm họng cấp", 1, "10", "Uống 1 viên/ngày");

            chiTietPage.takeScreenshot("GuiToaThuoc_02_Success");
            test.log(Status.PASS, "✅ Test case 02 thành công");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}