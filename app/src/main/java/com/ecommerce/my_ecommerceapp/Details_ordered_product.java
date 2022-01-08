package com.ecommerce.my_ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Details_ordered_product extends AppCompatActivity {
    private List<String> l_sl;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_images;
    private String file="myfile_extra";
    private My_adapter6_view_order my_adapter6_view_order;

    String email,id;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/show_details_ordered_product.php";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ordered_product);
        recyclerView=findViewById(R.id.recyclcerview_id_ordered_list);

        l_sl=new ArrayList<>();
        l_name=new ArrayList<>();
        l_price=new ArrayList<>();
        l_des=new ArrayList<>();
        l_images=new ArrayList<>();

        id=getIntent().getStringExtra("id");
        email=getIntent().getStringExtra("email");

        product_finder(id,email);

    }
    void product_finder(String id,String email){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        l_sl.add(jsonObject.getString("Sl"));
                        l_name.add(jsonObject.getString("Name"));
                        l_price.add(jsonObject.getString("Price"));
                        l_des.add(jsonObject.getString("Details"));
                        l_images.add(jsonObject.getString("Image"));
                      }
                    my_adapter6_view_order=new My_adapter6_view_order(getApplicationContext(),l_sl,l_name,l_price,l_des,l_images);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(my_adapter6_view_order);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.contains("No Data found")){
                    Toast.makeText(Details_ordered_product.this, "No Data Found.", Toast.LENGTH_SHORT).show();
                }
                requestQueue.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("sl",id );
                data.put("email",email );
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }
}