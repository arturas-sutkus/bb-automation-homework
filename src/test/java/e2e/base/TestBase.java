package e2e.base;

import e2e.constants.pageUrls;
import e2e.helper.ScreenshotUtils;
import e2e.manager.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    protected WebDriver driver;
    private DriverFactory driverFactory;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driverFactory = new DriverFactory();
        driver = driverFactory.createDriver();
        driver.get(pageUrls.BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driverFactory != null) {
            if (ITestResult.FAILURE == result.getStatus()) {
                ScreenshotUtils.captureScreenshot(result);
            }
            driverFactory.quitDriver();
        } else {
            System.out.println("DriverFactory is not initialized.");
        }
    }
}
