package com.devmobile.servi_alpha;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NewPostActivity extends AppCompatActivity {
    private Button post;
    private EditText title,desc;
    private String JSON_NEW_POST_URL ;
    private boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Post");
        post= (Button) findViewById(R.id.btn_post);
        title=(EditText)findViewById(R.id.input_title);
        desc=(EditText)findViewById(R.id.input_desc);

        post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validate() && flag) {
                    sendRequest();
                } else {
                    Toast.makeText(getBaseContext(), "Post failed", Toast.LENGTH_LONG).show();
                }

            }


        });


}

    private void sendRequest() {

        String titlestr = title.getText().toString();
        String descstr = desc.getText().toString();


        try {
            titlestr=URLEncoder.encode(titlestr,"utf-8");
            descstr=URLEncoder.encode(descstr,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSON_NEW_POST_URL = Globals.address+":8080/servi/app/user/add?id="+Globals.id
                +"&token="+Globals.token+"&title="+ titlestr+ "&description=" + descstr;
            flag=false;
        StringRequest stringRequest1 = new StringRequest(JSON_NEW_POST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){

                            Toast.makeText(NewPostActivity.this,"Post Succeeded" , Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(NewPostActivity.this,"Post Failed" , Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewPostActivity.this,"Post Failed" , Toast.LENGTH_LONG).show();
                flag=true;
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(NewPostActivity.this);
        requestQueue.add(stringRequest1);
    }

    public boolean validate(){
        boolean valid=true;
        String titlestr=title.getText().toString();
        String descstr=desc.getText().toString();
        if (titlestr.isEmpty()){
            title.setError("Required field");
            valid=false;
        }
        else{
            title.setError(null);
        }
        if (descstr.isEmpty() || descstr.length() < 50 ) {
            desc.setError("Description too short");
            valid = false;
        } else {
            desc.setError(null);
        }

        return valid;
    }
}
