package com.ecommerce.my_ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_adapter3 extends RecyclerView.Adapter<My_adapter3.MyViewHolder> {

    private List<String> l_id;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_images;
    private Context context;

    RecyclerView recyclerView;

    private String file="myfile_extra";

    CardView cardView;
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete_wishlist.php";


    public My_adapter3(Context context,List<String> l_id, List<String> l_name, List<String> l_price, List<String> l_des, List<String> l_images) {
        this.context = context;
        this.l_id = l_id;
        this.l_name = l_name;
        this.l_price = l_price;
        this.l_des = l_des;
        this.l_images = l_images;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.grid_items3,parent,false);
        cardView=view.findViewById(R.id.item_cardviewid3);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(l_images.get(position)).into(holder.imageView);
        holder.name.setText(l_name.get(position));
        holder.price.setText("BDT: "+l_price.get(position)+" tk");
        holder.des.setText(l_des.get(position));
        holder.id=l_id.get(position);

        //find email
        try {
            FileInputStream fin =context.openFileInput(file);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            holder.email=temp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.i=position;

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Show_details_product.class);
                intent.putExtra("id",l_id.get(position));
                intent.putExtra("name",l_name.get(position));
                intent.putExtra("price",l_price.get(position));
                intent.putExtra("des",l_des.get(position));
                intent.putExtra("image",l_images.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });
    }
    @Override
    public int getItemCount() {
        return l_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name;
        TextView price;
        TextView des;
        ImageView delete;

        String email,id;


        int i=0,p=0;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.item_imageview_id3);
            name=itemView.findViewById(R.id.item_name_id3);
            price=itemView.findViewById(R.id.item_price_id3);
            des=itemView.findViewById(R.id.item_des_id3);
            delete=itemView.findViewById(R.id.delete_wishlist_item_id);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wishlist_delete(email,id);
                    l_id.remove(i);
                    l_name.remove(i);
                    l_price.remove(i);
                    l_des.remove(i);
                    l_images.remove(i);
                    notifyItemRemoved(i);
                }
            });
        }
    }
    void wishlist_delete(String email,String id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                requestQueue.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No Data Deleted.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email );
                data.put("id",id );
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }
}
