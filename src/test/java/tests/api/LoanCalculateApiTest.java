package tests.api;

import api.actions.LoanCalculate;
import api.helpers.JsonHelper;
import io.qameta.allure.Story;
import org.json.JSONObject;
import utils.JsonFileUtils;
import io.restassured.response.Response;
import api.models.CalculateRequest;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.services.CalculateService;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoanCalculateApiTest {
    private static final double ADMINISTRATION_FEE = 3.99;
    private static final int MONTHLY_PAYMENT_DAY = 15;
    private static final double INTEREST_RATE = 15.1;
    private static final String CURRENCY = "EUR";
    private static final String PRODUCT_TYPE = "SMALL_LOAN_EE01";

    @Story("api - calculation")
    @Test(description = "Default payload calculation")
    public void testDefaultPayloadCalculation() {
        Response response = LoanCalculate.getLoanCalculateResponse(
                ADMINISTRATION_FEE,
                5000,
                100,
                CURRENCY,
                INTEREST_RATE,
                60,
                MONTHLY_PAYMENT_DAY,
                PRODUCT_TYPE
        );
        assertThat(LoanCalculate.getTotalRepayableAmount(response), is(7435.18)); //could use closeTo(value, 5) because every day value changes
        assertThat(LoanCalculate.getMonthlyPayment(response), is(123.92)); //could use closeTo(value, 5) because every day value changes
        assertThat(LoanCalculate.getApr(response), is(19.25)); //could use closeTo(value, 5) because every day value changes
    }

    @Story("api - calculation")
    @Test(description = "Minimum payload calculation")
    public void testMinLoanCalculation() {
        Response response = LoanCalculate.getLoanCalculateResponse(
                ADMINISTRATION_FEE,
                500,
                45,
                CURRENCY,
                INTEREST_RATE,
                6,
                MONTHLY_PAYMENT_DAY,
                PRODUCT_TYPE
        );
        assertThat(LoanCalculate.getTotalRepayableAmount(response), is(547.05)); //could use closeTo(value, 5) because every day value changes
        assertThat(LoanCalculate.getMonthlyPayment(response), is(91.18)); //could use closeTo(value, 5) because every day value changes
        assertThat(LoanCalculate.getApr(response), is(87.82)); //could use closeTo(value, 5) because every day value changes
    }

    @Story("api - calculation")
    @Test(description = "Maximum payload calculation")
    public void testMaxLoanCalculation() {
        Response response = LoanCalculate.getLoanCalculateResponse(
                ADMINISTRATION_FEE,
                30000,
                600,
                CURRENCY,
                INTEREST_RATE,
                120,
                MONTHLY_PAYMENT_DAY,
                PRODUCT_TYPE
        );
        assertThat(LoanCalculate.getTotalRepayableAmount(response), is(59334.69)); //could use closeTo(value, 5) because every day value changes
        assertThat(LoanCalculate.getMonthlyPayment(response), is(494.46)); //could use closeTo(value, 5) because every day value changes
        assertThat(LoanCalculate.getApr(response), is(17.3)); //could use closeTo(value, 5) because every day value changes
    }

    @Story("api - invalid values")
    @Test(dataProvider = "invalidData", description = "Invalid payload calculation")
    public void testInvalidPayloadValueScenarios(Double fee,
                                                 Double amount,
                                                 Double conclusionFee,
                                                 String currency,
                                                 Double interest,
                                                 Integer maturity,
                                                 Integer day,
                                                 String productType
    ) {
        CalculateRequest request = new CalculateRequest(currency, productType, maturity, fee, conclusionFee, amount, day, interest);
        Response response = CalculateService.calculateLoan(request);
        assertThat(
                String.format("Expected status code 400 with params fee=%.2f, amount=%.2f, conclusionFee=%.2f, currency=%s, interest=%.2f, maturity=%d, day=%d, productType=%s. " +
                                "\n Actual status code: %d",
                        fee, amount, conclusionFee, currency, interest, maturity, day, productType, response.getStatusCode()),
                response.getStatusCode() == 400
        );
    }

    @DataProvider(name = "invalidData")
    public Object[][] getInvalidData() {
        return LoanCalculate.getInvalidValues();
    }

    @Story("api - assert response fields")
    @Test(description = "Assert response fields with expected json file")
    public void testCalculationResponseFields() throws JSONException {
        Response response = LoanCalculate.getLoanCalculateResponse(
                ADMINISTRATION_FEE,
                7650,
                153,
                CURRENCY,
                INTEREST_RATE,
                52,
                MONTHLY_PAYMENT_DAY,
                PRODUCT_TYPE
        );
        String responseJson = response.getBody().asString();
        JSONObject responseJsonObject = new JSONObject(responseJson);
        String expectedJson = JsonFileUtils.getJsonResponseFile("loanCalculation.json");
        JSONObject expectedJsonObject = new JSONObject(expectedJson);
        JsonHelper.removeFieldValues(responseJsonObject);
        JsonHelper.removeFieldValues(expectedJsonObject);
        JSONAssert.assertEquals(expectedJsonObject, responseJsonObject, false);
    }

    @Story("api - verify value rules")
    @Test(description = "Apply rules for response values")
    public void testCalculationResponseValuesWithRules() {
        Response response = LoanCalculate.getLoanCalculateResponse(
                ADMINISTRATION_FEE,
                5000,
                100,
                CURRENCY,
                INTEREST_RATE,
                60,
                MONTHLY_PAYMENT_DAY,
                PRODUCT_TYPE
        );
        Map<String, Double> values = Map.of(
                "Total Repayable Amount", LoanCalculate.getTotalRepayableAmount(response),
                "Monthly Payment", LoanCalculate.getMonthlyPayment(response),
                "APR", LoanCalculate.getApr(response)
        );
        values.forEach((name, value) -> {
            assertThat(name + " should be a valid number, not negative or zero", value, allOf(
                    notNullValue(),
                    greaterThan(0.0),
                    not(instanceOf(String.class))
            ));
        });
    }
}
