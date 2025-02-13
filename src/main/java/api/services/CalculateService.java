package api.services;

import io.restassured.response.Response;
import api.config.Endpoints;
import api.core.Client;
import api.models.CalculateRequest;

public class CalculateService {
    public static Response calculateLoan(CalculateRequest request) {
        return Client.post(Endpoints.CALCULATE, request);
    }
}
