package tests;

import base.BaseTest;
import com.github.javafaker.Faker;
import listeners.RetryAnalyzer;
import listeners.TestListener;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login with dynamic user creation on failure")
    public void testValidLoginWithCreationFallback() {
        LoginPage loginPage = new LoginPage();
        
        // Each thread assigns itself a unique username (e.g. ThreadUser_12)
        String threadUser = "ThreadUser_" + Thread.currentThread().getId();
        
        // 1. Initial Attempt: Try to login with the user
        loginPage.enterUsername(threadUser)
                 .enterPassword(threadUser) // User and Pass are the same
                 .clickLogin();

        // 2. The Check: If the login screen displays user does not exist/password does not match
        if (loginPage.isLoginErrorDisplayed()) {
            
            // 3. The Creation: Trigger the synchronized admin block to create this user
            utils.UserManager.createMissingUserViaAdmin(threadUser);
            
            // 4. The Retry: Now that the user exists, try logging in one more time
            loginPage.enterUsername(threadUser)
                     .enterPassword(threadUser)
                     .clickLogin();
        }
        
        // Finally, assert that we successfully logged in
        String message = loginPage.getSuccessMessage();
        softAssert.assertTrue(message.contains("You logged into a secure area!"), "Login success message did not match.");
    }
    
    @Test(description = "Verify failed login triggers retry and screenshot")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage();
        Faker faker = new Faker();
        
        loginPage.enterUsername(faker.name().username())
                 .enterPassword(faker.internet().password())
                 .clickLogin();

        String message = loginPage.getSuccessMessage();
        
        // This will purposely fail to demonstrate retry and screenshot mechanisms
        softAssert.assertTrue(message.contains("You logged into a secure area!"), "Intentional failure for demonstration.");
    }
}
