package pages.login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class LoginPage extends BasePage{

    private Logger logger = LogManager.getLogger(LoginPage.class);

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public WebElement getUsernameField() {
        logger.info("Geeting username Field");
        return findElement(usernameField);
    }
    public WebElement getPasswordField() {
        logger.info("Geeting password Field");
        return findElement(passwordField);
    }
    public WebElement getLoginBtn() {
        logger.info(" Geeting login Button");
        return findElement(loginBtn);
    }

    public void enterUsername(String username){
        getUsernameField().sendKeys(username);
        logger.debug("Entering username: {}", username);
    }
    public void enterPassword(String password){

        getPasswordField().sendKeys(password);
        logger.debug("Entering password: {}", password);
    }
    public void clickLogin(){

        getLoginBtn().click();
        logger.info("Clicking on login button");
    }
}
