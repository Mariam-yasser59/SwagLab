package checkoutTest;

import baseTest.BaseTest;
import dataTestProvider.DataProviderTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.cart.CartPage;
import pages.information.InformationPage;
import pages.login.LoginPage;
import pages.overview.OverviewPage;
import pages.product.ProductPage;

public class CheckoutTest extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(CheckoutTest.class);


    @Test(
            dataProvider = "validCredentials",
            dataProviderClass = DataProviderTest.class
    )
    public void checkout(
            String username,
            String password
    ) {

        logger.info("===== Start Checkout Test =====");
        logger.info("Testing username: {}", username);

        LoginPage login =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        CartPage cartPage =
                new CartPage(driver);

        logger.info("Logging in");

        login.enterUsername(username);
        login.enterPassword(password);
        login.clickLogin();

        logger.info("Adding selected products");

        productPage.addSelectedProducts();

        productPage.clickCartButton();

        int count = 0;

        for (String id : ProductPage.productIds) {

            for (WebElement checkItem :
                    driver.findElements(
                            By.xpath(
                                    "//*[@class='btn btn_secondary btn_small cart_button']"
                            )
                    )) {

                if (id.equals(
                        checkItem
                                .getAttribute("id")
                                .replace("remove-", "")
                )) {

                    count++;
                }
            }
        }

        logger.info(
                "Products expected: {} | Products found: {}",
                ProductPage.productIds.size(),
                count
        );

        cartPage.clickCheckout();

        softAssert.assertTrue(
                count == ProductPage.productIds.size(),
                "Cart products count does not match"
        );

        softAssert.assertTrue(
                productPage.navigateToPage(
                        configHandler.getValue("checkoutUrlStepOne")
                )
        );

        softAssert.assertAll();

        logger.info("Checkout test passed");
        logger.info("===== End Checkout Test =====");
    }


    @Test(
            dataProvider = "validCredentials",
            dataProviderClass = DataProviderTest.class
    )
    public void checkTotalSalary(
            String username,
            String password
    ) throws InterruptedException {

        logger.info("===== Start Check Total Price Test =====");
        logger.info("Testing username: {}", username);

        driver.get(
                configHandler.getValue("url")
        );

        LoginPage login =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        InformationPage informationPage =
                new InformationPage(driver);

        CartPage cartPage =
                new CartPage(driver);

        OverviewPage overviewPage =
                new OverviewPage(driver);

        logger.info("Logging in");

        login.enterUsername(username);
        login.enterPassword(password);
        login.clickLogin();

        logger.info("Calculating actual total price");

        double actualTotalPrice =
                productPage.price();

        logger.info(
                "Actual total price: {}",
                actualTotalPrice
        );

        productPage.clickCartButton();

        cartPage.clickCheckout();

        logger.info("Entering checkout information");

        informationPage.enterFirstname(
                configHandler.getValue("firstname")
        );

        informationPage.enterLastname(
                configHandler.getValue("lastname")
        );

        informationPage.enterPostalCode(
                configHandler.getValue("postalCode")
        );

        informationPage.clickContinue();

        double expectedTotalPrice =
                overviewPage.parseDouble(
                        overviewPage.getSubTotal()
                );

        logger.info(
                "Expected subtotal: {}",
                expectedTotalPrice
        );

        SoftAssert softAssert =
                new SoftAssert();

        softAssert.assertTrue(
                overviewPage.checkSubtotal(
                        actualTotalPrice,
                        expectedTotalPrice
                )
        );

        softAssert.assertTrue(
                overviewPage.checkTotal(
                        actualTotalPrice
                )
        );

        softAssert.assertAll();

        logger.info(
                "Total price validation passed"
        );

        logger.info("===== End Check Total Price Test =====");
    }
}