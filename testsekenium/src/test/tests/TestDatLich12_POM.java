package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestDatLich12_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich12", "Đặt lịch thiếu ngày khám");

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
            datLichPage.chonKhungGio(1);
            datLichPage.nhapTrieuChung("Đau bụng nhẹ");

            datLichPage.clickDatNgay();

            String result = datLichPage.getResultMessage();
            test.log(Status.INFO, "Thông báo: " + result);
            datLichPage.takeScreenshot("DATLICH12.png");

            test.log(Status.PASS, "✅ Đã test thiếu ngày khám");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}