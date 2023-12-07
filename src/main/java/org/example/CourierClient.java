package org.example;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.MatcherAssert;


public class CourierClient {


    private String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private String COURIER_URL = "/api/v1/courier";
    private String DELETE_URL = "/api/v1/courier/";
    private String LOGIN_URL = "/api/v1/courier/login";
    private String ORDERS_URL = "/api/v1/orders";

@Step("Создание курьера {courier}")
    public Response create (Courier courier){
    return             given()
                    .header("Content-type", "application/json")
                    .body(courier)
                    .when()
                    .post(COURIER_URL);
    }
    @Step ("Создание заказа с параметрам {order}")
    public Response createOrder (Order order){
        return             given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS_URL);
    }

    @Step ("Авторизация курьера с учётными данными {credentials}")
    public Response login ( Credentials credentials  ) {
        return given()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post(LOGIN_URL);
    }


    @Step ("Удаление курьера с ID {id}")
    public Response delete(int id) {
        return given()
                .header("Content-type", "application/json")
//                .body(courier)
                .when()
               .delete(DELETE_URL + id);
    }


}
