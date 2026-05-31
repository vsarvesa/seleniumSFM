package actions;

import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utils.WaitStrategy;

public class ScrollActions {

    private static final Logger logger = LogManager.getLogger(ScrollActions.class);

    private static JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) DriverManager.getDriver();
    }

    /**
     * Scrolls the web page so that the specific element is brought into the visible viewport.
     * 
     * @param locator The locator of the element to scroll to.
     */
    public static void scrollToElement(By locator) {
        try {
            WebElement element = WaitStrategy.waitForPresence(locator);
            getJsExecutor().executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            logger.info("Scrolled to element successfully.");
        } catch (Exception e) {
            logger.error("Failed to scroll to element. Reason: " + e.getMessage());
        }
    }

    /**
     * Scrolls straight to the very bottom of the web page.
     */
    public static void scrollToBottom() {
        try {
            getJsExecutor().executeScript("window.scrollTo(0, document.body.scrollHeight)");
            logger.info("Scrolled to the bottom of the page.");
        } catch (Exception e) {
            logger.error("Failed to scroll to the bottom. Reason: " + e.getMessage());
        }
    }

    /**
     * Scrolls straight to the very top of the web page.
     */
    public static void scrollToTop() {
        try {
            getJsExecutor().executeScript("window.scrollTo(0, 0)");
            logger.info("Scrolled to the top of the page.");
        } catch (Exception e) {
            logger.error("Failed to scroll to the top. Reason: " + e.getMessage());
        }
    }

    /**
     * Scrolls the web page by a specific number of pixels.
     * 
     * @param x Horizontal scroll amount (positive goes right, negative goes left)
     * @param y Vertical scroll amount (positive goes down, negative goes up)
     */
    public static void scrollByPixels(int x, int y) {
        try {
            getJsExecutor().executeScript("window.scrollBy(" + x + ", " + y + ")");
            logger.info("Scrolled by pixels. X: " + x + ", Y: " + y);
        } catch (Exception e) {
            logger.error("Failed to scroll by pixels. Reason: " + e.getMessage());
        }
    }
}
