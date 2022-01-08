package com.ecommerce.my_ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.ecommerce.my_ecommerceapp.adminPanel.My_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add_to_cart extends AppCompatActivity implements Recyclerview_interface{
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/get_data_to_cart.php";
    RecyclerView recyclerView;
    private List<String> l_sl;
    private List<String> l_id;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_images;
    private My_adapter4 myAdapter4;
    private String file="myfile_extra";
    Button tk,payment;
    int price=0;
    String email;
    String delete_url="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/remove_cart.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        recyclerView=findViewById(R.id.recyclcerview_id_add_to_cart);
        tk=findViewById(R.id.tk_id_add_to_cart);
        payment=findViewById(R.id.payment_id_add_cart);

        l_sl=new ArrayList<>();
        l_id=new ArrayList<>();
        l_name=new ArrayList<>();
        l_price=new ArrayList<>();
        l_des=new ArrayList<>();
        l_images=new ArrayList<>();


        //find email
        try {
            FileInputStream fin =getApplicationContext().openFileInput(file);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            email=temp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(Add_to_cart.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        l_sl.add(jsonObject.getString("Sl"));
                        l_id.add(jsonObject.getString("Id"));
                        l_name.add(jsonObject.getString("Name"));
                        l_price.add(jsonObject.getString("Price"));
                        l_des.add(jsonObject.getString("Details"));
                        l_images.add(jsonObject.getString("Image"));
                        price=price+jsonObject.getInt("Price");

                    }
                    tk.setText("TK.  "+price);
                    myAdapter4=new My_adapter4(getApplicationContext(),l_sl,l_id,l_name,l_price,l_des,l_images,Add_to_cart.this,price);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(myAdapter4);
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


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String total= tk.getText().toString();
                Intent it=new Intent(Add_to_cart.this,Payment_confirm.class);
                it.putExtra("tk",total);
                it.putExtra("email",email);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
                finish();
            }
        });
    }

    @Override
    public void click_listener(String email,String sl,int position) {
        price=price-Integer.parseInt(l_price.get(position));
        tk.setText("TK.  "+price);
        l_sl.remove(position);
        l_name.remove(position);
        l_id.remove(position);
        l_price.remove(position);
        l_des.remove(position);
        l_images.remove(position);
        myAdapter4.notifyItemRemoved(position);
        cart_delete(email,sl);
    }
    void cart_delete(String email,String sl){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, delete_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                requestQueue.stop();

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
                data.put("sl", sl);
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }

}