package core;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import utils.AllureAttachments;
import org.junit.jupiter.api.TestInfo;


import java.net.MalformedURLException;

public class BaseTest {

    private BasePage basePage = new BasePage();

    @AfterEach
    public void closeApp(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();

        // 1) captura bytes
        byte[] png = DriverFactory.takeScreenshotBytes();

        // 2) anexa no Allure
        AllureAttachments.attachPng("Screenshot - " + testName, png);

        // 3) opcional: salva em target/screenshot
        DriverFactory.saveScreenshotToFile(testName, png);

        // 4) encerra driver
        DriverFactory.tearDown();
    }

    @Step("Acessar menu de Echo Box")
    public void accessEchoBoxMenu() throws MalformedURLException {
        basePage.accessMenu("Echo Box");
    }

    @Step("Acessar menu de Login")
    public void accessLoginMenu() throws MalformedURLException {
        basePage.accessMenu("Login Screen");
    }

    @Step("Acessar menu de Clipboard")
    public void accessClipboardDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Clipboard Demo");
    }

    @Step("Acessar menu de Webview Demo")
    public void accessWebviewDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Webview Demo");
    }

    @Step("Acessar menu de Dual Webview Demo")
    public void accessDualWebviewDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Dual Webview Demo");
    }

    @Step("Acessar menu de List Demo")
    public void accessListDemoMenu() throws MalformedURLException {
        basePage.accessMenu("List Demo");
    }

    @Step("Acessar menu de Geolocation Demo")
    public void accessGeolocationDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Geolocation Demo");
    }

    @Step("Acessar menu de Picker Demo")
    public void accessPickerDemoMenu() throws MalformedURLException {
        basePage.accessMenu("Picker Demo");
    }

    @Step("Acessar menu de Verify Phone Number")
    public void accessVerifyPhoneNumberMenu() throws MalformedURLException {
        basePage.accessMenu("Verify Phone Number");
    }

}
