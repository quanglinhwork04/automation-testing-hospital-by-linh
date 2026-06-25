package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestTimKiemBacSy03_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestTimKiemBacSy03", "Tìm kiếm theo chuyên khoa");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            HomePage homePage = new HomePage(driver);
            homePage.open();

            DanhSachBacSyPage dsPage = homePage.clickTimBacSy();
            dsPage.chonChuyenKhoa(1);
            dsPage.clickSearch();

            dsPage.takeScreenshot("timkiem_bacsy_SEARCH03.png");
            test.log(Status.PASS, "✅ Tìm kiếm theo chuyên khoa");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}