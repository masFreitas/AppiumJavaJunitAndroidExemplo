package pages.clipboard;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ClipboardSelector {

    public static final By MESSAGE_INPUT = AppiumBy.accessibilityId("messageInput");
    public static final By SET_CLIPBOARD_BUTTON = AppiumBy.accessibilityId("setClipboardText");
    public static final By REFRESH_CLIPBOARD_BUTTON = AppiumBy.accessibilityId("refreshClipboardText");


}
