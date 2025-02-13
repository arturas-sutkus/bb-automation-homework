package e2e.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigUtils;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            synchronized (DriverManager.class) {
                if (driver.get() == null) {
                    ChromeOptions options = new ChromeOptions();
                    if (Boolean.parseBoolean(ConfigUtils.getProperty("HEADLESS"))) {
                        options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920x1080", "--remote-debugging-port=" + getRandomPort() + "");
                    }
                    driver.set(new ChromeDriver(options));
                }
            }
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    private static int getRandomPort() {
        return 9222 + (int) (Math.random() * 1000);
    }
}