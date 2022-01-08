package com.ecommerce.my_ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {
LottieAnimationView lottieAnimationView;
EditText email,password;
TextView forgetpass,signup,login;
String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

        lottieAnimationView=findViewById(R.id.lganim_id);
        email=findViewById(R.id.email_id);
        password=findViewById(R.id.password_id);
        forgetpass=findViewById(R.id.forgetpass_id);
        login=findViewById(R.id.login_id);
        signup=findViewById(R.id.signuptxt_id);



        email.setTranslationY(300);
        password.setTranslationY(300);
        forgetpass.setTranslationY(300);
        login.setTranslationY(300);
        signup.setTranslationY(300);


        email.setAlpha(0);
        password.setAlpha(0);
        forgetpass.setAlpha(0);
        login.setAlpha(0);
        signup.setAlpha(0);


        email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        forgetpass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1100).start();
        signup.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1200).start();


        forgetpass.setEnabled(false);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(LoginPage.this,SignupPage.class);
                startActivity(it);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(LoginPage.this, "Email Address is empty!", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
                else if(!email.getText().toString().contains("@gmail.com")){
                    Toast.makeText(LoginPage.this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
                else if(password.getText().toString().isEmpty()){
                    Toast.makeText(LoginPage.this, "Password is empty!", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }
                else if(password.getText().toString().length()<6 || password.getText().toString().length()>12){
                    Toast.makeText(LoginPage.this, "Password must be 6 to 12 letters.", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }
                else {
                    String em = email.getText().toString();
                    String pass = password.getText().toString();

                    if(em.equals("rh@gmail.com") && pass.equals("123456")){
                        Toast.makeText(LoginPage.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent it=new Intent(LoginPage.this,Homepage.class);
                        it.putExtra("data","1");
                        it.putExtra("email",em);
                        startActivity(it);
                        finish();
                    }
                    else {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(LoginPage.this, response, Toast.LENGTH_SHORT).show();
                                email.setText("");
                                password.setText("");
                                email.requestFocus();
                                requestQueue.stop();

                                if (response.contains("Login Success")) {
                                    Intent it = new Intent(LoginPage.this, Homepage.class);
                                    it.putExtra("data","0");
                                    it.putExtra("email",em);
                                    startActivity(it);
                                    finish();
                                }
                                if (response.contains("Login Failed")) {
                                    forgetpass.setEnabled(true);
                                    forgetpass.setText("Forget Password?");

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginPage.this, "Access Denied.", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            protected Map<String, String> getParams() {
                                Map<String, String> data = new HashMap<String, String>();
                                data.put("email", em);
                                data.put("password", pass);
                                return data;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(LoginPage.this,ForgetPassword.class);
                startActivity(it);
            }
        });
    }
}