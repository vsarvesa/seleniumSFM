package driver;

import config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

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
            String executionTarget = config.getExecutionTarget();
            String gridUrl = config.getGridUrl();
            
            // Convert relative download path to an absolute path for the browsers
            String downloadPath = new File(config.getDownloadDir()).getAbsolutePath();
            
            WebDriver driver = null;
            AbstractDriverOptions<?> options;

            // 1. Build the Browser Options & Preferences
            switch (browser) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (incognito) firefoxOptions.addArguments("-private");
                    // Firefox Download Preferences
                    firefoxOptions.addPreference("browser.download.folderList", 2);
                    firefoxOptions.addPreference("browser.download.dir", downloadPath);
                    firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", 
                        "application/pdf,application/octet-stream,application/zip,text/csv,application/vnd.ms-excel");
                    options = firefoxOptions;
                    break;
                    
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (incognito) edgeOptions.addArguments("-inPrivate");
                    // Edge Download Preferences
                    HashMap<String, Object> edgePrefs = new HashMap<>();
                    edgePrefs.put("download.default_directory", downloadPath);
                    edgeOptions.setExperimentalOption("prefs", edgePrefs);
                    options = edgeOptions;
                    break;
                    
                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (incognito) chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    // Chrome Download Preferences
                    HashMap<String, Object> chromePrefs = new HashMap<>();
                    chromePrefs.put("download.default_directory", downloadPath);
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    chromeOptions.setExperimentalOption("prefs", chromePrefs);
                    options = chromeOptions;
                    break;
            }

            // 2. Instantiate the Driver (Local vs Grid)
            try {
                if ("grid".equalsIgnoreCase(executionTarget)) {
                    URL url = new URL(gridUrl);
                    driver = new RemoteWebDriver(url, options);
                } else {
                    if (options instanceof FirefoxOptions) {
                        driver = new FirefoxDriver((FirefoxOptions) options);
                    } else if (options instanceof EdgeOptions) {
                        driver = new EdgeDriver((EdgeOptions) options);
                    } else {
                        driver = new ChromeDriver((ChromeOptions) options);
                    }
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Grid URL provided: " + gridUrl, e);
            }
            
            driver.manage().window().maximize();
            setDriver(driver);
        }
    }
}
