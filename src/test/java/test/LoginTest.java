package test;

import core.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.login.LoginPage;

import java.net.MalformedURLException;

public class LoginTest extends BaseTest {

    private LoginPage loginPage = new LoginPage();

    @Test
    public void loginSuccessfully() throws MalformedURLException {
        accessLoginMenu();
        loginPage.fillUsername("alice");
        loginPage.fillPassword("mypassword");
        loginPage.clickLogin();
        Assertions.assertTrue(loginPage.existElementByText("Secret Area"));
    }

    @Test
    public void loginWithInvalidCredentials() throws MalformedURLException {
        accessLoginMenu();
        loginPage.fillUsername("invalid-username");
        loginPage.fillPassword("invalid-password");
        loginPage.clickLogin();
        Assertions.assertTrue(loginPage.existElementByText("Invalid login credentials, please try again"));
    }
}
