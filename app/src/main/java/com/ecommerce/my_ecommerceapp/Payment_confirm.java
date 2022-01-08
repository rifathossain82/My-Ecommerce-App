package com.ecommerce.my_ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Payment_confirm extends AppCompatActivity {
    TextView cancel,tk,date_delivery;
    TextInputLayout name,number,region,city,area,address;
    Button office,home,proceed;
    String label="HOME",date,email,ss="";
    int x=0;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/get_data_to_cart.php";
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/add_order.php";
    String db_insert="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/Insert_order.php";
    String delete_url="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete_cart_all.php";
    String db_file3="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/search_orderForBillNO.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);
        cancel=findViewById(R.id.cancel_confirm_id);
        tk=findViewById(R.id.total_tk_id);

        name=findViewById(R.id.full_name_il_id);
        number=findViewById(R.id.phone_il_id);
        region=findViewById(R.id.region_il_id);
        city=findViewById(R.id.city_il_id);
        area=findViewById(R.id.area_name_il_id);
        address=findViewById(R.id.address_il_id);
        date_delivery=findViewById(R.id.delivery_date_id);

        office=findViewById(R.id.office_btn_id);
        home=findViewById(R.id.home_btn_id);
        proceed=findViewById(R.id.proceed_id);

        String total=getIntent().getStringExtra("tk");
        email=getIntent().getStringExtra("email");

        tk.setText(" "+total+"");

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
        date=simpleDateFormat.format(Calendar.getInstance().getTime());

        addday(3,0,0);
        addday2(5,0,0);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label=office.getText().toString();
                office.setBackgroundResource(R.drawable.button_00);
               // office.setLeft(R.drawable.ic_office2);
                //home.setLeft(R.drawable.ic_home2);
               home.setBackgroundResource(R.drawable.button_0);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                label=home.getText().toString();
                office.setBackgroundResource(R.drawable.button_0);
                //office.setLeft(R.drawable.ic_office);

                //home.setLeft(R.drawable.ic_home3);
                home.setBackgroundResource(R.drawable.button_000);
            }
        });

        //for product name

        RequestQueue requestQueue = Volley.newRequestQueue(Payment_confirm.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String n=jsonObject.getString("Name");
                        ss=ss.concat(n+", ");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email);
                return data;
            }
        };
        requestQueue.add(stringRequest);

        //end to finding product name

        // find id//
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, db_file3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        x=jsonObject.getInt("Id");
                    }
                    x=x+1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.contains("No Data found")){
                }
                requestQueue2.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email );
                return data;
            }
        };
        requestQueue2.add(stringRequest2);

        //end process//
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_=name.getEditText().getText().toString().trim();
                String phone_=number.getEditText().getText().toString().trim();
                String region_=region.getEditText().getText().toString().trim();
                String city_=city.getEditText().getText().toString().trim();
                String area_=area.getEditText().getText().toString().trim();
                String address_=address.getEditText().getText().toString().trim();

                if(name_.isEmpty()){
                    name.setError("Name is Empty!");
                    name.requestFocus();
                }
                else if(phone_.isEmpty()){
                    number.setError("Phone Number is Empty!");
                    number.requestFocus();
                }
                else if(phone_.length()!=11){
                    number.setError("Invalid Phone Number!");
                    number.requestFocus();
                }
                else if(region_.isEmpty()){
                    region.setError("Region is Empty!");
                    region.requestFocus();
                }
                else if(city_.isEmpty()){
                    city.setError("City is Empty!");
                    city.requestFocus();
                }
                else if(area_.isEmpty()){
                    area.setError("Area is Empty!");
                    area.requestFocus();
                }
                else if(address_.isEmpty()){
                    address.setError("Address is Empty!");
                    address.requestFocus();
                }
                else{
                    String address=region_+", "+city_+", "+area_+", "+address_;
                    add_order(name_,phone_,address,label,ss,total,email,"Order",date,x);
                }
            }
        });


    }
    void addday(int n,int n2,int n3){
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,n);
        cal.add(Calendar.MONTH,n2);
        cal.add(Calendar.YEAR,n3);
        String result_fubd=dateFormat.format(cal.getTime());
        date_delivery.setText("Possible delivery date: "+result_fubd);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    void addday2(int n, int n2, int n3){
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,n);
        cal.add(Calendar.MONTH,n2);
        cal.add(Calendar.YEAR,n3);
        String result_fubd=dateFormat.format(cal.getTime());
        date_delivery.setText(date_delivery.getText().toString().concat(" to "+result_fubd));

    }

    void show_add_product(){
        RequestQueue requestQueue2 = Volley.newRequestQueue(Payment_confirm.this);
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String s=jsonObject.getString("Sl");
                        String id=jsonObject.getString("Id");
                        String n=jsonObject.getString("Name");
                        String p=jsonObject.getString("Price");
                        String d=jsonObject.getString("Details");
                        String img=jsonObject.getString("Image");
                        String po= String.valueOf(x);
                        add_cart(po,email,id,n,p,d,img);
                   }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue2.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email);
                return data;
            }
        };
        requestQueue2.add(stringRequest2);
    }
    void add_cart(String p,String em0,String id0,String nm0,String pp0,String des0,String img0){
        RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, db_file2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cart_delete(email);
                requestQueue3.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Payment_confirm.this, "Failed Something.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",em0 );
                data.put("id",id0 );
                data.put("name", nm0);
                data.put("price", pp0);
                data.put("des", des0);
                data.put("image", img0);
                data.put("sl",p);
                return data;
            }
        };
        requestQueue3.add(stringRequest3);
    }
    void add_order(String name0,String phone0,String address0,String d_place0,String p_name0,String ammount0,String email0,String status0,String date0,int p){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Payment_confirm.this, ""+response, Toast.LENGTH_SHORT).show();
               if(response.contains("Order Success.")){
                   show_add_product();

                   Intent it=new Intent(Payment_confirm.this,Homepage.class);
                   String e="0";
                   if(email0.contains("rh@gmail.com")){
                       e="1";
                   }
                   it.putExtra("data",e);
                   it.putExtra("email",email0);
                   it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(it);
                   finish();
               }
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Payment_confirm.this, "Failed Proceed.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("t1",name0 );
                data.put("t2",phone0 );
                data.put("t3", address0);
                data.put("t4", d_place0);
                data.put("t5", p_name0);
                data.put("t6", ammount0);
                data.put("t7", email0);
                data.put("t8", status0);
                data.put("t9", date0);
                data.put("t10", String.valueOf(p));
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
    void cart_delete(String email){
        RequestQueue requestQueue4 = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, delete_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestQueue4.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No Data Deleted.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email );
                return data;
            }
        };
        requestQueue4.add(stringRequest4);

    }
}