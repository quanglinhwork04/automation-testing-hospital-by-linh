package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestDatLich08_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich08", "Đặt lịch với triệu chứng quá ngắn (1 ký tự)");

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
            datLichPage.nhapNgayKham("29/07/2025");
            datLichPage.chonKhungGio(1);
            datLichPage.nhapTrieuChung("Đ");

            datLichPage.clickDatNgay();

            String result = datLichPage.getResultMessage();
            test.log(Status.INFO, "Thông báo: " + result);
            datLichPage.takeScreenshot("DATLICH08.png");

            test.log(Status.PASS, "✅ Đã test triệu chứng ngắn");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}