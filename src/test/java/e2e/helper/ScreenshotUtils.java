package e2e.helper;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import utils.ConfigUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static e2e.manager.DriverManager.getDriver;

public class ScreenshotUtils {
    public static void captureScreenshot(ITestResult result) {
        String failedName = generateName(result);
        File screenshotFolder = new File(getFolderPath());
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }
        File screenshotFile = new File(screenshotFolder, failedName);
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, screenshotFile);
            attachScreenshotToAllure(screenshotFile, failedName);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

    private static void attachScreenshotToAllure(File screenshotFile, String name) {
        try (FileInputStream fis = new FileInputStream(screenshotFile)) {
            byte[] screenshotData = new byte[(int) screenshotFile.length()];
            fis.read(screenshotData);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshotData), "png");
        } catch (IOException e) {
            System.err.println("Failed to attach screenshot to Allure report: " + e.getMessage());
        }
    }

    private static String generateName(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        return testName + "_" + timestamp + ".png";
    }

    private static String getFolderPath(){
        String baseDir = System.getProperty("user.dir");
        return baseDir+ ConfigUtils.getProperty("SCREENSHOT_FOLDER_PATH");
    }
}
