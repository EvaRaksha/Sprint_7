package com.example.order;

import com.example.Client;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.notNullValue;

public class OrderList extends Client {
    public ValidatableResponse getOrdersList(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
        ;
        return response;
    }

    public void checkOrdersInResponse(ValidatableResponse response) {
        response
                .assertThat().body("orders", notNullValue());
    }
}