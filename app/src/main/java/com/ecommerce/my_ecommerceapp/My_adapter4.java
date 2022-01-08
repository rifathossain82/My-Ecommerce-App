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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_adapter4 extends RecyclerView.Adapter<My_adapter4.MyViewHolder> {

    private List<String> l_sl;
    private List<String> l_id;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_images;
    private Context context;
    Button tk;
    int price_,gp;

    RecyclerView recyclerView;
    private Recyclerview_interface recyclerview_interface;

    private String file="myfile_extra";

    CardView cardView;
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete_wishlist.php";
    String delete_url="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/remove_cart.php";
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/get_data_to_cart.php";

    public My_adapter4(Context context,List<String> l_sl, List<String> l_id, List<String> l_name, List<String> l_price, List<String> l_des, List<String> l_images,Recyclerview_interface recyclerview_interface,int price_) {
        this.context = context;
        this.l_sl = l_sl;
        this.l_id = l_id;
        this.l_name = l_name;
        this.l_price = l_price;
        this.l_des = l_des;
        this.l_images = l_images;
        this.recyclerview_interface=recyclerview_interface;
        this.price_=price_;
        gp=price_;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.grid_items4,parent,false);
        cardView=view.findViewById(R.id.item_cardviewid4);
        tk=view.findViewById(R.id.tk_id_add_to_cart);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(l_images.get(position)).into(holder.imageView);
        holder.name.setText(l_name.get(position));
        holder.price.setText("BDT: "+l_price.get(position)+" tk");
        holder.des.setText(l_des.get(position));

        holder.sl=l_sl.get(position);
        holder.id=l_id.get(position);
        holder.name0=l_name.get(position);
        holder.price0=l_price.get(position);
        holder.des0=l_des.get(position);
        holder.image0=l_images.get(position);
        holder.pp= Integer.parseInt(l_price.get(position));



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
        TextView remove,edit;

        String email,sl,id,name0,price0,des0,image0;
        int pp=0;


        int i=0,p=0;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.item_imageview_id4);
            name=itemView.findViewById(R.id.item_name_id4);
            price=itemView.findViewById(R.id.item_price_id4);
            des=itemView.findViewById(R.id.item_des_id4);
            remove=itemView.findViewById(R.id.remove_card4);
            edit=itemView.findViewById(R.id.edit_card4);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   recyclerview_interface.click_listener(email,sl,getAdapterPosition());
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Show_details_product.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name0);
                    intent.putExtra("price",price0);
                    intent.putExtra("des",des0);
                    intent.putExtra("image",image0);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                 }
            });
        }
    }
}
