package org.example;

import static org.example.Utils.randomString;

public class CourierGenerator {

    public static Courier randomCourier(){
        return new Courier(randomString(8), randomString(16), randomString(6));
        //return new Courier("myLogin11233", "myPassw", "myName");
    }

    public static Courier givenCourier(String login){
        return new Courier(login, randomString(16), randomString(6));
//        return new Courier(login, "myPassw", "myName");
    }

}
