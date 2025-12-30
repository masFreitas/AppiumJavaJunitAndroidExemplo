package core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

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

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
