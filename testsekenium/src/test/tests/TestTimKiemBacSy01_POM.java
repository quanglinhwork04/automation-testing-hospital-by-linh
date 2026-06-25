package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestTimKiemBacSy01_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestTimKiemBacSy01", "Tìm kiếm bác sĩ không nhập từ khóa");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            HomePage homePage = new HomePage(driver);
            homePage.open();

            DanhSachBacSyPage dsPage = homePage.clickTimBacSy();
            dsPage.clickSearch();

            dsPage.takeScreenshot("timkiem_bacsy_SEARCH01.png");
            test.log(Status.PASS, "✅ Tìm kiếm không từ khóa thành công");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}