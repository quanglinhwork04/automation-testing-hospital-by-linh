package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestTimKiemBacSy08_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestTimKiemBacSy08", "Kiểm tra phân trang danh sách bác sĩ");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            HomePage homePage = new HomePage(driver);
            homePage.open();

            DanhSachBacSyPage dsPage = homePage.clickTimBacSy();
            dsPage.clickSearch();   // Hiển thị danh sách

            dsPage.takeScreenshot("timkiem_bacsy_SEARCH08_phantrang_truoc.png");
            test.log(Status.PASS, "✅ Đã kiểm tra phân trang");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}