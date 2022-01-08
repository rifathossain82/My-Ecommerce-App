package com.ecommerce.my_ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Show_details_product extends AppCompatActivity {
    ImageView imageView;
    TextView name,price,description,wishlist;

    private String file="myfile_extra";
    String url_image,id_,name_,price_,des_,email;
    Button add_to_cart,buy_now;

    String db_file3="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/wishlist_select_icon.php";
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/add_cart.php";
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/wishlist_insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_product);
        imageView=findViewById(R.id.imageview_details);
        name=findViewById(R.id.name_details);
        price=findViewById(R.id.price_details);
        description=findViewById(R.id.description_details);
        add_to_cart=findViewById(R.id.add_to_cart_id);
        buy_now=findViewById(R.id.buy_now_id);
        wishlist=findViewById(R.id.wishlist_show_dProduct_id);

        url_image=getIntent().getStringExtra("image");
        id_=getIntent().getStringExtra("id");
        name_=getIntent().getStringExtra("name");
        price_=getIntent().getStringExtra("price");
        des_=getIntent().getStringExtra("des");

        Picasso.get().load(url_image).into(imageView);
        name.setText(name_);
        price.setText("BDT. "+price_+" tk");
        description.setText(des_);

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

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Show_details_product.this, "Item added to cart.", Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Show_details_product.this, "No Data Added.", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<String, String>();
                        data.put("email",email );
                        data.put("id",id_ );
                        data.put("name", name_);
                        data.put("price", price_);
                        data.put("des", des_);
                        data.put("image", url_image);
                        return data;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        //show image
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getApplicationContext(),Show_Image.class);
                it.putExtra("image1",url_image);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
            }
        });


        //add wishlist
        wishlist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    //search duplicate
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file3, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.contains("True")){
                            }
                            if(response.contains("False")){
                                wishlist_create(email,id_,name_,price_,des_,url_image);
                            }
                            requestQueue.stop();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Failed to select favourite.", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<String, String>();
                            data.put("email",email );
                            data.put("id",id_ );
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Toast.makeText(v.getContext(), "Item added to wishlist.  ", Toast.LENGTH_SHORT).show();
                }
        });


        //buy now set listener

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent=new Intent(Show_details_product.this,Add_to_cart.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Show_details_product.this, "No Data Added.", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<String, String>();
                        data.put("email",email );
                        data.put("id",id_ );
                        data.put("name", name_);
                        data.put("price", price_);
                        data.put("des", des_);
                        data.put("image", url_image);
                        return data;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

    }
    void wishlist_create(String email,String id,String name,String price,String des,String image){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed To add wishlist.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email );
                data.put("id",id );
                data.put("name", name);
                data.put("price", price);
                data.put("des", des);
                data.put("image", image);
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }


}