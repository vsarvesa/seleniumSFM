package actions;

import driver.DriverManager;
import exceptions.CustomElementNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.WaitStrategy;

public class ElementActions {

    private static final Logger logger = LogManager.getLogger(ElementActions.class);
    // Increased to 2 so attempts are: 0 (1st), 1 (2nd), 2 (3rd/JS Fallback)
    private static final int MAX_RETRIES = 2; 

    public static void click(By locator, String elementName) {
        int attempt = 0;
        while (attempt <= MAX_RETRIES) {
            try {
                WebElement element = WaitStrategy.waitForPresence(locator);
                
                if (attempt == MAX_RETRIES) {
                    logger.warn("Attempt 3: Using JavascriptExecutor to click element: " + elementName);
                    JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
                    js.executeScript("arguments[0].click();", element);
                } else {
                    WaitStrategy.waitForClickability(locator);
                    element.click();
                }
                
                logger.info("Clicked on element: " + elementName);
                return;
            } catch (Exception e) {
                logger.warn("Failed to click element: " + elementName + " on attempt " + (attempt + 1) + ". Reason: " + e.getMessage());
                attempt++;
                if (attempt > MAX_RETRIES) {
                    logger.error("Element not interactable after retries: " + elementName);
                    throw new CustomElementNotFoundException("Failed to click element '" + elementName + "' after " + (MAX_RETRIES + 1) + " attempts.", e);
                }
            }
        }
    }

    public static void type(By locator, String text, String elementName) {
        int attempt = 0;
        while (attempt <= MAX_RETRIES) {
            try {
                WebElement element = WaitStrategy.waitForPresence(locator);
                
                if (attempt == MAX_RETRIES) {
                    logger.warn("Attempt 3: Using JavascriptExecutor to type into element: " + elementName);
                    JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
                    js.executeScript("arguments[0].value='" + text + "';", element);
                } else {
                    WaitStrategy.waitForVisibility(locator);
                    element.clear();
                    element.sendKeys(text);
                }
                
                logger.info("Typed text '" + text + "' into element: " + elementName);
                return;
            } catch (Exception e) {
                logger.warn("Failed to type into element: " + elementName + " on attempt " + (attempt + 1) + ". Reason: " + e.getMessage());
                attempt++;
                if (attempt > MAX_RETRIES) {
                    logger.error("Element not interactable after retries: " + elementName);
                    throw new CustomElementNotFoundException("Failed to type into element '" + elementName + "' after " + (MAX_RETRIES + 1) + " attempts.", e);
                }
            }
        }
    }

    public static void selectByVisibleText(By locator, String visibleText, String dropdownName) {
        int attempt = 0;
        while (attempt <= MAX_RETRIES) {
            try {
                WebElement element = WaitStrategy.waitForPresence(locator);
                Select select = new Select(element);
                select.selectByVisibleText(visibleText);
                logger.info("Selected '" + visibleText + "' from dropdown: " + dropdownName);
                return;
            } catch (Exception e) {
                logger.warn("Failed to select from dropdown: " + dropdownName + " on attempt " + (attempt + 1) + ". Reason: " + e.getMessage());
                attempt++;
                if (attempt > MAX_RETRIES) {
                    logger.error("Dropdown not interactable after retries: " + dropdownName);
                    throw new CustomElementNotFoundException("Failed to select from dropdown '" + dropdownName + "' after " + (MAX_RETRIES + 1) + " attempts.", e);
                }
            }
        }
    }
}
