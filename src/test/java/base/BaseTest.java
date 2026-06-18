package base;

import config.ConfigReader;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.CustomHardAssert;
import utils.CustomSoftAssert;

public class BaseTest {

    protected CustomSoftAssert softAssert;
    protected CustomHardAssert hardAssert;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.initDriver();
        WebDriver driver = DriverManager.getDriver();
        driver.get(ConfigReader.getInstance().getUrl());
        softAssert = new CustomSoftAssert();
        hardAssert = new CustomHardAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // Assert all soft assertions collected during the test
        try {
            softAssert.assertAll();
        } finally {
            DriverManager.quitDriver();
        }
    }
}
