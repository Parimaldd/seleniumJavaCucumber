Feature: Add product to cart

  Scenario Outline:Test scenario to verify Login and add product
    Given we login with "<UserId>" and "<Password>" in Swag Labs
    And we add "<Product>" to cart in product page
    And verify "<Product>" in the cart page
    And user clicks on logout button

    Examples:
      | UserId        | Password     | Product                 |
      | standard_user | secret_sauce | Sauce Labs Bolt T-Shirt |
