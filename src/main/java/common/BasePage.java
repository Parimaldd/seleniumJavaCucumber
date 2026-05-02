package common;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
//import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.logging.Logger;

public class BasePage {
    protected static Logger log =Logger.getLogger(BasePage.class.getName());
    protected WebDriver driver ;
//    protected HandlerUtil lastBrowserhandler;
    protected static Duration waitTime = Duration.ofSeconds(7);
    protected String StringparentWinHandle;
    WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver=driver;
        wait= new WebDriverWait(driver,waitTime);
    }

    public void sendKeys(By by, String text){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).sendKeys(text);
    }

    public String getElementText(By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by).getText();
    }

    public String getElementValue(By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by).getAttribute("Value");
    }

    public void click(By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    public void assertElement(ExtentTest test , By by, String infoText){
        try {
            WebElement element = driver.findElement(by);

            if (element.isDisplayed()) {
                test.log(Status.PASS, infoText + " - Element is displayed: " + by.toString());
            } else {
                test.log(Status.FAIL, infoText + " - Element is NOT displayed: " + by.toString());
                attachScreenshot(test);
            }

        } catch (Exception e) {
            test.log(Status.FAIL, infoText + " - Exception occurred while locating element: " + e.getMessage());
            attachScreenshot(test);
        }
    }

    private void attachScreenshot(ExtentTest test) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir") + "/reports/screenshots/" + System.currentTimeMillis() + ".png";

            File destFile = new File(path);
            destFile.getParentFile().mkdirs(); // create folder if not exist
            Files.copy(screenshot.toPath(), destFile.toPath());

            test.addScreenCaptureFromPath(path);
        } catch (IOException io) {
            test.log(Status.WARNING, "Failed to attach screenshot: " + io.getMessage());
        }
    }
    

}
