package com.ecommerce.my_ecommerceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class Electronics_items extends Fragment {
    String search_by_item_url="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/search_by_item.php";
    RecyclerView recyclerView;
    private List<String> l_id;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_category;
    private List<String> l_images;
    private List<String> l_path;
    private My_adapter2 my_adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_electronics_items, container, false);

        recyclerView=view.findViewById(R.id.recyclcerview_id_electronics_items);

        l_id=new ArrayList<>();
        l_name=new ArrayList<>();
        l_price=new ArrayList<>();
        l_des=new ArrayList<>();
        l_category=new ArrayList<>();
        l_images=new ArrayList<>();
        l_path=new ArrayList<>();

        show_data("ELECTRONICS");

        return view;
    }

    void show_data(String x){
        l_id.clear();
        l_name.clear();
        l_price.clear();
        l_des.clear();
        l_category.clear();
        l_images.clear();
        l_path.clear();



        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, search_by_item_url, new Response.Listener<String>() {
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
                        l_category.add(jsonObject.getString("Category"));
                        l_path.add(jsonObject.getString("Image Path"));
                        String uri="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/images/".concat(jsonObject.getString("Image"));
                        l_images.add(uri);
                    }
                    my_adapter2=new My_adapter2(getContext(),l_id,l_name,l_price,l_des,l_category,l_images,l_path);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(my_adapter2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("cat",x);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}