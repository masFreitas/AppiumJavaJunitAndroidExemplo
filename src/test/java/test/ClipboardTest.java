package test;

import core.BaseTest;
import org.junit.jupiter.api.Test;
import pages.ClipboardPage;

import java.net.MalformedURLException;

public class ClipboardTest extends BaseTest {

    ClipboardPage clipboardPage = new ClipboardPage();

    @Test
    public void addClipboardText () throws MalformedURLException {
        accessClipboardDemoMenu();
        clipboardPage.enterClipboardText("texto do clipboard");
        clipboardPage.clickSetClipboardText();
    }

}
