package stepDefinition;

import io.cucumber.java.en.And;
import page.ProductsPage;

public class ProductSteps extends BaseTest {
    ProductsPage productsPage;

    public ProductSteps() {
        productsPage = new ProductsPage(driver, test);
    }

    @And("we add {string} to cart in product page")
    public void weAddToCartInProductPage(String product) {
        productsPage.clickAddToCart(product);
        productsPage.clickShoppingCart();
    }
}
