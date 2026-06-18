package utils;

import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

/**
 * A sophisticated extension of TestNG's SoftAssert.
 * Provides custom, robust assertion methods specifically tailored for web automation.
 */
public class CustomSoftAssert extends SoftAssert {

    /**
     * Extracts all visible text from the entire page body safely.
     */
    private String getPageText() {
        try {
            WaitStrategy.waitForPageLoad();
            return DriverManager.getDriver().findElement(By.tagName("body")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Asserts that a specific string of text exists ANYWHERE on the current page.
     */
    public void assertTextPresentOnPage(String expectedText, String customMessage) {
        String pageText = getPageText();
        assertTrue(pageText.contains(expectedText), 
            customMessage + "\n=> EXPECTED TEXT: '" + expectedText + "' was NOT found on the page.\n");
    }

    /**
     * Asserts that a specific string of text does NOT exist anywhere on the current page.
     */
    public void assertTextNotPresentOnPage(String unexpectedText, String customMessage) {
        String pageText = getPageText();
        assertFalse(pageText.contains(unexpectedText), 
            customMessage + "\n=> UNEXPECTED TEXT: '" + unexpectedText + "' WAS found on the page but shouldn't be!\n");
    }

    /**
     * Asserts that a specific WebElement contains a partial string.
     */
    public void assertElementContainsText(By locator, String expectedText, String elementName) {
        try {
            WaitStrategy.waitForPageLoad();
            WebElement element = WaitStrategy.waitForVisibility(locator);
            String actualText = element.getText();
            assertTrue(actualText.contains(expectedText), 
                "Element '" + elementName + "' did not contain the expected partial text." +
                "\n=> EXPECTED TO CONTAIN: '" + expectedText + "'" +
                "\n=> ACTUAL TEXT FOUND: '" + actualText + "'\n");
        } catch (Exception e) {
            fail("Could not validate text for element '" + elementName + "' because it was not visible or found. Exception: " + e.getMessage());
        }
    }

    /**
     * Asserts that a specific WebElement exactly matches a string.
     */
    public void assertElementTextEquals(By locator, String expectedText, String elementName) {
        try {
            WaitStrategy.waitForPageLoad();
            WebElement element = WaitStrategy.waitForVisibility(locator);
            String actualText = element.getText();
            assertEquals(actualText, expectedText, 
                "Element '" + elementName + "' text completely mismatched." +
                "\n=> EXACT MATCH EXPECTED: '" + expectedText + "'" +
                "\n=> ACTUAL TEXT FOUND: '" + actualText + "'\n");
        } catch (Exception e) {
            fail("Could not validate exact text for element '" + elementName + "' because it was not visible or found. Exception: " + e.getMessage());
        }
    }

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        // Take a screenshot at the EXACT millisecond this specific soft assertion fails
        ScreenshotUtil.takeScreenshotAndAttachToAllure();
        super.onAssertFailure(assertCommand, ex);
    }
}
