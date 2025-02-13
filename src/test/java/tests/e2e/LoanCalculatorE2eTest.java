package tests.e2e;

import api.actions.LoanCalculate;
import e2e.base.TestBase;
import e2e.pages.modal.LoanCalculator;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

public class LoanCalculatorE2eTest extends TestBase {

    private LoanCalculator loanCalculatorModal;

    @BeforeMethod
    public void setUpLoanCalculatorModal() {
        loanCalculatorModal = new LoanCalculator(driver);
        loanCalculatorModal.navigateToLoanCalculatorModal();
    }

    @Story("e2e - default values")
    @Test(description = "Verify modals default state values")
    public void testDefaultLoanCalculatorStateValues() {
        String loanAmount = "5000";
        String period = "60";
        String calculation = "€123.92";
        loanCalculatorModal.waitUntilLoaderNotVisible();
        assertEquals(loanAmount, loanCalculatorModal.getLoanAmount());
        assertEquals(period, loanCalculatorModal.getPeriod());
        assertEquals("Calculated result changed.",
                calculation, loanCalculatorModal.getCalculatedResult());
    }

    @Story("e2e - dialog submit & close")
    @Test(description = "Verify submit & close buttons")
    public void testLoanCalculatorModalSubmitClose() {
        loanCalculatorModal.clickSubmitButton();
        assertFalse(loanCalculatorModal.isModalDisplayed());
        loanCalculatorModal.navigateToLoanCalculatorModal();
        loanCalculatorModal.closeModal();
        assertFalse(loanCalculatorModal.isModalDisplayed());
    }

    @Story("e2e - invalid values")
    @Test(dataProvider = "invalidData", description = "Verify handling of invalid values")
    public void testLoanCalculatorNegativeValues(String loanAmount, String period, String expectedLoanAmount, String expectedPeriod) {
        loanCalculatorModal.clearLoanAmount();
        loanCalculatorModal.enterLoanAmount(loanAmount);
        loanCalculatorModal.clearPeriod();
        loanCalculatorModal.enterPeriod(period);
        loanCalculatorModal.clickAwayOnModal();
        loanCalculatorModal.waitUntilLoaderNotVisible();
        assertEquals(String.format("Loan Amount Mismatch | Input: %s | Expected: %s | Actual: %s",
                loanAmount, expectedLoanAmount, loanCalculatorModal.getLoanAmount()), expectedLoanAmount, loanCalculatorModal.getLoanAmount());
        assertEquals(String.format("Period Mismatch | Input: %s | Expected: %s | Actual: %s",
                period, expectedPeriod, loanCalculatorModal.getPeriod()), expectedPeriod, loanCalculatorModal.getPeriod());
    }

    @DataProvider(name = "invalidData")
    public Object[][] getInvalidData() {
        return new Object[][]{
                {"-1", "-1", "500", "6"},
                {"100000", "100", "30000", "100"},
                {"1", "1", "500", "6"},
                {"a", "b", "5000", "60"}
        };
    }

    @Story("e2e - calculation")
    @Test(description = "Verify sliders & calculation result mapping with api")
    public void testLoanCalculatorSlidersCalculation() {
        loanCalculatorModal.dragLoanAmountSlider(50, 0);
        loanCalculatorModal.clickAwayOnModal();
        loanCalculatorModal.dragPeriodSlider(-50, 0);
        loanCalculatorModal.clickAwayOnModal();
        Response response = LoanCalculate.getLoanCalculateResponse(
                3.99,
                Double.parseDouble(loanCalculatorModal.getLoanAmount()),
                100,
                "EUR",
                15.1,
                Integer.parseInt(loanCalculatorModal.getPeriod()),
                15,
                "SMALL_LOAN_EE01"
        );
        loanCalculatorModal.waitUntilLoaderNotVisible();
        Double apiCalculation = LoanCalculate.getMonthlyPayment(response);
        Double feCalculation = Double.parseDouble(loanCalculatorModal.getCalculatedResult().replace("€", ""));
        assertEquals("API calculation does not match",
                apiCalculation, feCalculation);
    }
}
