package com.devmobile.servi_alpha;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Souhail on 25/12/2015.
 */
class Person {
    String username;
    int photoID;
    String userId;
    String email;
    String service;
    String phone;
    String address;

    Person(String name, String userid, int photoId) {
        this.username = name;
        this.userId = userid;
        this.photoID = photoId;
    }
}

