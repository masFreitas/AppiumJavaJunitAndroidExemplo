package core;

import org.openqa.selenium.By;

import java.net.MalformedURLException;

import static core.DriverFactory.getDriver;

public class BasePage {

    public void writeText(By by, String text) throws MalformedURLException {
        getDriver().findElement(by).sendKeys(text);
    }

    public String getText(By by) throws MalformedURLException {
        return getDriver().findElement(by).getText();
    }

    public void click(By by) throws MalformedURLException {
        getDriver().findElement(by).click();
    }

    public void clickByText(String texto) throws MalformedURLException {
        click(By.xpath("//*[@text='" + texto + "']"));
    }

    public void accessMenu(String text) throws MalformedURLException {
        click(By.xpath("//*[@text='" + text + "']"));
    }

}
