package pages.product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends BasePage {

    private final By titleField = By.className("title");
    private final By allProductsLocator = By.cssSelector("button.btn_inventory");
    private final By allProductsCarts = By.cssSelector(".inventory_item");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");
    private final By shoppingCartButton = By.className("shopping_cart_link");
    private final By dropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By addToCartLocator = By.tagName("button");

    public static List<String> productIds = new ArrayList<>();

    private Logger logger = LogManager.getLogger(ProductPage.class);

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getTitle() {
        logger.info("Getting Products page title");
        return findElement(titleField);
    }

    public WebElement getShoppingCartBadge() {
        logger.debug("Getting shopping cart badge");
        return findElement(shoppingCartBadge);
    }

    public WebElement getShoppingCartButton() {
        logger.debug("Getting shopping cart button");
        return findElement(shoppingCartButton);
    }

    public WebElement getFilterButton() {
        logger.debug("Getting product filter dropdown");
        return findElement(dropdown);
    }

    public WebElement getAddToCartButton() {
        logger.debug("Getting Add to Cart button");
        return findElement(addToCartLocator);
    }

    public List<WebElement> getAllButtons() {
        logger.debug("Getting all Add to Cart buttons");
        return findElements(allProductsLocator);
    }

    public List<WebElement> getAllProducts() {
        logger.debug("Getting all products");
        return findElements(allProductsCarts);
    }

    public List<String> addSelectedProducts() {

        logger.info("Starting to add selected products to cart");

        String[] substrings = new String[]{
                "backpack",
                "bike",
                "t-shirt",
                "jacket"
        };

        for (String sub : substrings) {

            logger.debug("Searching for product containing: {}", sub);

            List<WebElement> elements = getAllButtons();

            for (WebElement el : elements) {

                String id = el.getAttribute("id");

                if (id.toLowerCase().contains(sub)) {

                    WebElement priceEl = el.findElement(
                            By.xpath("preceding-sibling::div[@class='inventory_item_price']")
                    );

                    String price = priceEl.getText();

                    logger.info("Adding product: {} with price: {}", id, price);

                    el.click();

                    productIds.add(id.replace("add-to-cart-", ""));
                }
            }
        }

        logger.info("Selected products added successfully. Total products: {}",
                productIds.size());

        return productIds;
    }

    public double price() {

        logger.info("Calculating total price of selected products");

        String[] substrings = new String[]{
                "backpack",
                "bike",
                "t-shirt",
                "jacket"
        };

        double price = 0;

        for (String sub : substrings) {

            logger.debug("Searching for product: {}", sub);

            List<WebElement> elements = getAllButtons();

            for (WebElement el : elements) {

                String id = el.getAttribute("id");

                if (id.toLowerCase().contains(sub)) {

                    WebElement priceEl = el.findElement(
                            By.xpath("preceding-sibling::div[@class='inventory_item_price']")
                    );

                    logger.info("Product: {} | Price: {}",
                            id,
                            priceEl.getText());

                    price += parseDouble(priceEl);

                    el.click();
                }
            }
        }

        logger.info("Total calculated price: {}", price);

        return price;
    }

    public void clickCartButton() {

        logger.info("Clicking shopping cart button");

        getShoppingCartButton().click();
    }

    public boolean takeDecision(String str) {

        logger.debug("Checking product sorting using decision: {}", str);

        List<WebElement> carts = getAllProducts();

        List<String> titles = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        for (WebElement cart : carts) {

            titles.add(
                    cart.findElement(By.className("inventory_item_name"))
                            .getText()
            );

            prices.add(
                    parseDouble(
                            cart.findElement(By.className("inventory_item_price"))
                    )
            );
        }

        switch (str) {

            case "<":

                logger.info("Checking products sorted from A to Z");

                for (int i = 0; i < titles.size() - 1; i++) {

                    if (titles.get(i).compareTo(titles.get(i + 1)) > 0) {

                        logger.error("A to Z sorting validation failed");
                        return false;
                    }
                }

                logger.info("A to Z sorting validation passed");
                return true;

            case ">":

                logger.info("Checking products sorted from Z to A");

                for (int i = 0; i < titles.size() - 1; i++) {

                    if (titles.get(i).compareTo(titles.get(i + 1)) < 0) {

                        logger.error("Z to A sorting validation failed");
                        return false;
                    }
                }

                logger.info("Z to A sorting validation passed");
                return true;

            case "n<":

                logger.info("Checking products sorted by price Low to High");

                for (int i = 0; i < prices.size() - 1; i++) {

                    if (prices.get(i) > prices.get(i + 1)) {

                        logger.error("Low to High price sorting validation failed");
                        return false;
                    }
                }

                logger.info("Low to High price sorting validation passed");
                return true;

            case "n>":

                logger.info("Checking products sorted by price High to Low");

                for (int i = 0; i < prices.size() - 1; i++) {

                    if (prices.get(i) < prices.get(i + 1)) {

                        logger.error("High to Low price sorting validation failed");
                        return false;
                    }
                }

                logger.info("High to Low price sorting validation passed");
                return true;

            default:

                logger.error("Invalid sorting decision: {}", str);
                return false;
        }
    }

    public boolean checkFilterAToZ() {

        logger.info("Checking A to Z filter");

        return takeDecision("<");
    }

    public boolean checkFilterZToA() {

        logger.info("Checking Z to A filter");

        return takeDecision(">");
    }

    public boolean checkFilterLowToHighPrice() {

        logger.info("Checking Low to High price filter");

        return takeDecision("n<");
    }

    public boolean checkFilterHighToLowPrice() {

        logger.info("Checking High to Low price filter");

        return takeDecision("n>");
    }

    public boolean checkFilter(int idx) {

        logger.info("Checking filter with index: {}", idx);

        return switch (idx) {

            case 0 -> checkFilterAToZ();
            case 1 -> checkFilterZToA();
            case 2 -> checkFilterLowToHighPrice();
            case 3 -> checkFilterHighToLowPrice();

            default -> {
                logger.error("Unexpected filter index: {}", idx);
                throw new IllegalStateException(
                        "Unexpected value: " + idx
                );
            }
        };
    }

    public boolean cartIconLogic() {

        logger.info("Starting cart icon validation");

        List<WebElement> buttons = getAllButtons();

        logger.debug("Clicking first product");
        buttons.get(0).click();

        logger.debug("Clicking second product");
        buttons.get(1).click();

        WebElement cartContainer =
                driver.findElement(By.className("shopping_cart_container"));

        for (int i = 0; i < 2; i++) {

            String ariaLabel = cartContainer
                    .findElement(By.tagName("a"))
                    .getAttribute("aria-label");

            logger.info("Cart aria-label: {}", ariaLabel);

            buttons = getAllButtons();

            logger.debug("Clicking product button at index: {}", i);

            buttons.get(i).click();
        }

        String finalAriaLabel = cartContainer
                .findElement(By.tagName("a"))
                .getAttribute("aria-label");

        boolean result = finalAriaLabel.contains("empty");

        logger.info("Cart empty validation result: {}", result);

        return result;
    }
}