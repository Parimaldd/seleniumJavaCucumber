package page;

import com.aventstack.extentreports.ExtentTest;
import common.BasePage2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage2 {

    private By burgerButton = By.id("react-burger-menu-btn");
    private By logoutButton = By.id("logout_sidebar_link");
    private By shoppingCart = By.xpath("//*[@class='shopping_cart_link']");

    public ProductsPage(WebDriver driver, ExtentTest test) {
        super(driver);
        this.test = test;
    }

    public void clickBurgetButton() {
        jsClick(burgerButton, "Clicked on Burget Button");
    }

    public void clickLogoutButton() {
        click(logoutButton, "Clicked on Logout Button");
        waitForSeconds(3);
    }

    public void clickAddToCart(String product) {
        String addToCart = "//*[text()='"+product+"']/parent::a/parent::div/parent::div//*[text()='Add to cart']";
        click(By.xpath(addToCart), "Clicked on Add to Cart Button");
    }

    public void clickShoppingCart() {
        click(shoppingCart, "Clicked on Shopping Cart");
    }
}
