package common;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.markuputils.ExtentColor;

import stepDefinition.BaseTest;

public class BasePage2 {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    protected ExtentTest test;

    private final Duration WAIT_TIME = Duration.ofSeconds(10);
    protected static final Logger log = LogManager.getLogger(BasePage2.class);

    public BasePage2(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIME);
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    public void setTest(ExtentTest test) {
        this.test = test;
    }

    // ================= COMMON LOG =================

    private void logStep(String message) {
        log.info(message);
        if (test != null) {
            test.info(message);
        }
    }

    private void handleFailure(String actionPerformed, Exception e) {

        String errorMsg = actionPerformed +
                " | FAILED DUE TO: " + e.getClass().getSimpleName() +
                " - " + e.getMessage();

        log.error(errorMsg, e);

        if (test != null) {

            test.fail(
                    MarkupHelper.createLabel(actionPerformed, ExtentColor.RED)
            );

            test.fail(errorMsg);
        }

        attachScreenshot(actionPerformed);
    }
    // ================= WAIT =================

    private WebElement waitForElement(By locator, WaitType type) {
        switch (type) {
            case CLICKABLE:
                return wait.until(ExpectedConditions.elementToBeClickable(locator));
            case VISIBLE:
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            case PRESENT:
                return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            default:
                throw new IllegalArgumentException("Invalid wait type");
        }
    }

    public enum WaitType {
        CLICKABLE, VISIBLE, PRESENT
    }

    // ================= ACTIONS =================

    public void click(By locator, String actionPerformed) {
        try {
            waitForElement(locator, WaitType.CLICKABLE).click();
            logStep(actionPerformed);
        } catch (Exception e) {
            handleFailure(actionPerformed, e);
            throw e;
        }
    }

    public void doubleClick(By locator, String actionPerformed) {
        try {
            WebElement element = waitForElement(locator, WaitType.VISIBLE);
            actions.doubleClick(element).perform();
            logStep(actionPerformed);
        } catch (Exception e) {
            handleFailure(actionPerformed, e);
            throw e;
        }
    }

    public void sendKeys(By locator, String text, String actionPerformed) {
        try {
            WebElement element = waitForElement(locator, WaitType.VISIBLE);
            element.clear();
            element.sendKeys(text);
            logStep(actionPerformed);
        } catch (Exception e) {
            handleFailure(actionPerformed, e);
            throw e;
        }
    }

    public String getText(By locator, String actionPerformed) {
        try {
            String text = waitForElement(locator, WaitType.VISIBLE).getText();
            logStep(actionPerformed + " | value: " + text);
            return text;
        } catch (Exception e) {
            handleFailure(actionPerformed, e);
            throw e;
        }
    }

    public boolean isDisplayed(By locator, String actionPerformed) {
        try {
            boolean status = waitForElement(locator, WaitType.VISIBLE).isDisplayed();
            logStep(actionPerformed + " | status: " + status);
            return status;
        } catch (Exception e) {
            handleFailure(actionPerformed, e);
            return false;
        }
    }

    // ================= ADVANCED =================

    public void jsClick(By locator, String actionPerformed) {
        try {
            WebElement element = waitForElement(locator, WaitType.VISIBLE);
            js.executeScript("arguments[0].click();", element);
            logStep(actionPerformed);
        } catch (Exception e) {
            handleFailure(actionPerformed, e);
            throw e;
        }
    }

    public void hover(By locator, String actionPerformed) {
        try {
            actions.moveToElement(waitForElement(locator, WaitType.VISIBLE)).perform();
            logStep(actionPerformed);
        } catch (Exception e) {
            handleFailure(actionPerformed, e);
            throw e;
        }
    }

    // ================= RETRY =================

    public void clickWithRetry(By locator, String actionPerformed) {
        int attempts = 0;

        while (attempts < 3) {
            try {
                click(locator, actionPerformed + " (retry " + (attempts + 1) + ")");
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
                log.warn("Retry {} for element {}", attempts, locator);
            }
        }

        handleFailure(actionPerformed + " failed after retries", new Exception("Retry Failed"));
        throw new RuntimeException("Failed after retries: " + locator);
    }

    // ================= SCREENSHOT =================

    public void attachScreenshot(String actionPerformed) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir") + "/target/screenshots/" + System.currentTimeMillis() + ".png";

            File dest = new File(path);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());

            if (test != null) {
                test.addScreenCaptureFromPath(path);
            }

            log.info(actionPerformed + " | Screenshot attached");

        } catch (IOException e) {
            log.error("Screenshot failed", e);
        }
    }

    // ================= ASSERT =================

    public void assertEqualsHard(Object actual, Object expected, String actionPerformed) {

        if (Objects.equals(actual, expected)) {
            logStep("PASS: " + actionPerformed);
            if (test != null) {
                test.pass(actionPerformed);
            }
        } else {
            handleFailure(actionPerformed, new Exception("Assertion Failed"));
            throw new AssertionError(actionPerformed);
        }
    }

    // ================= WAIT =================

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public enum AssertType {
        EQUALS,
        CONTAINS,
        NOT_EQUALS,
        NOT_CONTAINS,
        STARTS_WITH
    }


    public void softAssert(String actual, String expected, String message, AssertType type) {
        boolean result = false;
        actual = actual.trim();
        expected = expected.trim();
        switch (type) {
            case EQUALS:
                result = actual.equals(expected);
                break;
            case CONTAINS:
                result = actual.contains(expected);
                break;
            case NOT_EQUALS:
                result = !actual.equals(expected);
                break;
            case NOT_CONTAINS:
                result = !actual.contains(expected);
                break;
            case STARTS_WITH:
                result = actual.startsWith(expected);
                break;
        }
        if (result) {
            log.info("PASS: {} | Expected: {} | Actual: {}", message, expected, actual);
            if (test != null) {
                test.pass(MarkupHelper.createLabel(
                        "PASS: " + message + " | Expected: " + expected + " | Actual: " + actual,
                        ExtentColor.GREEN
                ));
            }
        } else {
            String errorMsg = "FAIL: " + message +
                    " | Condition: " + type +
                    " | Expected: " + expected +
                    " | Actual: " + actual;

            log.error(errorMsg);
            if (test != null) {
                test.fail(MarkupHelper.createLabel(errorMsg, ExtentColor.RED));
                test.info("Assertion failed at this step");
                attachScreenshot("Soft Assertion Failure");
            }
            BaseTest.softFailures.add(errorMsg);
        }
    }
}