package pages.generateReport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.overview.OverviewPage;

public class GenerateReport extends BasePage {

    private Logger logger = LogManager.getLogger(GenerateReport.class);


    private final By generateButton = By.id("generate-pdf-order");

    public GenerateReport(WebDriver driver){
        super(driver);
    }

    public WebElement getGenerateButton() {
        logger.debug("getGenerateButton");
        return findElement(generateButton);
    }

    public void clickGenerateButton() {
        logger.info("clickGenerateButton");
        getGenerateButton().click();
    }

}
