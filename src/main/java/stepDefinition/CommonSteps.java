package stepDefinition;

import io.cucumber.java.*;
import io.cucumber.java.en.*;
import page.LoginPage;
import page.ProductsPage;
import io.cucumber.java.Scenario;

public class CommonSteps extends BaseTest {

    LoginPage loginpage;
    ProductsPage productsPage;

    @Before
    public void startTest(Scenario scenario) {
        softFailures.clear();
        initReport();
        createTest(scenario.getName());
        setup();
    }

    @After
    public void endTest() {
        teardown();
        flushReport();
    }

    @Given("we login with {string} and {string} in Swag Labs")
    public void weLoginWithAndInSwagLabs(String userID, String userPassword) {
        loginpage = new LoginPage(driver,test);
        loginpage.login(userID, userPassword);
    }

    @And("user clicks on logout button")
    public void userClicksOnLogoutButton() {
        productsPage= new ProductsPage(driver,test);
        productsPage.clickBurgetButton();
        productsPage.clickLogoutButton();
    }
}
