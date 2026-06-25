package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestTimKiemBacSy06_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestTimKiemBacSy06", "Tìm kiếm với ký tự đặc biệt");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            HomePage homePage = new HomePage(driver);
            homePage.open();

            DanhSachBacSyPage dsPage = homePage.clickTimBacSy();
            dsPage.search("Nam<");

            dsPage.takeScreenshot("timkiem_bacsy_SEARCH06.png");
            test.log(Status.PASS, "✅ Test input có ký tự đặc biệt");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}