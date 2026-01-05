package pages.clipboard;

import core.BasePage;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import pages.clipboard.ClipboardSelector;

import java.net.MalformedURLException;

public class ClipboardPage extends BasePage {

    @Step("Preencher texto do CLipboard: {text}")
    public void enterClipboardText (String text) throws MalformedURLException {
        writeText(ClipboardSelector.MESSAGE_INPUT, text);
    }

    @Step("Clicar no botão Set Clipboard Text")
    public void clickSetClipboardText () throws MalformedURLException {
        click(ClipboardSelector.SET_CLIPBOARD_BUTTON);
    }

    @Step("Clicar no botão Refresh Clipboard Text")
    public void clickRefreshClipboardText () throws MalformedURLException {
        click(ClipboardSelector.REFRESH_CLIPBOARD_BUTTON);
    }

}
