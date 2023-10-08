package com.example.order;

import com.example.Client;
import com.example.courier.Credentials;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {

    static final String ORDER_PATH = "/orders";

    public ValidatableResponse create(Credentials order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

}
