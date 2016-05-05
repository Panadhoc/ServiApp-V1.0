package com.devmobile.servi_alpha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    public static String JSON_URL =Globals.address+":8080/servi/app/user/signin?";
    private String resp;
    private String name;
    private String password;

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_address) EditText _addressText;
    @InjectView(R.id.input_phone) EditText _phoneText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_service) EditText _serviceText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.v(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        String address = _addressText.getText().toString();
        String phone = _phoneText.getText().toString();
        String service = _serviceText.getText().toString();
        try {
            address=URLEncoder.encode(address,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSON_URL += "username="+name+"&pwd="+password+"&address="+ address+"&phone="+phone+"&email="+email;

        if (service != null && !service.equals(""))
        {
            JSON_URL+="&service="+service;
            Log.v("error",JSON_URL);
        }
        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            onSignupSuccess();

                        }
                        else
                        {onSignupFailed();}
                        progressDialog.dismiss();

                        }

                    }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        requestQueue.add(stringRequest);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        Intent i= getIntent();
        i.putExtra("name",name);
        i.putExtra("password",password);
        setResult(RESULT_OK, i);
        Toast.makeText(getBaseContext(), "Your account has been created", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        String address = _addressText.getText().toString();
        String phone = _phoneText.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else if (name.contains(" ")){
            _nameText.setError("no spaces allowed");
            valid = false;
        }
        else
        {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 16) {
            _passwordText.setError("between 8 and 16 alphanumeric characters");
            valid = false;
        }else if (name.contains(" ")){
            _passwordText.setError("no spaces allowed");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if (phone.isEmpty() || phone.length() < 8 || phone.contains(" ")) {
            _phoneText.setError("Invalid phone number");
            valid = false;
        } else {
            _phoneText.setError(null);
        }
        if (address.isEmpty() ) {
            _addressText.setError("This field is required");
            valid = false;
        } else {
            _addressText.setError(null);
        }

        return valid;
    }
}