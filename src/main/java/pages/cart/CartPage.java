package pages.cart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.generateReport.GenerateReport;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    private Logger logger = LogManager.getLogger(CartPage.class);


    private final By checkoutLocator = By.id("checkout");

    public CartPage( WebDriver driver){
        super(driver);
    }


    public WebElement getCheckoutButton(){
        logger.debug("getCheckoutButton");
        return findElement(checkoutLocator);
    }

    public void clickCheckout(){
        logger.info("clickCheckout");
        getCheckoutButton().click();
    }


}
