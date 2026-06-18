package utils;

import driver.DriverManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    // Thread-safe counter for uniquely identifying screenshots across parallel runs
    private static final AtomicInteger screenshotCounter = new AtomicInteger(0);

    public static void takeScreenshotAndAttachToAllure() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                int screenshotId = screenshotCounter.incrementAndGet();
                logger.info("Initiating Screenshot #" + screenshotId + " (This may take a moment due to 2-second scroll delays)...");
                
                // 1. Take full page scrolling screenshot using AShot with a 2-second delay between scrolls
                Screenshot screenshot = new AShot()
                        .shootingStrategy(ShootingStrategies.viewportPasting(2000))
                        .takeScreenshot(driver);
                        
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(screenshot.getImage(), "PNG", baos);
                byte[] screenshotBytes = baos.toByteArray();
                
                // 2. Programmatically attach to Allure
                Allure.addAttachment("Assertion Failure Screenshot #" + screenshotId, "image/png", 
                    new ByteArrayInputStream(screenshotBytes), ".png");
                    
                // 3. Simultaneously save it locally so you can easily view it in your IDE
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String destPath = "target/screenshots/Failure_Id" + screenshotId + "_" + timestamp + ".png";
                
                Path destDir = Paths.get("target/screenshots/");
                if (!Files.exists(destDir)) {
                    Files.createDirectories(destDir);
                }
                Files.write(Paths.get(destPath), screenshotBytes);
                logger.info("Screenshot #" + screenshotId + " successfully saved locally at: " + destPath);
                    
            } catch (Exception e) {
                logger.error("Failed to take screenshot", e);
            }
        }
    }

    public static String takeScreenshotAndSaveLocally(String testName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                int screenshotId = screenshotCounter.incrementAndGet();
                logger.info("Initiating Screenshot #" + screenshotId + " for test: " + testName + "...");

                // Takes full page screenshot by scrolling with 2 second delay for rendering
                Screenshot screenshot = new AShot()
                        .shootingStrategy(ShootingStrategies.viewportPasting(2000))
                        .takeScreenshot(driver);

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = testName + "_Id" + screenshotId + "_" + timestamp + ".png";
                String destPath = "target/screenshots/" + fileName;

                Path destDir = Paths.get("target/screenshots/");
                if (!Files.exists(destDir)) {
                    Files.createDirectories(destDir);
                }

                ImageIO.write(screenshot.getImage(), "PNG", new File(destPath));
                logger.info("Screenshot #" + screenshotId + " successfully saved locally at: " + destPath);
                return destPath;
            } catch (IOException e) {
                logger.error("Failed to save full page screenshot locally", e);
            }
        }
        return null;
    }
}
