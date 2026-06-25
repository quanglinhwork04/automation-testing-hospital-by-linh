package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestDatLich09_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich09", "Kiểm tra danh sách bác sĩ sau khi chọn chuyên khoa");

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
            test.log(Status.INFO, "Đã chọn chuyên khoa");

            datLichPage.takeScreenshot("DATLICH09.png");
            test.log(Status.PASS, "✅ Đã chụp danh sách bác sĩ");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}