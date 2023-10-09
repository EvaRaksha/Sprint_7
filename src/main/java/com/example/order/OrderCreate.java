package com.example.order;

import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;

public class OrderCreate {
    private String trackNumber; // Объявляем переменную trackNumber

    public void createdOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED);
        trackNumber = response.extract().path("track").toString();
    }
    // Геттер для получения сохраненного номера заказа
    public String getTrackNumber() {
        return trackNumber;
    }

    public void getOrdersList(ValidatableResponse response) {
    }
}

