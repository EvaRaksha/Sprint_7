package com.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

public class OrderCreate {
    private String trackNumber;

    @Step("Отправка запроса на успешное создние заказа")
    public void createdOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED);
        trackNumber = response.extract().path("track").toString();
    }

    @Step("Получение трекномера заказа")
    public String getTrackNumber() {
        return trackNumber;
    }
}