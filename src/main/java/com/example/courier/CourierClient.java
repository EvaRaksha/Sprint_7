package com.example.courier;

import com.example.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class CourierClient extends Client {
    static final String COURIER_PATH = "/courier";

    @Step("Отправка запроса на создание курьера")
    public ValidatableResponse create(Credentials courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Отправка запроса на логин курьера")
    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("Попытка входа с пустыми значениями")
    public ValidatableResponse loginWithEmptyValues() {
        Credentials emptyCredentials = new Credentials("", "");

        return login(emptyCredentials);
    }

    @Step("Попытка входа с несуществующими данным")
    public ValidatableResponse loginWithNonExistentAccount() {
        Credentials nonExistentCredentials = new Credentials("non_existent_login", "non_existent_password");

        return login(nonExistentCredentials);
    }

    @Step("Запрос на удаление курьера по ID")
    public ValidatableResponse delete(int courierId) {
        return spec()
                .body(Map.of("id", courierId))
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then().log().all();
    }

    @Step("Попытка создания курьера с пустыми данным")
    public ValidatableResponse createWithoutMandatoryFields() {
        Credentials emptyCredentials = new Credentials("", "");

        return create(emptyCredentials);
    }
}