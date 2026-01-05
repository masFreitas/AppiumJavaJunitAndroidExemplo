package core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DriverFactory {

    protected static AndroidDriver driver;

    public static AndroidDriver getDriver() throws MalformedURLException {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    public static void createDriver() throws MalformedURLException {
        BaseOptions options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("automationName", "UIAutomator2")
                .amend("deviceName", "RX8R808B8TL")
                // Se for APK local:
                // .amend("app", System.getProperty("user.dir") + "/apps/seuapp.apk")
                // Se app instalado (ajuste conforme seu app):
                .amend("appPackage", "com.appiumpro.the_app")
                .amend("appActivity", "com.appiumpro.the_app.MainActivity")
                .amend("noReset", false);

        URL serverUrl = new URL("http://127.0.0.1:4723");
        driver = new AndroidDriver(serverUrl, options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public static byte[] takeScreenshotBytes() {
        try {
            if (driver == null) return new byte[0];
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    /** Opcional: também salva em target/screenshot. */
    public static Path saveScreenshotToFile(String testName, byte[] pngBytes) {
        try {
            if (pngBytes == null || pngBytes.length == 0) return null;

            Path dir = Paths.get("target", "screenshot");
            Files.createDirectories(dir);

            String safeTestName = sanitizeFileName(testName);
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
            Path out = dir.resolve(safeTestName + "_" + ts + ".png");

            Files.write(out, pngBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return out;
        } catch (Exception e) {
            return null;
        }
    }

    private static String sanitizeFileName(String name) {
        if (name == null || name.isBlank()) return "test";
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
