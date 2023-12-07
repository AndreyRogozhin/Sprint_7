package org.example;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static io.restassured.RestAssured.*;
import static org.example.CourierGenerator.randomCourier;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.MatcherAssert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@RunWith(Parameterized.class)
public class OrderTest {

        @Before
        public void setUp() {
            RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        }
        @Parameterized.Parameter(0)
        public String color1;
        @Parameterized.Parameter(1)
        public String color2;


        @Parameterized.Parameters(name = "Тест {index} для цветов: {0}, {1}")
        public static Object[][] data() {
            return new Object[][]{
                    {"BLACK",""},
                    {"GREY",""},
                    {"BLACK", "GREY"},
                    {"",""},
            };
        }



    @Test
    @Step("Оформление заказа")
    public void createTestOrder(){

        List<String> colors = new ArrayList<String>();
        if (!color1.isEmpty()) colors.add(color1);
        if (!color2.isEmpty()) colors.add(color2);

        Order order = new Order("Алексей","Алексеев", "Летняя, 23", "Сокол", "+79990001122", 3, "01-12-2023", "на три дня", colors);
        CourierClient courierClient = new CourierClient();
       Response response = courierClient.createOrder(order);

        response.then()                .statusCode(201)
                .and().assertThat().body("track", notNullValue());

    }           }