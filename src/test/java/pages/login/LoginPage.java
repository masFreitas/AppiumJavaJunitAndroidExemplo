package pages.login;

import core.BasePage;
import io.appium.java_client.AppiumBy;
import java.net.MalformedURLException;
import pages.login.LoginSelector;

public class LoginPage extends BasePage {

    public void fillUsername (String username) throws MalformedURLException {
        writeText(LoginSelector.USERNAME_INPUT, username);
    }

    public void fillPassword (String password) throws MalformedURLException {
        writeText(LoginSelector.PASSWORD_INPUT, password);
    }

    public void clickLogin () throws MalformedURLException {
        click(LoginSelector.LOGIN_BUTTON);
    }

}
