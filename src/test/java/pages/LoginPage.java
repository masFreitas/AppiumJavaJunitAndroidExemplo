package pages;

import core.BasePage;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import java.net.MalformedURLException;

public class LoginPage extends BasePage {

    public void fillUsername (String username) throws MalformedURLException {
        writeText(AppiumBy.accessibilityId("username"), username);
    }

    public void fillPassword (String password) throws MalformedURLException {
        writeText(AppiumBy.accessibilityId("password"), password);
    }

    public void clickLogin () throws MalformedURLException {
        click(AppiumBy.accessibilityId("loginBtn"));
    }

}
