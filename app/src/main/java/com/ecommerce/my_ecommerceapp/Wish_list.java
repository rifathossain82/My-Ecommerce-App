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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wish_list extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        /*recyclerView=findViewById(R.id.recyclcerview_id_wishlist_items);

        l_name=new ArrayList<>();
        l_price=new ArrayList<>();
        l_des=new ArrayList<>();
        l_images=new ArrayList<>();

        l_name.add(getIntent().getStringExtra("name"));
        l_price.add(getIntent().getStringExtra("price"));
        l_des.add(getIntent().getStringExtra("des"));
        l_images.add(getIntent().getStringExtra("image"));

        my_adapter3=new My_adapter3(getApplicationContext(),l_name,l_price,l_des,l_images);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(my_adapter3);*/

        try {
            FileInputStream fin = getApplicationContext().openFileInput(file);
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
    }


}