package pages;

import driver.DriverManager;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
    }

    /**
     * Extracts all visible text from the entire page body and checks if the expected string is present.
     * This is highly useful for quick SoftAssert validations.
     *
     * @param expectedText The text to search for anywhere on the page.
     * @return true if the text is found, false otherwise.
     */
    public boolean isTextPresentOnPage(String expectedText) {
        try {
            // Grab the root body element and extract all visible text on the page
            String pageText = driver.findElement(org.openqa.selenium.By.tagName("body")).getText();
            return pageText.contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }
}
