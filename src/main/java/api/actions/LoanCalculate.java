package api.actions;

import api.models.CalculateRequest;
import api.services.CalculateService;
import io.restassured.response.Response;
import utils.JsonFileUtils;

import java.util.List;

public class LoanCalculate {
    public static Response getLoanCalculateResponse(double administrationFee, double amount, double conclusionFee,
                                                    String currency, double interestRate, int maturity,
                                                    int monthlyPaymentDay, String productType) {
        CalculateRequest requestBody = new CalculateRequest(currency, productType, maturity, administrationFee,
                conclusionFee, amount, monthlyPaymentDay, interestRate);
        return CalculateService.calculateLoan(requestBody);
    }

    public static double getMonthlyPayment(Response response) {
        return response.jsonPath().getDouble("monthlyPayment");
    }

    public static double getTotalRepayableAmount(Response response) {
        return response.jsonPath().getDouble("totalRepayableAmount");
    }

    public static double getApr(Response response) {
        return response.jsonPath().getDouble("apr");
    }

    public static Object[][] getInvalidValues() {
        List<CalculateRequest> testDataList = JsonFileUtils.loadTestDataFromFile("calculationInvalidValues.json");
        Object[][] data = new Object[testDataList.size()][8];
        for (int i = 0; i < testDataList.size(); i++) {
            CalculateRequest request = testDataList.get(i);
            data[i] = new Object[]{
                    request.getAdministrationFee(),
                    request.getAmount(),
                    request.getConclusionFee(),
                    request.getCurrency(),
                    request.getInterestRate(),
                    request.getMaturity(),
                    request.getMonthlyPaymentDay(),
                    request.getProductType()
            };
        }
        return data;
    }
}
