package pages;

import actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;

public class LoginPage extends BasePage {

    // Static String Templates for Locators
    private static final String USERNAME_ID = "username";
    private static final String PASSWORD_ID = "password";
    private static final String LOGIN_BTN_CSS = "button[type='submit']";
    private static final String SUCCESS_MSG_ID = "flash";
    
    // Example of a Dynamic String Template (ready for MessageFormat)
    private static final String DYNAMIC_ELEMENT_XPATH = "//div[contains(text(), ''{0}'')]";

    // Standard Static By Locators
    private final By usernameField;
    private final By passwordField;
    private final By loginButton;
    private final By successMessage;

    // Constructor to initialize static locators
    public LoginPage() {
        this.usernameField = By.id(USERNAME_ID);
        this.passwordField = By.id(PASSWORD_ID);
        this.loginButton = By.cssSelector(LOGIN_BTN_CSS);
        this.successMessage = By.id(SUCCESS_MSG_ID);
    }
    
    /**
     * Example of a Dynamic Locator Method!
     * Instead of assigning it in the constructor, we build the 'By' object exactly when we need it.
     */
    public void clickDynamicElement(String dynamicText) {
        String formattedXpath = java.text.MessageFormat.format(DYNAMIC_ELEMENT_XPATH, dynamicText);
        By dynamicLocator = By.xpath(formattedXpath);
        
        ElementActions.click(dynamicLocator, "Dynamic Element: " + dynamicText);
    }

    // Actions
    public LoginPage enterUsername(String username) {
        ElementActions.type(usernameField, username, "Username Field");
        return this;
        
    }

    public LoginPage enterPassword(String password) {
        ElementActions.type(passwordField, password, "Password Field");
        return this;
    }

    public LoginPage clickLogin() {
        ElementActions.click(loginButton, "Login Button");
        return this;
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }

    // New method to check if the login failed
    public boolean isLoginErrorDisplayed() {
        try {
            // Check if the flash error message is present
            return driver.findElement(By.id("flash")).getText().contains("invalid");
        } catch (Exception e) {
            return false;
        }
    }
}
