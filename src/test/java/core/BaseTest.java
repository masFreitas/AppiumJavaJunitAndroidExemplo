package core;

import org.junit.jupiter.api.AfterEach;

import java.net.MalformedURLException;

public class BaseTest {

    private BasePage basePage = new BasePage();

    @AfterEach
    public void closeApp() {
        DriverFactory.tearDown();
    }

    public void accessEchoBoxMenu() throws MalformedURLException {
        basePage.accessMenu("Echo Box");
    }

    public void accessLoginMenu() throws MalformedURLException {
        basePage.accessMenu("Login Screen");
    }

    public void accessClipboardDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Clipboard Demo");
    }

    public void accessWebviewDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Webview Demo");
    }

    public void accessDualWebviewDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Dual Webview Demo");
    }

    public void accessListDemoMenu() throws MalformedURLException {
        basePage.accessMenu("List Demo");
    }

    public void accessGeolocationDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Geolocation Demo");
    }

    public void accessPickerDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Picker Demo");
    }

    public void accessVerifyPhoneNumberMenu() throws MalformedURLException {
        basePage.accessMenu("Verify Phone Number");
    }

}
