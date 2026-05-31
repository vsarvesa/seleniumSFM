package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserManager {

    private static final Logger logger = LogManager.getLogger(UserManager.class);

    /**
     * Synchronized method to ensure that if multiple threads fail login at the same time,
     * only ONE thread logs into the Admin portal at a time to create their specific user.
     * 
     * @param username The specific username (and password) to be created by the Admin.
     */
    public static synchronized void createMissingUserViaAdmin(String username) {
        logger.info("Thread " + Thread.currentThread().getId() + " is locked in Admin Block to create missing user: " + username);
        
        // TODO: Add UI steps to open a new tab, or navigate to Admin Login
        // AdminLogin.login("admin", "adminPass");
        
        // TODO: Add UI steps to create the user
        // AdminDashboard.createUser(username, username); // User and Pass are the same
        
        logger.info("Successfully created missing user: " + username);
        
        // TODO: Add UI steps to log out of Admin and return to the main login screen
        // AdminDashboard.logout();
    }
}
