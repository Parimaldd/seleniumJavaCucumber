package page;

import com.aventstack.extentreports.ExtentTest;
import common.BasePage2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage2 {

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver, ExtentTest test) {
        super(driver);
        this.test = test;
    }

    public void login(String userID, String userPassword) {
        sendKeys(username, userID,"Entered UserName as "+userID);
        sendKeys(password, userPassword,"Entered Password as "+userPassword);
        click(loginBtn,"Clicked on Login Button");
    }

}