package e2e.manager;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    public WebDriver createDriver() {
        return DriverManager.getDriver();
    }

    public void quitDriver() {
        DriverManager.quitDriver();
    }
}
