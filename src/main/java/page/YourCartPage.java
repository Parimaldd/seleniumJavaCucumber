package page;

import com.aventstack.extentreports.ExtentTest;
import common.BasePage2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class YourCartPage extends BasePage2 {


    public YourCartPage(WebDriver driver, ExtentTest test) {
        super(driver);
        this.test = test;
    }

    public String getAddedProductText(String product) {
        String cartProduct = "//*[text()='"+product+"']";
        return getText(By.xpath(cartProduct),"Retrieved name of added cart Product");
    }
}
