package utils;

import driver.DriverManager;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    @Attachment(value = "Full Page Screenshot", type = "image/png")
    public static byte[] takeScreenshotAndAttachToAllure() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                // Takes full page screenshot by scrolling
                Screenshot screenshot = new AShot()
                        .shootingStrategy(ShootingStrategies.viewportPasting(100))
                        .takeScreenshot(driver);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(screenshot.getImage(), "PNG", baos);
                return baos.toByteArray();
            } catch (Exception e) {
                logger.error("Failed to take full page screenshot for Allure", e);
            }
        }
        return new byte[0];
    }

    public static String takeScreenshotAndSaveLocally(String testName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                // Takes full page screenshot by scrolling
                Screenshot screenshot = new AShot()
                        .shootingStrategy(ShootingStrategies.viewportPasting(100))
                        .takeScreenshot(driver);

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = testName + "_" + timestamp + ".png";
                String destPath = "target/screenshots/" + fileName;

                Path destDir = Paths.get("target/screenshots/");
                if (!Files.exists(destDir)) {
                    Files.createDirectories(destDir);
                }

                ImageIO.write(screenshot.getImage(), "PNG", new File(destPath));
                logger.info("Full page screenshot saved locally at: " + destPath);
                return destPath;
            } catch (IOException e) {
                logger.error("Failed to save full page screenshot locally", e);
            }
        }
        return null;
    }
}
