package productTest;

import baseTest.BaseTest;
import dataTestProvider.DataProviderTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.login.LoginPage;
import pages.product.ProductPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductTest extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(ProductTest.class);

    @Test(
            dataProvider = "validCredentials",
            dataProviderClass = DataProviderTest.class
    )
    public void addToCart(String username, String password) {

        logger.info("===== Start Add To Cart Test =====");
        logger.info("Testing with username: {}", username);

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        logger.info("Entering username");
        login.enterUsername(username);

        logger.info("Entering password");
        login.enterPassword(password);

        logger.info("Clicking Login button");
        login.clickLogin();

        logger.info("Adding selected products to cart");

        List<String> addedIds = productPage.addSelectedProducts();

        logger.info("Number of products added: {}", addedIds.size());

        softAssert.assertTrue(
                addedIds.size() == 5,
                "Expected 5 products to be added, but found "
                        + addedIds.size()
        );

        logger.info("Opening shopping cart");

        productPage.getShoppingCartBadge().click();

        String currentUrl = driver.getCurrentUrl();

        logger.info("Current URL: {}", currentUrl);

        Assert.assertEquals(
                currentUrl,
                "https://www.saucedemo.com/cart.html"
        );

        softAssert.assertAll();

        logger.info("Add To Cart Test passed");
        logger.info("===== End Add To Cart Test =====");
    }


    @Test(
            dataProvider = "validCredentials",
            dataProviderClass = DataProviderTest.class
    )
    public void checkCartIconNumber(String username, String password) {

        logger.info("===== Start Cart Icon Number Test =====");
        logger.info("Testing with username: {}", username);

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        logger.info("Logging in");

        login.enterUsername(username);
        login.enterPassword(password);
        login.clickLogin();

        logger.info("Checking cart icon number behavior");

        boolean result = productPage.cartIconLogic();

        logger.info("Cart icon validation result: {}", result);

        softAssert.assertTrue(
                result,
                "Cart icon number logic failed"
        );

        softAssert.assertAll();

        logger.info("Cart Icon Number Test passed");
        logger.info("===== End Cart Icon Number Test =====");
    }


    @Test(
            dataProvider = "validCredentials",
            dataProviderClass = DataProviderTest.class
    )
    public void checkRemoveIsDisplayed(
            String username,
            String password
    ) throws InterruptedException {

        logger.info("===== Start Remove Button Test =====");
        logger.info("Testing with username: {}", username);

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        logger.info("Logging in");

        login.enterUsername(username);
        login.enterPassword(password);
        login.clickLogin();

        logger.info("Reading products from JSON file");

        List<String> substrings = new ArrayList<>(Arrays.asList(
                productJsonFileManager.getValue("product1")
                        .toString().toLowerCase(),

                productJsonFileManager.getValue("product2")
                        .toString().toLowerCase(),

                productJsonFileManager.getValue("product3")
                        .toString().toLowerCase(),

                productJsonFileManager.getValue("product4")
                        .toString().toLowerCase()
        ));

        List<String> linksIds = new ArrayList<>();

        logger.info("Searching for selected products");

        for (String sub : substrings) {

            logger.debug("Searching for product containing: {}", sub);

            List<WebElement> elements =
                    productPage.getAllProducts();

            for (WebElement el : elements) {

                String productName = el
                        .findElement(
                                By.className("inventory_item_name")
                        )
                        .getText();

                if (productName.toLowerCase().contains(sub)) {

                    logger.info(
                            "Adding product to cart: {}",
                            productName
                    );

                    el.findElement(
                            By.tagName("button")
                    ).click();

                    String linkId = el
                            .findElement(By.tagName("a"))
                            .getAttribute("id");

                    linksIds.add(linkId);

                    logger.debug(
                            "Product link ID added: {}",
                            linkId
                    );
                }
            }
        }

        logger.info(
                "Total selected product links: {}",
                linksIds.size()
        );

        for (String linkId : linksIds) {

            logger.info(
                    "Opening product details using link ID: {}",
                    linkId
            );

            driver.findElement(By.id(linkId)).click();

            Thread.sleep(1000);

            WebElement removeBtn =
                    driver.findElement(By.id("remove"));

            logger.info(
                    "Checking Remove button visibility for product: {}",
                    linkId
            );

            softAssert.assertTrue(
                    removeBtn.isDisplayed(),
                    "Remove button is not displayed for product: "
                            + linkId
            );

            driver.navigate().back();

            Thread.sleep(1000);
        }

        softAssert.assertAll();

        logger.info("Remove Button Test passed");
        logger.info("===== End Remove Button Test =====");
    }


    @Test(
            dataProvider = "validCredentials",
            dataProviderClass = DataProviderTest.class
    )
    public void filterProducts(
            String username,
            String password
    ) throws InterruptedException {

        logger.info("===== Start Filter Products Test =====");
        logger.info("Testing with username: {}", username);

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        logger.info("Logging in");

        login.enterUsername(username);
        login.enterPassword(password);
        login.clickLogin();

        WebElement dropdown;
        Select select;

        for (int i = 0; i < 4; i++) {

            logger.info(
                    "Applying product filter with index: {}",
                    i
            );

            dropdown = productPage.getFilterButton();

            select = new Select(dropdown);

            select.selectByIndex(i);

            boolean result =
                    productPage.checkFilter(i);

            logger.info(
                    "Filter {} validation result: {}",
                    i,
                    result
            );

            softAssert.assertTrue(
                    result,
                    "Filter validation failed for index: " + i
            );

            Thread.sleep(2000);
        }

        logger.info("All product filters have been checked");

        softAssert.assertAll();

        logger.info("Filter Products Test passed");
        logger.info("===== End Filter Products Test =====");
    }
}