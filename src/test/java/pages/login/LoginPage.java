package pages.login;

import core.BasePage;
import java.net.MalformedURLException;

import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    @Step("Preencher usuário: {username}")
    public void fillUsername (String username) throws MalformedURLException {
        writeText(LoginSelector.USERNAME_INPUT, username);
    }

    @Step("Preencher senha: {password}")
    public void fillPassword (String password) throws MalformedURLException {
        writeText(LoginSelector.PASSWORD_INPUT, password);
    }

    @Step("Clicar no botão Login")
    public void clickLogin () throws MalformedURLException {
        click(LoginSelector.LOGIN_BUTTON);
    }

}
