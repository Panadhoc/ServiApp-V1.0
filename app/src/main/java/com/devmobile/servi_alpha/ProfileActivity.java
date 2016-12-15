package com.devmobile.servi_alpha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ProfileActivity extends AppCompatActivity  {


    TextView name;
    TextView email;
    TextView address;
    TextView phone;

    private AppCompatButton edit;

    private String JSON_URL=Globals.address+":8080/servi/app/user/profile?id="+Globals.id+"&token="+Globals.token+"&userID="+Globals.id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
         name = (TextView) findViewById(R.id.person_name);
        email = (TextView) findViewById(R.id.person_email);
        address = (TextView) findViewById(R.id.person_address);
        phone = (TextView) findViewById(R.id.person_number);
        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();


        StringRequest stringRequest1 = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ParseJSON pj =new ParseJSON(response);

                        pj.parsePO();
                        name.setText(ParseJSON.owner.username);
                        email.setText(ParseJSON.owner.email);
                        address.setText(ParseJSON.owner.address);
                        phone.setText(ParseJSON.owner.phone);
                        progressDialog.dismiss();
                        findViewById(android.R.id.content).invalidate();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this,"Connection Problem" , Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(stringRequest1);
        edit= (AppCompatButton) findViewById(R.id.btn_edit);
        edit.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProfileEditActivity.class);

                intent.putExtra("username",ParseJSON.owner.username);
                intent.putExtra("email" ,ParseJSON.owner.email);
                intent.putExtra("address",ParseJSON.owner.address);
                intent.putExtra("phone",ParseJSON.owner.phone);
                startActivity(intent);

            }
        });

    }






}
