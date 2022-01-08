package com.ecommerce.my_ecommerceapp.adminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ecommerce.my_ecommerceapp.ForgetPassword;
import com.ecommerce.my_ecommerceapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getproduct extends AppCompatActivity {
    /*TextView name,price,des,category;
    ImageView imageView;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/getproduct.php";
   */ @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getproduct);

       /* name=findViewById(R.id.name_);
        price=findViewById(R.id.price_);
        des=findViewById(R.id.des_);
        category=findViewById(R.id.category_);
        imageView=findViewById(R.id.image_);

        getdata();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_id);
        bottomNavigationView.setSelectedItemId(R.id.Show_product_id);

       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.Show_product_id:
                       return true;
                   case R.id.add_product_id:
                       startActivity(new Intent(getApplicationContext(),
                               AddProduct.class));
                       overridePendingTransition(0,0);
                       return true;
                   // case R.id.modify_product_id:
                   //startActivity(new Intent(getApplicationContext(),
                   //        getproduct.class));
                   // overridePendingTransition(0,0);
                   // return true;
               }
               return false;
           }
       });


*/


    }
    /*void getdata(){
        String em = name.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        name.setText(jsonObject.getString("Name"));
                        price.setText(jsonObject.getString("Price"));
                        des.setText(jsonObject.getString("Des"));
                        category.setText(jsonObject.getString("Category"));
                        String uri="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/images/".concat(jsonObject.getString("Image"));
                        Picasso.get().load(uri).into(imageView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getproduct.this,response, Toast.LENGTH_LONG).show();
                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getproduct.this, "Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email", em);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }*/
}