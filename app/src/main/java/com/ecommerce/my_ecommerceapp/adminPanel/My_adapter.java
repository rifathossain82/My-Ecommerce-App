package com.ecommerce.my_ecommerceapp.adminPanel;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.my_ecommerceapp.All_products_home;
import com.ecommerce.my_ecommerceapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class My_adapter extends RecyclerView.Adapter<My_adapter.MyViewHolder> {

    private List<String> l_id;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_category;
    private List<String> l_images;
    private List<String> l_path;
    private Context context;
    CardView cardView;



    public My_adapter(Context context,List<String> l_id,List<String> l_name, List<String> l_price, List<String> l_des, List<String> l_category, List<String> l_images,List<String> l_path) {
        this.context = context;
        this.l_id = l_id;
        this.l_name = l_name;
        this.l_price = l_price;
        this.l_des = l_des;
        this.l_category = l_category;
        this.l_images = l_images;
        this.l_path = l_path;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.grid_items,parent,false);
        cardView=view.findViewById(R.id.item_cardviewid);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(l_images.get(position)).into(holder.imageView);
        holder.id.setText(l_id.get(position));
        holder.name.setText(l_name.get(position));
        holder.category.setText("Category: "+l_category.get(position));
        holder.price.setText("BDT: "+l_price.get(position)+"tk");
        holder.des.setText(l_des.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Modify_product.class);
                intent.putExtra("id",l_id.get(position));
                intent.putExtra("name",l_name.get(position));
                intent.putExtra("price",l_price.get(position));
                intent.putExtra("des",l_des.get(position));
                intent.putExtra("cat",l_category.get(position));
                intent.putExtra("image",l_images.get(position));
                intent.putExtra("path",l_path.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return l_name.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView id;
        TextView name;
        TextView price;
        TextView des;
        TextView category;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.item_imageview_id);
            id=itemView.findViewById(R.id.item_id_id);
            name=itemView.findViewById(R.id.item_name_id);
            price=itemView.findViewById(R.id.item_price_id);
            des=itemView.findViewById(R.id.item_des_id);
            category=itemView.findViewById(R.id.item_category_id);


        }
    }
}
