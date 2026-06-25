package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestDatLich06_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich06", 
                "Truy cập đặt lịch khi chưa đăng nhập");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            HomePage homePage = new HomePage(driver);
            homePage.open();

            DatLichPage datLichPage = new DatLichPage(driver);
            datLichPage.clickDatLichButton();

            test.log(Status.INFO, "Đã nhấn nút ĐẶT LỊCH mà chưa đăng nhập");
            datLichPage.takeScreenshot("DATLICH06.png");

            // Có thể kiểm tra redirect về login hoặc thông báo
            test.log(Status.PASS, "✅ Đã chụp màn hình trường hợp chưa đăng nhập");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}