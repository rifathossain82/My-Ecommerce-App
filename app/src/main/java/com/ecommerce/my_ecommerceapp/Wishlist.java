package com.ecommerce.my_ecommerceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Wishlist extends Fragment {


    private List<String> l_id;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_images;
    private String file="myfile_extra";
    private My_adapter3 my_adapter3;

    String email;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/wishlish_search.php";
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wishlist, container, false);

        recyclerView=view.findViewById(R.id.recyclcerview_id_wishlist_items);

        l_id=new ArrayList<>();
        l_name=new ArrayList<>();
        l_price=new ArrayList<>();
        l_des=new ArrayList<>();
        l_images=new ArrayList<>();

//find email
        try {
            FileInputStream fin = getContext().openFileInput(file);
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

        wishlist_finder(email);

        return view;
    }

    void wishlist_finder(String email){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        l_id.add(jsonObject.getString("Id"));
                        l_name.add(jsonObject.getString("Name"));
                        l_price.add(jsonObject.getString("Price"));
                        l_des.add(jsonObject.getString("Des"));
                        l_images.add(jsonObject.getString("Image"));
                    }
                    my_adapter3=new My_adapter3(getContext(),l_id,l_name,l_price,l_des,l_images);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(my_adapter3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.contains("No Data found")){
                    Toast.makeText(getContext(), "You don't have any products of choice yet.", Toast.LENGTH_SHORT).show();
                }
                requestQueue.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email );
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }

}