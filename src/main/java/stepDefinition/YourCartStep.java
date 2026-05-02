package stepDefinition;

import common.BasePage2;
import io.cucumber.java.en.And;
import org.assertj.core.api.SoftAssertions;
import page.YourCartPage;

public class YourCartStep extends BaseTest {

    YourCartPage yourCartPage;
    SoftAssertions softly;

    public YourCartStep() {
        yourCartPage = new YourCartPage(driver, test);
        softly = new SoftAssertions();
    }

    @And("verify {string} in the cart page")
    public void verifyInTheCartPage(String product) {
        String productName = yourCartPage.getAddedProductText(product);
        yourCartPage.softAssert(productName,product,"verify added productName is same", BasePage2.AssertType.EQUALS);
    }
}
