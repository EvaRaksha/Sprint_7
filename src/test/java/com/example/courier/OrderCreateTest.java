package com.example.courier;

import com.example.Client;
import com.example.order.OrderClient;
import com.example.order.OrderCreate;
import com.example.order.OrderData;
import com.example.order.OrderList;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderCreateTest extends Client{

    private final OrderClient client = new OrderClient();
    private final OrderCreate chek = new OrderCreate();

    private final OrderList checkList = new OrderList();

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> scooterColor;

    @Parameterized.Parameter(0)
    public List<String> scooterColors;

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Collection<List<String>> initParamsForTest() {
        return Arrays.asList(
                Arrays.asList(),           // Без указания цвета
                Arrays.asList("BLACK"),   // Цвет BLACK
                Arrays.asList("GREY"),    // Цвет GREY
                Arrays.asList("BLACK", "GREY")  // Оба цвета
        );
    }

    @Before
    public void prepareTestData() {
        this.firstName = "testName";
        this.lastName = "testLastName";
        this.address = "Москва, Тестовая ул., д. 123";
        this.metroStation = "Черкизовская";
        this.phone = "+79999999999";
        this.rentTime = "3";
        this.deliveryDate = "2023-07-24";
        this.comment = "Some comment";
        this.scooterColor = scooterColors;
    }

    @After
    @DisplayName("Удаление заказа по его номеру")
    public void cleanupTestData() {
        if (chek.getTrackNumber() != null) {
            client.deleteOrder(chek.getTrackNumber());
        }
    }

    @Test
    @DisplayName("Создание заказа")
    public void testPostRequest() {
        OrderData orderData = new OrderData(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                scooterColor
        );


        ValidatableResponse response;
        response = client.createOrder(OrderData.from(orderData));
        chek.createdOrderSuccessfully(response);

        String trackNumber = chek.getTrackNumber();
    }

    @Test
    @DisplayName("Список заказов")
    public void testGetOrdersList() {
            ValidatableResponse response = client.getOrdersList();
            checkList.getOrdersList(response);
    }

    @Test
    @DisplayName("Список заказов не равен нулю")
    public void testCheckOrdersInResponse() {
        ValidatableResponse response = client.getOrdersList();
        checkList.checkOrdersInResponse( response);
    }
}