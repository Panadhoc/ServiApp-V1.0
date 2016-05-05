package com.devmobile.servi_alpha;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class ParseJSON {


    public static String[] usernames;
    public static String[] userids;
    public static String[] phones;
    public static String[] emails;
    public static String[] addresses;
    public static String[] services;
    public static String[] ratings;

    public static String[] rusernames;
    public static String[] ruserids;
    public static String[] rphones;
    public static String[] remails;
    public static String[] raddresses;
    public static String[] rservices;
    public static String[] rratings;

    public static String[] postids;
    public static String[] posttitles;
    public static String[] postdescs;
    public static String[] postowners;

    public static String[] rpostids;
    public static String[] rposttitles;
    public static String[] rpostdescs;
    public static String[] rpostowners;


    public static final String KEY_USERNAME = "userName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERID = "userID";
    public static final String KEY_PHONE = "phoneNumber";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_SERVICE = "service";
    public static final String KEY_RATING = "rating";

    public static final String KEY_POSTID = "postID";
    public static final String KEY_POSTTITLE = "title";
    public static final String KEY_POSTDESC = "description";
    public static final String KEY_POSTOWNER = "userID";

    private JSONArray users = null;
    private JSONArray rusers = null;

    private JSONArray posts = null;
    private JSONArray rposts = null;



    private String json;
    public static Person owner;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseFU(){
        try {

            users = new JSONArray(json);
            usernames = new String[users.length()];
            userids = new String[users.length()];
            phones = new String[users.length()];
            emails = new String[users.length()];
            addresses = new String[users.length()];
            services = new String[users.length()];
            ratings= new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                userids[i] = jo.getString(KEY_USERID);
                usernames[i] = jo.getString(KEY_USERNAME);
                phones[i] = jo.getString(KEY_PHONE);
                try {
                emails[i] = jo.getString(KEY_EMAIL);
                }
                catch (Exception e ){}



                    addresses[i] = jo.getString(KEY_ADDRESS).replaceAll("%20"," ") ;

                try {
                    services[i] = jo.getString(KEY_SERVICE);
                }
                    catch (Exception e ){}
                try {
                    ratings[i] = jo.getString(KEY_RATING);
                }
                catch (Exception e ){}
            }
        } catch (JSONException e) {
            Log.v("error", "JSON user Parse didn't work");
        }
    }
    protected void parseFO(){

        try {
            posts = new JSONArray(json);
            postids = new String[posts.length()];
            posttitles = new String[posts.length()];
            postdescs = new String[posts.length()];
            postowners = new String[posts.length()];
            for(int i=0;i<posts.length();i++) {
                JSONObject jo = posts.getJSONObject(i);

                postids[i]= jo.getString(KEY_POSTID);

                    posttitles[i]=jo.getString(KEY_POSTTITLE).replaceAll("%20"," ");


                    postdescs[i]=jo.getString(KEY_POSTDESC).replaceAll("%20"," ");
                postowners[i]= jo.getString(KEY_POSTOWNER);

            }


        } catch (JSONException e) {
            Log.v("error", "JSON offers Parse didn't work");
        }


    }

    protected void parseRO(){

        try {
            rposts = new JSONArray(json);
            rpostids = new String[rposts.length()];
            rposttitles = new String[rposts.length()];
            rpostdescs = new String[rposts.length()];
            rpostowners = new String[posts.length()];
            for(int i=0;i<rposts.length();i++) {
                JSONObject jo = rposts.getJSONObject(i);

                rpostids[i]= jo.getString(KEY_POSTID);

                    rposttitles[i]=jo.getString(KEY_POSTTITLE).replaceAll("%20"," ");


                    rpostdescs[i]=jo.getString(KEY_POSTDESC).replaceAll("%20"," ");
                rpostowners[i]= jo.getString(KEY_POSTOWNER);

            }
        } catch (JSONException e) {
            Log.v("error", "JSON search offers Parse didn't work");
        }


    }
    protected void parseRU(){
        try {

            rusers = new JSONArray(json);
            rusernames = new String[rusers.length()];
            ruserids = new String[rusers.length()];
            rphones = new String[rusers.length()];
            remails = new String[rusers.length()];
            raddresses = new String[rusers.length()];
            rservices = new String[rusers.length()];
            rratings= new String[rusers.length()];

            for(int i=0;i<rusers.length();i++){
                JSONObject jo = rusers.getJSONObject(i);
                ruserids[i] = jo.getString(KEY_USERID);
                rusernames[i] = jo.getString(KEY_USERNAME);
                rphones[i] = jo.getString(KEY_PHONE);
                remails[i] = jo.getString(KEY_EMAIL);
                    raddresses[i]=jo.getString(KEY_ADDRESS).replaceAll("%20"," ");

                rservices[i]= jo.getString(KEY_SERVICE);
                rratings[i] = jo.getString(KEY_RATING);
            }
        } catch (JSONException e) {
            Log.v("error","JSON search users Parse didn't work");
        }
    }


    protected String[] parseID()  {
        String[] parsed= new String[2];
        try {
            JSONObject jo = new JSONObject(json);
            parsed[0]=jo.getString("id");
            parsed[1]=jo.getString("token");
        } catch (JSONException e) {
            Log.v("error", "JSON ID Parse didn't work");
        }



        return parsed;
    }
    protected void parsePO(){
        try {
            JSONObject jo = new JSONObject(json);
            owner=new Person(jo.getString(KEY_USERNAME),jo.getString(KEY_USERID),R.drawable.prof);
                owner.address=jo.getString(KEY_ADDRESS).replaceAll("%20"," ");


            owner.phone=jo.getString(KEY_PHONE);
            owner.email=jo.getString(KEY_EMAIL);
        } catch (JSONException e) {
            Log.v("error", "JSON post owners Parse didn't work");
        }


    }
}
