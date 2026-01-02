package pages.login;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class LoginSelector {

    public static final By USERNAME_INPUT = AppiumBy.accessibilityId("username");
    public static final By PASSWORD_INPUT = AppiumBy.accessibilityId("password");
    public static final By LOGIN_BUTTON = AppiumBy.accessibilityId("loginBtn");

}
