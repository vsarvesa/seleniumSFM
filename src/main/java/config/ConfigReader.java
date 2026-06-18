package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static ConfigReader instance;

    private ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/selenium.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load selenium.properties file: " + e.getMessage());
        }
    }

    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getUrl() {
        String sysUrl = System.getProperty("url");
        return sysUrl != null ? sysUrl : properties.getProperty("url");
    }

    public String getBrowser() {
        String sysBrowser = System.getProperty("browser");
        return (sysBrowser != null ? sysBrowser : properties.getProperty("browser", "chrome")).toLowerCase();
    }

    public String getBrowserVersion() {
        String sysVersion = System.getProperty("browser.version");
        return sysVersion != null ? sysVersion : properties.getProperty("browser.version", "");
    }

    public boolean isIncognito() {
        String sysIncognito = System.getProperty("incognito");
        return Boolean.parseBoolean(sysIncognito != null ? sysIncognito : properties.getProperty("incognito", "false"));
    }

    public String getAdminUsername() {
        String sysAdminUser = System.getProperty("admin.username");
        return sysAdminUser != null ? sysAdminUser : properties.getProperty("admin.username", "admin");
    }

    public String getAdminPassword() {
        String sysAdminPass = System.getProperty("admin.password");
        return sysAdminPass != null ? sysAdminPass : properties.getProperty("admin.password", "admin123");
    }

    public int getExplicitWait() {
        String sysWait = System.getProperty("explicit.wait");
        return Integer.parseInt(sysWait != null ? sysWait : properties.getProperty("explicit.wait", "10"));
    }

    public String getExecutionTarget() {
        String sysTarget = System.getProperty("execution.target");
        return (sysTarget != null ? sysTarget : properties.getProperty("execution.target", "local")).toLowerCase();
    }

    public String getGridUrl() {
        String sysGridUrl = System.getProperty("grid.url");
        return sysGridUrl != null ? sysGridUrl : properties.getProperty("grid.url", "http://localhost:4444/wd/hub");
    }

    public String getDownloadDir() {
        String sysDownloadDir = System.getProperty("download.dir");
        return sysDownloadDir != null ? sysDownloadDir : properties.getProperty("download.dir", "target/downloads");
    }
}
