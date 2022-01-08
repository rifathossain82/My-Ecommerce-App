package com.ecommerce.my_ecommerceapp.adminPanel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ecommerce.my_ecommerceapp.My_adapter5_order;
import com.ecommerce.my_ecommerceapp.R;

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


public class Admin_view_order extends Fragment {
    private List<String> l_name;
    private List<String> l_phone;
    private List<String> l_address;
    private List<String> l_d_place;
    private List<String> l_pname;
    private List<String> l_price;
    private List<String> l_status;
    private List<String> l_date;
    private List<String> l_id;
    private List<String> l_email;
    private String file="myfile_extra";
    private My_adapter7_vieworder_admin my_adapter7_vieworder_admin;

    String email;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/all_order_admin.php";
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/order_spacific_status.php";
    RecyclerView recyclerView;
    Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_view_order, container, false);

        recyclerView=view.findViewById(R.id.recyclcerview_id_admin_view_order_items);

        l_name=new ArrayList<>();
        l_phone=new ArrayList<>();
        l_address=new ArrayList<>();
        l_d_place=new ArrayList<>();
        l_pname=new ArrayList<>();
        l_price=new ArrayList<>();
        l_status=new ArrayList<>();
        l_date=new ArrayList<>();
        l_id=new ArrayList<>();
        l_email=new ArrayList<>();

        spinner=view.findViewById(R.id.spinnerid_ad_view);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItemId()==0){
                    order_finder();
                }
                else {
                    order_finder2(spinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

      //  order_finder();

        return view;
    }
    void order_finder(){
        l_name.clear();
        l_phone.clear();
        l_address.clear();
        l_d_place.clear();
        l_pname.clear();
        l_price.clear();
        l_status.clear();
        l_date.clear();
        l_id.clear();
        l_email.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        l_name.add(jsonObject.getString("Name"));
                        l_phone.add(jsonObject.getString("Phone"));
                        l_address.add(jsonObject.getString("Address"));
                        l_d_place.add(jsonObject.getString("D_Place"));
                        l_pname.add(jsonObject.getString("Product_name"));
                        l_price.add(jsonObject.getString("Ammount"));
                        l_status.add(jsonObject.getString("Status"));
                        l_date.add(jsonObject.getString("Date"));
                        l_id.add(jsonObject.getString("Id"));
                        l_email.add(jsonObject.getString("Email"));
                    }
                    my_adapter7_vieworder_admin=new My_adapter7_vieworder_admin(getContext(),l_name,l_phone,l_address,l_d_place,l_pname,l_price,l_status,l_date,l_id,l_email);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(my_adapter7_vieworder_admin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.contains("No Data found")){
                    Toast.makeText(getContext(), "You haven't purchased any products yet.", Toast.LENGTH_SHORT).show();
                }
                requestQueue.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed To Load.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }
    void order_finder2(String s){
        l_name.clear();
        l_phone.clear();
        l_address.clear();
        l_d_place.clear();
        l_pname.clear();
        l_price.clear();
        l_status.clear();
        l_date.clear();
        l_id.clear();
        l_email.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        l_name.add(jsonObject.getString("Name"));
                        l_phone.add(jsonObject.getString("Phone"));
                        l_address.add(jsonObject.getString("Address"));
                        l_d_place.add(jsonObject.getString("D_Place"));
                        l_pname.add(jsonObject.getString("Product_name"));
                        l_price.add(jsonObject.getString("Ammount"));
                        l_status.add(jsonObject.getString("Status"));
                        l_date.add(jsonObject.getString("Date"));
                        l_id.add(jsonObject.getString("Id"));
                        l_email.add(jsonObject.getString("Email"));
                    }
                    my_adapter7_vieworder_admin=new My_adapter7_vieworder_admin(getContext(),l_name,l_phone,l_address,l_d_place,l_pname,l_price,l_status,l_date,l_id,l_email);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(my_adapter7_vieworder_admin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.contains("No Data found")){
                    Toast.makeText(getContext(), "You haven't purchased any products yet.", Toast.LENGTH_SHORT).show();
                }
                requestQueue.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed To Load.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("t1",s);
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }
}