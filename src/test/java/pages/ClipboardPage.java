package pages;

import core.BasePage;
import io.appium.java_client.AppiumBy;

import java.net.MalformedURLException;

public class ClipboardPage extends BasePage {

    public void enterClipboardText (String text) throws MalformedURLException {
        writeText(AppiumBy.accessibilityId("messageInput"), text);
    }

    public void clickSetClipboardText () throws MalformedURLException {
        click(AppiumBy.accessibilityId("setClipboardText"));
    }

}
