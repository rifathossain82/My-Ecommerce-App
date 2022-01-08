package com.ecommerce.my_ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupPage extends AppCompatActivity{
   public String s_email;
    EditText email,mobile,password,con_password;
    TextView signup;
    ImageView back;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/sign_up.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_page);

        email=findViewById(R.id.email_id_sign);
        mobile=findViewById(R.id.mobile_id_sign);
        password=findViewById(R.id.password_id_sign);
        con_password=findViewById(R.id.con_password_id);
        back=findViewById(R.id.back_id);
        signup=findViewById(R.id.signup_id);





        email.setTranslationY(300);
        mobile.setTranslationY(300);
        password.setTranslationY(300);
        con_password.setTranslationY(300);
        signup.setTranslationY(300);

        email.setAlpha(0);
        mobile.setAlpha(0);
        password.setAlpha(0);
        con_password.setAlpha(0);
        signup.setAlpha(0);

        email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        mobile.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        con_password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        signup.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1200).start();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(SignupPage.this,LoginPage.class);
                startActivity(it);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(SignupPage.this, "Email Address is empty!", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
                else if(!email.getText().toString().contains("@gmail.com")){
                    Toast.makeText(SignupPage.this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
                else if(mobile.getText().toString().isEmpty()){
                    Toast.makeText(SignupPage.this, "Mobile Number is empty!", Toast.LENGTH_SHORT).show();
                    mobile.requestFocus();
                }
                else if(mobile.getText().toString().length()>11){
                    Toast.makeText(SignupPage.this, "Invalid Mobile Number!", Toast.LENGTH_SHORT).show();
                    mobile.requestFocus();
                }
                else if(password.getText().toString().isEmpty()){
                    Toast.makeText(SignupPage.this, "Password is empty!", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }
                else if(con_password.getText().toString().isEmpty()){
                    Toast.makeText(SignupPage.this, "Confirm Password is empty!", Toast.LENGTH_SHORT).show();
                    con_password.requestFocus();
                }

                else if(password.getText().toString().length()<6 || password.getText().toString().length()>12){
                    Toast.makeText(SignupPage.this, "Password must be 6 to 12 letters.", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }
                else if(con_password.getText().toString().length()<6 || con_password.getText().toString().length()>12){
                    Toast.makeText(SignupPage.this, "Password must be 6 to 12 letters.", Toast.LENGTH_SHORT).show();
                    con_password.requestFocus();
                }
                else if(!password.getText().toString().contains(con_password.getText().toString())){
                    Toast.makeText(SignupPage.this, "Password Not Matching!", Toast.LENGTH_SHORT).show();
                    password.setText("");
                    con_password.setText("");
                    password.requestFocus();
                }
                else {
                    String em = email.getText().toString();
                    String mo = mobile.getText().toString();
                    String pass = password.getText().toString();
                    String con_pass = con_password.getText().toString();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SignupPage.this, response, Toast.LENGTH_SHORT).show();
                            s_email=email.getText().toString();
                            email.setText("");
                            mobile.setText("");
                            password.setText("");
                            con_password.setText("");
                            email.requestFocus();
                            requestQueue.stop();

                            if(response.contains("Signup Success")){
                                Intent it=new Intent(SignupPage.this,Homepage.class);
                                it.putExtra("email",em);
                                startActivity(it);
                                finish();
                            }
                            if(response.contains("Signup Failed")){


                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignupPage.this, "Access Denied.", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<String, String>();
                            data.put("email", em);
                            data.put("mobile", mo);
                            data.put("password", pass);
                            data.put("con_password", con_pass);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}