package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestDatLich03_POM {

    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestDatLich03", 
                "Đặt lịch không chọn bác sĩ - Kiểm tra thông báo lỗi");

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

            // Không chọn bác sĩ (chỉ chọn chuyên khoa)
            datLichPage.chonChuyenKhoa(1);
            datLichPage.nhapNgayKham("24/07/2025");
            datLichPage.chonKhungGio(1);
            datLichPage.nhapTrieuChung("Đau bụng nhẹ, sốt nhẹ");
            test.log(Status.INFO, "Đã nhập thông tin (không chọn bác sĩ)");

            datLichPage.clickDatNgay();
            test.log(Status.INFO, "Nhấn nút Đặt ngay");

            String result = datLichPage.getResultMessage();
            test.log(Status.INFO, "Thông báo nhận được: " + result);

            // Kiểm tra kết quả mong đợi là lỗi
            if (result.contains("bác sĩ") || result.contains("chưa chọn") || 
                result.toLowerCase().contains("error") || result.contains("alert-danger") ||
                !result.contains("thành công")) {
                
                test.log(Status.PASS, "✅ Test Passed - Hệ thống báo lỗi khi không chọn bác sĩ");
            } else {
                test.log(Status.FAIL, "❌ Test Failed - Không nhận được thông báo lỗi mong đợi");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi ngoại lệ: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
            // DriverFactory.quitDriver();   // Uncomment nếu muốn đóng browser sau test
        }
    }
}