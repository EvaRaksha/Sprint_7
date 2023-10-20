package com.example.order;

import com.example.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {

    static final String ORDERS_PATH = "/orders";

    @Step("Отправка запроса на создание заказа")
    public ValidatableResponse createOrder(OrderData orderData) {
        return spec()
                .body(orderData)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("Отправка запроса на удаление заказа")
    public void deleteOrder(String trackNumber) {
    }

    @Step("Отправка запроса на получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return spec()
                .when()
                .get(ORDERS_PATH)
                .then().log().all();
    }
}