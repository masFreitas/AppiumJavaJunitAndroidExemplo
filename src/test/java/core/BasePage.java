package core;

import org.openqa.selenium.By;

import java.net.MalformedURLException;
import java.util.List;
import org.openqa.selenium.WebElement;

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

    public boolean existElementByText(String text) throws MalformedURLException {
        List<WebElement> elements = getDriver().findElements(By.xpath("//*[@text='" + text + "']"));
        return elements.size() > 0;
    }

}
