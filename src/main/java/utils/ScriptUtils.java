package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static e2e.manager.DriverManager.getDriver;

public class ScriptUtils {
    public static void clearValue(By locator) {
        WebElement element = getDriver().findElement(locator);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].value = '';", element);
    }
}
