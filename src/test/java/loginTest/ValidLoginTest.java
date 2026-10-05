package loginTest;

import baseTest.BaseTest;
import dataTestProvider.DataProviderTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.login.LoginPage;
import pages.product.ProductPage;

public class ValidLoginTest extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(ValidLoginTest.class);

    @Test(
            dataProvider = "validCredentials",
            dataProviderClass = DataProviderTest.class
    )
    public void validLogin(
            String username,
            String password
    ) {

        logger.info("===== Start Valid Login Test =====");
        logger.info("Testing username: {}", username);

        LoginPage loginPage = new LoginPage(driver);

        logger.info("Entering username");
        loginPage.enterUsername(username);

        logger.info("Entering password");
        loginPage.enterPassword(password);

        logger.info("Clicking Login button");
        loginPage.clickLogin();

        ProductPage productPage =
                new ProductPage(driver);

        String actualTitle =
                productPage.getTitle().getText();

        logger.info(
                "Actual page title: {}",
                actualTitle
        );

        Assert.assertEquals(
                actualTitle,
                "Products",
                "User was not redirected to Products page"
        );

        logger.info(
                "Valid login passed for user: {}",
                username
        );

        logger.info("===== End Valid Login Test =====");
    }
}