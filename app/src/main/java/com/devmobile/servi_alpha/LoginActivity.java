package com.devmobile.servi_alpha;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
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

import java.io.File;
import java.io.FileOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static String JSON_URL ;
    private static final int REQUEST_SIGNUP = 0;
    private String name;
    private String password;
    private String resp;
    private String[] token;


    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Globals.init_address();
        JSON_URL =Globals.address+":8080/servi/app/user/login?username=";
        try {
            String filename = "token.txt";
            File file = new File(getFilesDir(), filename);

        if (file.exists()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }
        }catch (Exception e){}
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (validate())
                {
                    login();
                }

            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate() && (name == null|| name.equals(("")))) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        if (name == null) {
            name = _emailText.getText().toString();
            password = _passwordText.getText().toString();
        }
        JSON_URL+=name+"&pwd="+password;
        Log.v("error",JSON_URL);
        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ParseJSON pj = new ParseJSON(response);
                        resp=response;
                        token=pj.parseID();

                    }

                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (resp==null || resp.equals("{}") ){
                            onLoginFailed();
                        }
                        else {
                            onLoginSuccess();
                        }

                        progressDialog.dismiss();
                    }
                },4000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                name=data.getStringExtra("name");
                password=data.getStringExtra("password");
                login();

            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        String filename="token.txt";
        File file = new File(getFilesDir(), filename);
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(token[0].getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(token[1].getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(password.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        name= _emailText.getText().toString();
        password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _emailText.setError("at least 3 characters");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 16) {
            _passwordText.setError("between 8 and 16 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}