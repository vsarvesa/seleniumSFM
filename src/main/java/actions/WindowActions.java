package actions;

import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WindowActions {

    private static final Logger logger = LogManager.getLogger(WindowActions.class);

    private static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    /**
     * Switches to the most recently opened window or tab.
     */
    public static void switchToNewWindow() {
        try {
            Set<String> windowHandles = getDriver().getWindowHandles();
            List<String> handlesList = new ArrayList<>(windowHandles);
            
            // The newest window is typically the last one in the set
            String newestWindow = handlesList.get(handlesList.size() - 1);
            getDriver().switchTo().window(newestWindow);
            
            logger.info("Switched to new window/tab. Current Title: " + getDriver().getTitle());
        } catch (Exception e) {
            logger.error("Failed to switch to new window. Reason: " + e.getMessage());
        }
    }

    /**
     * Iterates through all open windows and switches to the one that matches the specific title.
     * 
     * @param exactTitle The exact title of the window you want to switch to.
     */
    public static void switchToWindowByTitle(String exactTitle) {
        try {
            Set<String> windowHandles = getDriver().getWindowHandles();
            for (String handle : windowHandles) {
                getDriver().switchTo().window(handle);
                if (getDriver().getTitle().equals(exactTitle)) {
                    logger.info("Successfully switched to window with title: " + exactTitle);
                    return;
                }
            }
            logger.warn("Could not find any window with the title: " + exactTitle);
        } catch (Exception e) {
            logger.error("Failed to switch to window by title. Reason: " + e.getMessage());
        }
    }

    /**
     * Iterates through all open windows and switches to the one whose URL contains the specific text.
     * 
     * @param partialUrl The partial or exact URL of the window you want to switch to.
     */
    public static void switchToWindowByUrl(String partialUrl) {
        try {
            Set<String> windowHandles = getDriver().getWindowHandles();
            for (String handle : windowHandles) {
                getDriver().switchTo().window(handle);
                if (getDriver().getCurrentUrl().contains(partialUrl)) {
                    logger.info("Successfully switched to window with URL containing: " + partialUrl);
                    return;
                }
            }
            logger.warn("Could not find any window with URL containing: " + partialUrl);
        } catch (Exception e) {
            logger.error("Failed to switch to window by URL. Reason: " + e.getMessage());
        }
    }

    /**
     * Switches back to the original/main window (the first window opened in the session).
     */
    public static void switchToMainWindow() {
        try {
            Set<String> windowHandles = getDriver().getWindowHandles();
            List<String> handlesList = new ArrayList<>(windowHandles);
            
            // The main window is always index 0
            getDriver().switchTo().window(handlesList.get(0));
            logger.info("Switched back to the Main window.");
        } catch (Exception e) {
            logger.error("Failed to switch back to main window. Reason: " + e.getMessage());
        }
    }

    /**
     * Closes the current active window/tab. If there are other windows open, 
     * it automatically switches focus back to the main window to prevent 'No Such Window' exceptions.
     */
    public static void closeCurrentWindowAndSwitchToMain() {
        try {
            getDriver().close();
            logger.info("Closed the current window/tab.");
            switchToMainWindow();
        } catch (Exception e) {
            logger.error("Failed to close window. Reason: " + e.getMessage());
        }
    }

    /**
     * Uses Selenium 4's native functionality to open a brand new Blank Tab and instantly switch to it.
     */
    public static void openNewTabAndSwitch() {
        getDriver().switchTo().newWindow(WindowType.TAB);
        logger.info("Opened and switched to a brand new blank TAB.");
    }

    /**
     * Uses Selenium 4's native functionality to open a brand new Blank Window and instantly switch to it.
     */
    public static void openNewWindowAndSwitch() {
        getDriver().switchTo().newWindow(WindowType.WINDOW);
        logger.info("Opened and switched to a brand new blank WINDOW.");
    }

    /**
     * Switches to a specific window or tab based on its zero-based index.
     * Index 0 is the main window, Index 1 is the second window, and so on.
     * 
     * @param index The index number of the window to switch to.
     */
    public static void switchToWindowByIndex(int index) {
        try {
            Set<String> windowHandles = getDriver().getWindowHandles();
            List<String> handlesList = new ArrayList<>(windowHandles);
            
            if (index >= 0 && index < handlesList.size()) {
                getDriver().switchTo().window(handlesList.get(index));
                logger.info("Switched to window at index: " + index + ". Current Title: " + getDriver().getTitle());
            } else {
                logger.error("Invalid window index: " + index + ". Only " + handlesList.size() + " windows are currently open.");
            }
        } catch (Exception e) {
            logger.error("Failed to switch to window by index. Reason: " + e.getMessage());
        }
    }
}
