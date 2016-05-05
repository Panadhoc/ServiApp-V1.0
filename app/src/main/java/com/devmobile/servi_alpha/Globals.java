package com.devmobile.servi_alpha;

/**
 * Created by Souhail on 03/01/2016.
 */
public class Globals {
    public static String token;
    public static String id;
    public static String address;
    public static String password;
    public Globals(String token,String id){

        Globals.token=token;
        Globals.id=id;



    }
    public static void init_address(){
        Globals.address="http://172.16.96.101";

    }
    public static void set_pass(String password){

        Globals.password=password;
    }


}
