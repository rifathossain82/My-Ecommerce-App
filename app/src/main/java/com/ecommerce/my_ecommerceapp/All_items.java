package com.ecommerce.my_ecommerceapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class All_items extends Fragment {
Toolbar toolbar;
ImageButton searchView;
SearchView editText;
String name;



    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/getproduct.php";
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/getdata_single_char.php";
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
        View view= inflater.inflate(R.layout.fragment_all_items, container, false);


        recyclerView=view.findViewById(R.id.recyclcerview_id_all_items);
        toolbar=view.findViewById(R.id.toolbarID);
        editText=view.findViewById(R.id.edit_item_name);

        l_id=new ArrayList<>();
        l_name=new ArrayList<>();
        l_price=new ArrayList<>();
        l_des=new ArrayList<>();
        l_category=new ArrayList<>();
        l_images=new ArrayList<>();
        l_path=new ArrayList<>();

        getdata();


        editText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    getdata();
                }
                else{
                    getdata2(newText);
                }
                return true;
            }
        });


    return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_nav,menu);
       MenuItem menuItem=menu.findItem(R.id.search_id);
       SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //getdata2(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    void getdata(){
        l_id.clear();
        l_name.clear();
        l_price.clear();
        l_des.clear();
        l_category.clear();
        l_images.clear();
        l_path.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
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
                data.put("email", "em");
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }


    void getdata2(String name0){
        l_id.clear();
        l_name.clear();
        l_price.clear();
        l_des.clear();
        l_category.clear();
        l_images.clear();
        l_path.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file2, new Response.Listener<String>() {
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
                data.put("name", name0);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
