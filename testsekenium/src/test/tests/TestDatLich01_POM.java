package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

import java.io.IOException;

public class TestDatLich01_POM {

    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich01", "Kiểm tra chức năng đặt lịch khám");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();
            test.log(Status.INFO, "Mở trình duyệt thành công");

            HomePage homePage = new HomePage(driver);
            homePage.open();
            test.log(Status.INFO, "Truy cập trang chủ");

            LoginPage loginPage = homePage.clickDangNhap();
            loginPage.login("patient06", "123");
            test.log(Status.INFO, "Đăng nhập thành công");

            DatLichPage datLichPage = new DatLichPage(driver);
            datLichPage.clickDatLichButton();
            test.log(Status.INFO, "Nhấn nút ĐẶT LỊCH");

            datLichPage.chonChuyenKhoa(1);
            datLichPage.chonBacSy(1);
            datLichPage.nhapNgayKham("10/10/2026");
            datLichPage.chonKhungGio(4);
            datLichPage.nhapTrieuChung("Đau bụng nhẹ, sốt nhẹ");

            datLichPage.clickDatNgay();
            test.log(Status.INFO, "Đã submit form đặt lịch");

            String result = datLichPage.getResultMessage();
            test.log(Status.INFO, "Thông báo: " + result);

            if (result.contains("thành công") || result.contains("OK")) {
                test.log(Status.PASS, "ĐẶT LỊCH THÀNH CÔNG");
            } else {
                test.log(Status.FAIL, "Đặt lịch thất bại");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
            // DriverFactory.quitDriver();
        }
    }
}