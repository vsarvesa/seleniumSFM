package utils;

import config.ConfigReader;
import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitStrategy {

    private static int getTimeout() {
        return ConfigReader.getInstance().getExplicitWait();
    }

    public static WebElement waitForVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getTimeout()));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getTimeout()));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForPresence(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getTimeout()));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits for the DOM readyState to become 'complete', meaning all HTML and assets have loaded.
     */
    public static void waitForPageLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getTimeout()));
            wait.until(driver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            // Catch timeouts in case of completely dynamic single-page applications
        }
    }

    /**
     * Waits for an element (like a loading spinner or overlay block) to completely disappear from the page.
     */
    public static boolean waitForInvisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getTimeout()));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * A deep wait that checks if jQuery or general AJAX requests are finished.
     */
    public static void waitForAjaxToComplete() {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getTimeout()));
            wait.until(driver -> (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return (typeof jQuery != 'undefined') ? jQuery.active == 0 : true"));
        } catch (Exception e) {
            // Catch timeouts if the page doesn't use jQuery
        }
    }
}
