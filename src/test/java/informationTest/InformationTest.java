package informationTest;

import baseTest.BaseTest;
import dataTestProvider.DataProviderTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.cart.CartPage;
import pages.information.InformationPage;
import pages.login.LoginPage;
import pages.product.ProductPage;

public class InformationTest extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(InformationTest.class);

    @Test(
            dataProvider = "getInfo",
            dataProviderClass = DataProviderTest.class
    )
    public void checkoutInfo(
            String firstName,
            String lastName,
            String postalCode
    ) {

        logger.info("===== Start Checkout Information Test =====");

        logger.info(
                "Testing information: {} {} - {}",
                firstName,
                lastName,
                postalCode
        );

        LoginPage login =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        CartPage cartPage =
                new CartPage(driver);

        logger.info("Logging in");

        login.enterUsername(
                configHandler.getValue("username")
        );

        login.enterPassword(
                configHandler.getValue("password")
        );

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

        logger.info("Entering checkout information");

        InformationPage infoPage =
                new InformationPage(driver);

        infoPage.enterFirstname(firstName);
        infoPage.enterLastname(lastName);
        infoPage.enterPostalCode(postalCode);

        logger.info("Clicking Continue");

        infoPage.clickContinue();

        Assert.assertTrue(
                infoPage.navigateToPage(
                        configHandler.getValue("checkoutUrlStepTwo")
                )
        );

        logger.info("Checkout information test passed");
        logger.info("===== End Checkout Information Test =====");
    }
}