package pages.clipboard;

import core.BasePage;
import io.appium.java_client.AppiumBy;
import pages.clipboard.ClipboardSelector;

import java.net.MalformedURLException;

public class ClipboardPage extends BasePage {

    public void enterClipboardText (String text) throws MalformedURLException {
        writeText(ClipboardSelector.MESSAGE_INPUT, text);
    }

    public void clickSetClipboardText () throws MalformedURLException {
        click(ClipboardSelector.SET_CLIPBOARD_BUTTON);
    }

    public void clickRefreshClipboardText () throws MalformedURLException {
        click(ClipboardSelector.REFRESH_CLIPBOARD_BUTTON);
    }

}
