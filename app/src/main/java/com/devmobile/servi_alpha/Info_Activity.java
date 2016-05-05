package com.devmobile.servi_alpha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


public class Info_Activity extends AppCompatActivity {


    private int value;
    private String postid;
    private String owner;
    private String JSON_POST_OWNER_URL=Globals.address+":8080/servi/app/user/owner?id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();


        try {
            Log.v("error", i.getStringExtra("postid"));


        }catch(Exception e){}
        value = i.getIntExtra("value",0);
        if (value==1) {
            setContentView(R.layout.activity_info_user);
            TextView username = (TextView) findViewById(R.id.user_name);
            TextView address = (TextView) findViewById(R.id.user_address);
            TextView phone = (TextView) findViewById(R.id.user_number);

            username.setText("Username: "+i.getStringExtra("username"));
            address.setText("Address: "+i.getStringExtra("address"));
            phone.setText("Phone Number: "+i.getStringExtra("phone"));
            findViewById(android.R.id.content).invalidate();


        }
        else {
            setContentView(R.layout.activity_info_post);
            TextView title = (TextView) findViewById(R.id.post_title);
            TextView desc = (TextView) findViewById(R.id.post_desc);
            Button contact_button = (Button) findViewById(R.id.btn_contact);
            title.setText(i.getStringExtra("title"));
            desc.setText(i.getStringExtra("desc"));
            findViewById(android.R.id.content).invalidate();
            postid=i.getStringExtra("postid");
            JSON_POST_OWNER_URL+= Globals.id+"&token="+Globals.token+"&post="+postid;
            StringRequest stringRequest = new StringRequest(JSON_POST_OWNER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ParseJSON pj = new ParseJSON(response);
                            pj.parsePO();

                        }},new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Info_Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(Info_Activity.this);
            requestQueue.add(stringRequest);

            contact_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), Info_Activity.class);
                    intent.putExtra("value",1);
                    intent.putExtra("username",ParseJSON.owner.username);
                    intent.putExtra("address",ParseJSON.owner.address);
                    intent.putExtra("phone",ParseJSON.owner.phone);
                    startActivity(intent);


                }
            });
        }

    }

}
