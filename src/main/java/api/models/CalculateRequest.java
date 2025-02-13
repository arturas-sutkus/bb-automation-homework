package api.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculateRequest {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("productType")
    private String productType;

    @JsonProperty("maturity")
    private Integer maturity;

    @JsonProperty("administrationFee")
    private Double administrationFee;

    @JsonProperty("conclusionFee")
    private Double conclusionFee;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("monthlyPaymentDay")
    private Integer monthlyPaymentDay;

    @JsonProperty("interestRate")
    private Double interestRate;

    @JsonCreator
    public CalculateRequest(
            @JsonProperty("currency") String currency,
            @JsonProperty("productType") String productType,
            @JsonProperty("maturity") Integer maturity,
            @JsonProperty("administrationFee") Double administrationFee,
            @JsonProperty("conclusionFee") Double conclusionFee,
            @JsonProperty("amount") Double amount,
            @JsonProperty("monthlyPaymentDay") Integer monthlyPaymentDay,
            @JsonProperty("interestRate") Double interestRate
    ) {
        this.currency = currency;
        this.productType = productType;
        this.maturity = maturity;
        this.administrationFee = administrationFee;
        this.conclusionFee = conclusionFee;
        this.amount = amount;
        this.monthlyPaymentDay = monthlyPaymentDay;
        this.interestRate = interestRate;
    }

    public String getCurrency() {
        return currency;
    }

    public String getProductType() {
        return productType;
    }

    public Integer getMaturity() {
        return maturity;
    }

    public Double getAdministrationFee() {
        return administrationFee;
    }

    public Double getConclusionFee() {
        return conclusionFee;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getMonthlyPaymentDay() {
        return monthlyPaymentDay;
    }

    public Double getInterestRate() {
        return interestRate;
    }
}
