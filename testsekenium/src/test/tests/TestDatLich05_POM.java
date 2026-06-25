package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestDatLich05_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich05", 
                "Đặt lịch với ngày trong quá khứ");

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
            datLichPage.nhapNgayKham("24/05/2025");
            datLichPage.chonKhungGio(1);
            datLichPage.nhapTrieuChung("Đau bụng nhẹ, sốt nhẹ");

            datLichPage.clickDatNgay();

            String result = datLichPage.getResultMessage();
            test.log(Status.INFO, "Thông báo: " + result);

            datLichPage.takeScreenshot("DATLICH05.png");

            if (result.contains("alert-danger") || result.toLowerCase().contains("error")) {
                test.log(Status.PASS, "✅ Test Passed - Báo lỗi ngày không hợp lệ");
            } else {
                test.log(Status.FAIL, "❌ Không báo lỗi như mong đợi");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}