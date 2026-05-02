package stepDefinition;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ConfigReader;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseTest {

    public static WebDriver driver;
    public WebDriverWait wait;
    public static ExtentReports extent;
    public static ExtentTest test;

    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("url"));
    }

    public void teardown() {
        try {
            assertAll();
        } catch (AssertionError e) {
            if (test != null) {
                test.fail("Soft Assert Failures:\n" + e.getMessage());
            }
            throw e;
        } finally {
            driver.quit();
            extent.flush();
        }
    }

    public void initReport() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String reportPath = "target/ExtentReport_" + timeStamp + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public void createTest(String scenarioName) {
        test = extent.createTest(scenarioName);
    }

    public void flushReport() {
        extent.flush();
    }

    public static List<String> softFailures = new ArrayList<>();

    public void assertAll() {
        if (!softFailures.isEmpty()) {
            throw new AssertionError("Soft Assert Failures:\n" + String.join("\n", softFailures));
        }
    }

}
