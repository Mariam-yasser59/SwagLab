package pages.information;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.product.ProductPage;

public class InformationPage extends BasePage {

    private Logger logger = LogManager.getLogger(InformationPage.class);

    private final By firstnameField = By.id("first-name");
    private final By lastnameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    public InformationPage(WebDriver driver){
        super(driver);
    }

    public WebElement getFirstname(){
        logger.debug("getFirstname");
        return findElement(firstnameField);
    }
    public WebElement getLastname(){
        logger.debug("getLastname");
        return findElement(lastnameField);
    }
    public WebElement getPostalCode(){
        logger.debug("getPostalCode");
        return findElement(postalCodeField);
    }
    public WebElement getContinueButton(){
        logger.debug("getContinueButton");
        return findElement(continueBtn);
    }
    public void enterFirstname(String username){
        logger.debug("enterFirstname");
        getFirstname().sendKeys(username);
    }
    public void enterLastname(String lastname){
        logger.debug("enterLastname");
        getLastname().sendKeys(lastname);
    }
    public void enterPostalCode(String postalCode){
        logger.debug("enterPostalCode");
        getPostalCode().sendKeys(postalCode);
    }
    public void clickContinue(){
        logger.info("clickContinue");
        getContinueButton().click();
    }

}
