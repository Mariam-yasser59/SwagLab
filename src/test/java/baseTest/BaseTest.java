package baseTest;

import driverFactory.GetChromeDriver;
import informationTest.InformationTest;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.asserts.SoftAssert;
import utilies.ConfigHandler;
import utilies.JSONFileManager;
import utilies.ScreenShot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public SoftAssert softAssert;
    public ConfigHandler configHandler;
    public JSONFileManager productJsonFileManager;
    public JSONFileManager loginJsonFileManager;

    private static final Logger logger =
            LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp(){
        configHandler = new ConfigHandler("src/main/resources/config.properties");
        productJsonFileManager = new JSONFileManager("src/main/resources/product.json");
        loginJsonFileManager = new JSONFileManager("src/main/resources/login.json");
        driver = GetChromeDriver.getWebDriver();
        driver.manage().window().maximize();
        driver.get(configHandler.getValue("url"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        softAssert = new SoftAssert();
        logger.debug("Current url is: " + driver.getCurrentUrl());
        logger.debug("Current driver is: " + driver);
    }

    @AfterMethod
    public void failedTestCase(ITestResult result) throws FileNotFoundException {
        if (result.getStatus() == ITestResult.FAILURE){
            File image= ScreenShot.takeScreenShot(driver);
            FileInputStream file =new FileInputStream(image);
            Allure.addAttachment("Screenshot", "image/png" , file,"png");


        }
    }

    @AfterMethod
    public void tearDown(){

        GetChromeDriver.quitDriver();
        driver = null;
    }
}