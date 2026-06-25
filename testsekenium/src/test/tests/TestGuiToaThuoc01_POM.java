package test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import test.pages.*;
import test.utils.*;

public class TestGuiToaThuoc01_POM {
    public static void main(String[] args) {
        ExtentReportManager.initReports();
        ExtentTest test = ExtentReportManager.createTest("TestGuiToaThuoc01", "Gửi toa thuốc thành công");

        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();

            // Đăng nhập bác sĩ
            driver.get("http://localhost:8084/bacsy/login/");
            BacSyLoginPage loginPage = new BacSyLoginPage(driver);
            loginPage.login("doctor01", "123");

            // Vào danh sách lịch khám
            DanhSachLichKhamPage dsLichPage = new DanhSachLichKhamPage(driver);
            dsLichPage.openDanhSachLichKham();
            dsLichPage.clickXemLich(33);   // hàng thứ 33

            // Gửi toa thuốc
            ChiTietLichKhamPage chiTietPage = new ChiTietLichKhamPage(driver);
            chiTietPage.clickGuiToaThuoc();
            chiTietPage.nhapToaThuoc("Viêm họng cấp", 1, "10", "Uống 1 viên/ngày");

            chiTietPage.takeScreenshot("GuiToaThuoc_01_Success");
            test.log(Status.PASS, "✅ Gửi toa thuốc thành công");

        } catch (Exception e) {
            test.log(Status.FAIL, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ExtentReportManager.flushReports();
        }
    }
}