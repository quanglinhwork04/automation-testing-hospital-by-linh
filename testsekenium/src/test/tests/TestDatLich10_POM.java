package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestDatLich10_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich10", "Chụp màn hình sau khi chọn bác sĩ");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            HomePage homePage = new HomePage(driver);
            homePage.open();

            LoginPage loginPage = homePage.clickDangNhap();
            loginPage.login("patient06", "123");

            DatLichPage datLichPage = new DatLichPage(driver);
            datLichPage.clickDatLichButton();

            datLichPage.chonChuyenKhoa(1);
            datLichPage.chonBacSy(1);

            datLichPage.takeScreenshot("DATLICH10.png");
            test.log(Status.PASS, "✅ Đã chụp màn hình sau khi chọn bác sĩ");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}