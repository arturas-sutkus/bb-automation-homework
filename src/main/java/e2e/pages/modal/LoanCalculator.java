package e2e.pages.modal;

import e2e.constants.pageUrls;
import e2e.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ScriptUtils;

public class LoanCalculator extends BasePage {
    private final By modalDialog = By.xpath("//dialog[@open]");
    private final By modalHeader = By.xpath("//*[@class='bb-calculator-modal__heading']");
    private final By loanAmountSlider = By.xpath("//*[@id='header-calculator-amount']//*[@class='vue-slider-dot']");
    private final By loanAmountInput = By.xpath("//input[@name='header-calculator-amount']");
    private final By periodSlider = By.xpath("//*[@id='header-calculator-period']//*[@class='vue-slider-dot']");
    private final By periodInput = By.xpath("//input[@name='header-calculator-period']");
    private final By calculatedResult = By.xpath("//p[contains(@class, 'value__value')]");
    private final By submitButton = By.xpath("//button[contains(@class, 'bb-calculator-modal__submit-button')]");
    private final By closeModalButton = By.xpath("//button[contains(@class, 'bb-modal__close')]");
    private final By calculationLoader = By.xpath("//div[contains(@class, 'result-value--loader')]");

    String VALUE_ATTRIBUTE = "aria-valuetext";

    public LoanCalculator(WebDriver driver) {
        super(driver);
    }

    public void navigateToLoanCalculatorModal() {
        driver.get(pageUrls.LOAN_CALCULATOR_URL);
        waitUntilVisible(modalDialog);
    }

    public boolean isModalDisplayed() {
        return !findElement(modalDialog).isDisplayed();
    }

    public void enterLoanAmount(String amount) {
        sendKeys(loanAmountInput, amount);
    }

    public void enterPeriod(String period) {
        sendKeys(periodInput, period);
    }

    public void clearLoanAmount() {
        ScriptUtils.clearValue(loanAmountInput);
    }

    public void clearPeriod() {
        ScriptUtils.clearValue(periodInput);
    }

    public void clickSubmitButton() {
        clickElement(submitButton);
    }

    public void closeModal() {
        clickElement(closeModalButton);
    }

    public String getLoanAmount() {
        return getAttribute(loanAmountSlider, VALUE_ATTRIBUTE);
    }

    public String getPeriod() {
        return getAttribute(periodSlider, VALUE_ATTRIBUTE);
    }

    public String getCalculatedResult() {
        return getText(calculatedResult);
    }

    public void clickAwayOnModal() {
        clickElement(modalHeader);
    }

    public void waitUntilLoaderNotVisible() {
        waitUntilNotVisible(calculationLoader);
    }

    public void dragLoanAmountSlider(int xOffset, int yOffset) {
        dragElementByOffset(loanAmountSlider, xOffset, yOffset);
    }

    public void dragPeriodSlider(int xOffset, int yOffset) {
        dragElementByOffset(periodSlider, xOffset, yOffset);
    }
}
