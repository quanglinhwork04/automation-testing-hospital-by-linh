# Hệ Thống Đặt Lịch Khám Bệnh — Automation Testing

## Giới thiệu

Đây là framework kiểm thử tự động được xây dựng bằng Selenium WebDriver và Java, áp dụng mô hình Page Object Model (POM). Dự án bao phủ các kịch bản kiểm thử end-to-end cho hệ thống đặt lịch khám bệnh, tập trung vào hai phía: bệnh nhân và bác sĩ.

Mục tiêu là phát hiện lỗi sớm và xác minh các luồng nghiệp vụ chính — từ đặt lịch khám đến quản lý toa thuốc — thông qua các bộ test có thể tái sử dụng và dễ bảo trì.

---

## Công nghệ sử dụng

- Java (JDK 17)
- Selenium WebDriver
- Page Object Model (POM)
- ExtentReports
- Chrome / ChromeDriver
- Eclipse IDE

---

## Phạm vi kiểm thử

**Phía bệnh nhân**
- Đăng nhập
- Tìm kiếm bác sĩ theo tên, chuyên khoa và phân trang
- Đặt lịch khám — 14 test cases bao gồm cả luồng thành công và các trường hợp ngoại lệ
- Kiểm thử bảo mật cơ bản — XSS và SQL Injection

**Phía bác sĩ**
- Đăng nhập
- Quản lý lịch khám
- Gửi toa thuốc — nhiều kịch bản khác nhau
- Hủy lịch khám
- Xác nhận đã khám và xác nhận lịch hẹn sắp tới

---

## Cấu trúc dự án

```
src/test/java/
├── test/pages/      # Page Objects
├── test/tests/      # Test Cases
└── test/utils/      # DriverFactory, cấu hình ExtentReport
```

---

## Hướng dẫn chạy test

1. Cấu hình đường dẫn ChromeDriver trong `DriverFactory.java`
2. Khởi động backend tại `http://localhost:8084`
3. Trong Eclipse, click chuột phải vào class bất kỳ trong `test.tests` và chọn Run As → Java Application

---

## Thống kê test cases

| Phần | Số test cases |
|---|---|
| Tìm kiếm bác sĩ | 14 |
| Đặt lịch khám | 14 |
| Quản lý bác sĩ | 12 |
| Tổng | 40+ |

---

## Kỹ năng thể hiện

- Selenium WebDriver với Java — xử lý waits, JavaScript Executor, dropdown, alert
- Thiết kế framework theo mô hình Page Object Model
- Viết test cases cho cả luồng happy path lẫn negative và boundary scenarios
- Kiểm thử bảo mật cơ bản với XSS và SQL Injection
- Tạo báo cáo chuyên nghiệp bằng ExtentReports

---

## Hướng phát triển tiếp theo

- Tích hợp TestNG và data-driven testing
- Hỗ trợ cross-browser testing
- Xây dựng CI/CD pipeline với Jenkins
- Tích hợp Allure Report

---

**Tác giả:** Nguyễn Quang Linh 
**Vai trò:** Automation Tester
