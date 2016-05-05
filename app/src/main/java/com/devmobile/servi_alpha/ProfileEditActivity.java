package com.devmobile.servi_alpha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ProfileEditActivity extends AppCompatActivity {

    EditText name;
    EditText address;
    EditText email;
    EditText phone;
    EditText password;
    AppCompatButton save;
    String JSON_URL =Globals.address+":8080/servi/app/user/account?id="+Globals.id
            +"&token="+Globals.token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        name = (EditText) findViewById(R.id.edit_username);
        password=(EditText) findViewById(R.id.edit_password);
        address = (EditText) findViewById(R.id.edit_address);
         email = (EditText) findViewById(R.id.edit_email);
         phone = (EditText) findViewById(R.id.edit_phone);

        save = (AppCompatButton) findViewById(R.id.btn_save);




        Intent i = getIntent();
        name.setText(i.getStringExtra("username"));
        email.setText(i.getStringExtra("email"));
        address.setText(i.getStringExtra("address"));
        phone.setText((i.getStringExtra("phone")));
        password.setText(Globals.password);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){

                    sendRequest();
                }


            }
        });

    }

    private void sendRequest() {

        JSON_URL+= "&username="+name.getText().toString()+"&email="+email.getText().toString()+"&address="+address.getText().toString()+"&phone="+phone.getText().toString()+"&pwd="+password.getText().toString();
        Log.v("error", JSON_URL);
        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){

                            Toast.makeText(ProfileEditActivity.this,"Profile Saved" , Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(ProfileEditActivity.this,"An error occured" , Toast.LENGTH_LONG).show();
                        }


                    }

                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileEditActivity.this, "Edit Failed", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileEditActivity.this);
        requestQueue.add(stringRequest);
    }

    public boolean validate() {
        boolean valid = true;

        String namestr = name.getText().toString();
        String emailstr = email.getText().toString();
        String passwordstr = password.getText().toString();
        String addressstr = address.getText().toString();
        String phonestr = phone.getText().toString();


        if (namestr.isEmpty() || name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else if (namestr.contains(" ")){
            name.setError("no spaces allowed");
            valid = false;
        }
        else
        {
            name.setError(null);
        }

        if (emailstr.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailstr).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passwordstr.isEmpty() || passwordstr.length() < 8 || passwordstr.length() > 16) {
            password.setError("between 8 and 16 alphanumeric characters");
            valid = false;
        }else if (namestr.contains(" ")){
            password.setError("no spaces allowed");
            valid = false;
        } else {
            password.setError(null);
        }
        if (phonestr.isEmpty() || phonestr.length() < 8 || phonestr.contains(" ")) {
            phone.setError("Invalid phone number");
            valid = false;
        } else {
            phone.setError(null);
        }
        if (addressstr.isEmpty() ) {
            address.setError("This field is required");
            valid = false;
        } else {
            address.setError(null);
        }

        return valid;
    }

}
