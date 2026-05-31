package pages;

import actions.ElementActions;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // Locators without Page Factory
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By successMessage = By.id("flash");

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
