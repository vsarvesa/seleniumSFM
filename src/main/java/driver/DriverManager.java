package driver;

import config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
    }

    public static void quitDriver() {
        if (threadLocalDriver.get() != null) {
            threadLocalDriver.get().quit();
            threadLocalDriver.remove();
        }
    }

    public static void initDriver() {
        if (getDriver() == null) {
            ConfigReader config = ConfigReader.getInstance();
            String browser = config.getBrowser();
            boolean incognito = config.isIncognito();
            
            WebDriver driver;
            
            switch (browser) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (incognito) firefoxOptions.addArguments("-private");
                    // Selenium Manager will automatically download the correct driver binary
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                    
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (incognito) edgeOptions.addArguments("-inPrivate");
                    driver = new EdgeDriver(edgeOptions);
                    break;
                    
                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (incognito) chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(chromeOptions);
                    break;
            }
            
            driver.manage().window().maximize();
            setDriver(driver);
        }
    }
}
