package com.example.order;

import com.example.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {

    static final String ORDERS_PATH = "/orders";

    public ValidatableResponse createOrder(OrderData orderData) {
        return spec()
                .body(orderData)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    public void deleteOrder(String trackNumber) {
    }

    public ValidatableResponse getOrdersList() {
        return spec()
                .when()
                .get(ORDERS_PATH)
                .then().log().all();
    }
}