package com.ecommerce.my_ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_adapter6_view_order extends RecyclerView.Adapter<My_adapter6_view_order.MyViewHolder> {

    private List<String> l_sl;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_images;
    private Context context;

    RecyclerView recyclerView;

    private String file="myfile_extra";

    CardView cardView;

    public My_adapter6_view_order(Context context, List<String> l_sl, List<String> l_name, List<String> l_price, List<String> l_des, List<String> l_images) {
        this.context = context;
        this.l_sl = l_sl;
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
        holder.id=l_sl.get(position);

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

            delete.setVisibility(View.GONE);

        }
    }

}
