package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestDatLich02_POM {

    public static void main(String[] args) {

        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich02", "Đặt lịch không chọn bác sĩ");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            HomePage home = new HomePage(driver);
            home.open();

            LoginPage login = home.clickDangNhap();
            login.login("patient06", "123");

            DatLichPage datLich = new DatLichPage(driver);
            datLich.clickDatLichButton();

            datLich.chonChuyenKhoa(1);
            datLich.nhapNgayKham("24/07/2025");
            datLich.chonKhungGio(1);
            datLich.nhapTrieuChung("Đau bụng nhẹ, sốt nhẹ");

            datLich.clickDatNgay();

            String result = datLich.getResultMessage();
            test.log(Status.INFO, "Thông báo: " + result);

            if (result.contains("thành công")) {
                test.log(Status.PASS, "✅ Test Passed");
            } else {
                test.log(Status.FAIL, "❌ Test Failed");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}