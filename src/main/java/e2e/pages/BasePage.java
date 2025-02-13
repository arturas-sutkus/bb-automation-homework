package e2e.pages;

import org.openqa.selenium.interactions.Actions;
import utils.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    private final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(ConfigUtils.getProperty("DEFAULT_WAIT_TIME"))));
    }

    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    public void clickElement(By locator) {
        findElement(locator).click();
    }

    public void sendKeys(By locator, String text) {
        findElement(locator).sendKeys(text);
    }

    public String getText(By locator) {
        return findElement(locator).getText();
    }

    public void waitUntilVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitUntilNotVisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public String getAttribute(By locator, String attribute) {
        return driver.findElement(locator).getDomAttribute(attribute);
    }

    public void dragElementByOffset(By locator, int xOffset, int yOffset) {
        WebElement slider = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.clickAndHold(slider).moveByOffset(xOffset, yOffset).release().perform();
    }
}