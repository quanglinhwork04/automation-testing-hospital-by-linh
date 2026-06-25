package test.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReports() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Báo cáo Kiểm thử Tự động");
        spark.config().setReportName("Selenium POM Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static ExtentTest createTest(String testName, String description) {
        test = extent.createTest(testName, description);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}