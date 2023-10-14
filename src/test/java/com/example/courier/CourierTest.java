package com.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CourierTest {

    private final CourierClient client = new CourierClient();
    private final CourierCreating chek = new CourierCreating();
    private final CourierLogin chekLogin = new CourierLogin();
    private int courierId;

    @After
    @Step("Удаление курьера по id")
    public void deleteCourier() {
        client.delete(courierId);
    }

    @Test
    @Step("Создание курьера")
    public void testCreateCourier() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(Credentials.from(courier));
        chek.createdSuccessfully(response);
    }

    @Test
    @Step("Создание курьера без передачи обязательных полей")
    public void testCreateCourierWithoutMandatoryFields() {
        ValidatableResponse createWithoutMandatoryFieldsResponse = client.createWithoutMandatoryFields();
        chek.insufficientDataForCreate(createWithoutMandatoryFieldsResponse);
    }

    @Test
    @Step("Попытка создания двух одинаковых курьеров")
    public void testDuplicateCreateCourier() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(Credentials.from(courier));
        chek.createdSuccessfully(response);

        ValidatableResponse duplicateCreateResponse = client.create(Credentials.from(courier));
        chek.duplicateCreateLoginError(duplicateCreateResponse);
    }

    @Test
    @Step("Создание двух разных курьеров")
    public void testCreateTwoDifferentCouriers() {
        var courier1 = CourierGenerator.random();
        var courier2 = CourierGenerator.random();

        ValidatableResponse createResponse1 = client.create(Credentials.from(courier1));
        chek.createdSuccessfully(createResponse1);

        ValidatableResponse createResponse2 = client.create(Credentials.from(courier2));
        chek.createdSuccessfully(createResponse2);
    }

    @Test
    @Step("Попытка создания курьера с существующим логином")
    public void testCreateCourierWithDuplicateLogin() {
        var courier = CourierGenerator.random();

        ValidatableResponse createResponse = client.create(Credentials.from(courier));
        chek.createdSuccessfully(createResponse);

        ValidatableResponse duplicateCreateResponse = client.create(Credentials.from(courier));
        chekLogin.duplicateLoginError(duplicateCreateResponse);
    }

    @Test
    @Step("Попытка входа курьера без передачи обязательных полей")
    public void testLoginCourierWithoutMandatoryFields() {
        ValidatableResponse invalidLoginResponse = client.loginWithEmptyValues();
        chekLogin.insufficientDataForLogin(invalidLoginResponse);
    }

    @Test
    @Step("Вход несуществующим пользователем")
    public void testLoginWithNonExistentUser() {
        var courier = CourierGenerator.random();
        ValidatableResponse createResponse = client.create(Credentials.from(courier));

        ValidatableResponse nonExistentAccountResponse = client.loginWithNonExistentAccount();
        chekLogin.accountNotFoundError(nonExistentAccountResponse);
    }

    @Test
    @Step("Успешный вход курьера")
    public void testSuccessfulCourierLogin() {
        var courier = CourierGenerator.random();
        ValidatableResponse createResponse = client.create(Credentials.from(courier));

        ValidatableResponse loginResponse = client.login(Credentials.from(courier));
        chekLogin.loggedInSuccessfully(loginResponse);
    }
}