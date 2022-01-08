package com.ecommerce.my_ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {
    EditText email;
    TextView ok,login;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/ForgetPassword.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        email=findViewById(R.id.email_id_forget);
        ok=findViewById(R.id.ok_id);
        login=findViewById(R.id.logintxt_id);

        email.setTranslationY(300);
        ok.setTranslationY(300);
        login.setTranslationY(300);

        email.setAlpha(0);
        ok.setAlpha(0);
        login.setAlpha(0);

        login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        ok.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ForgetPassword.this,LoginPage.class);
                startActivity(it);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(ForgetPassword.this, "Email or Mobile is not set.", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
                else if(email.getText().toString().length()>100){
                    Toast.makeText(ForgetPassword.this, "Invalid Data.", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
                else {
                    String em = email.getText().toString();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                response=jsonObject.getString("Password");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(ForgetPassword.this,response, Toast.LENGTH_LONG).show();
                            email.setText("");
                            email.requestFocus();
                            requestQueue.stop();




                              /*  class JsonTest extends AsyncTask<String,String,String>{

                                    @Override
                                    protected String doInBackground(String... strings) {
                                        HttpURLConnection httpURLConnection=null;
                                        BufferedReader bufferedReader=null;
                                        try {
                                            URL url=new URL("https://jsonformatter.org/0d1446");
                                            httpURLConnection= (HttpURLConnection) url.openConnection();
                                            InputStream inputStream=httpURLConnection.getInputStream();
                                            bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                                            StringBuffer stringBuffer=new StringBuffer();
                                            String line;
                                            while ((line=bufferedReader.readLine())!=null){
                                                stringBuffer.append(line);
                                            }
                                            return stringBuffer.toString();
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        finally {
                                            httpURLConnection.disconnect();
                                            try {
                                                bufferedReader.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        return null;

                                    }

                                    @Override
                                    protected void onPostExecute(String s) {
                                        super.onPostExecute(s);
                                        Toast.makeText(ForgetPassword.this, ""+s, Toast.LENGTH_SHORT).show();
                                    }
                                }*/

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ForgetPassword.this, "Access Denied.", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<String, String>();
                            data.put("email", em);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });

    }
}