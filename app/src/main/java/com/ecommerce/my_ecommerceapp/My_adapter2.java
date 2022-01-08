package com.ecommerce.my_ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.ecommerce.my_ecommerceapp.adminPanel.Modify_product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_adapter2 extends RecyclerView.Adapter<My_adapter2.MyViewHolder> implements Filterable {

    private List<String> l_id;
    private List<String> l_name;
    private List<String> name_list;
    private List<String> l_price;
    private List<String> l_des;
    private List<String> l_category;
    private List<String> l_images;
    private List<String> l_path;
    private Context context;

    private String file="myfile_extra";
    int e=0;

    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/wishlist_insert.php";
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete_wishlist.php";
    String db_file3="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/wishlist_select_icon.php";

    CardView cardView;


    public My_adapter2(Context context, List<String> l_id, List<String> l_name, List<String> l_price, List<String> l_des, List<String> l_category, List<String> l_images, List<String> l_path) {
        this.context = context;
        this.l_id = l_id;
        this.l_name = l_name;
        this.l_price = l_price;
        this.l_des = l_des;
        this.l_category = l_category;
        this.l_images = l_images;
        this.l_path = l_path;
        this.name_list=new ArrayList<>(l_name);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.grid_items2,parent,false);
        cardView=view.findViewById(R.id.item_cardviewid2);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(l_images.get(position)).into(holder.imageView);
        holder.name.setText(l_name.get(position));
        holder.price.setText("BDT: "+l_price.get(position)+" tk");
        holder.des.setText(l_des.get(position));

        holder.id_=l_id.get(position);
        holder.name_=l_name.get(position);
        holder.price_=l_price.get(position);
        holder.des_=l_des.get(position);
        holder.image_=l_images.get(position);

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
        /*int x=wishlist_finder(holder.email,holder.id_);
        if (x==1){
            holder.wishlist.setImageResource(R.drawable.ic_wishlist_ok);
            i=1;
        }*/


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Show_details_product.class);
                intent.putExtra("id",l_id.get(position));
                intent.putExtra("name",l_name.get(position));
                intent.putExtra("price",l_price.get(position));
                intent.putExtra("des",l_des.get(position));
                intent.putExtra("cat",l_category.get(position));
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

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filterlist=new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filterlist.addAll(name_list);
            }
            else{
                for(String name:name_list){
                    if(name.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterlist.add(name);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=filterlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            name_list.clear();
            name_list.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name;
        TextView price;
        TextView des;
        ImageView wishlist;
        String email, id_,name_,price_,des_,image_;
        int i=0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.item_imageview_id2);
            name=itemView.findViewById(R.id.item_name_id2);
            price=itemView.findViewById(R.id.item_price_id2);
            des=itemView.findViewById(R.id.item_des_id2);
            wishlist=itemView.findViewById(R.id.wishlist_id);

            wishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(i==0){
                        wishlist.setImageResource(R.drawable.ic_wishlist_ok);

                        //search duplicate
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file3, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.contains("True")){
                                    }
                                    if(response.contains("False")){
                                        wishlist_create(email,id_,name_,price_,des_,image_);
                                    }
                                    requestQueue.stop();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "Failed to select favourite.", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                protected Map<String, String> getParams() {
                                    Map<String, String> data = new HashMap<String, String>();
                                    data.put("email",email );
                                    data.put("id",id_ );
                                    return data;
                                }
                            };
                            requestQueue.add(stringRequest);
                        Toast.makeText(v.getContext(), "Item added to wishlist.  ", Toast.LENGTH_SHORT).show();
                        i=1;
                    }
                    else{
                        wishlist.setImageResource(R.drawable.ic_wishlist);
                        wishlist_delete(email,id_);
                        Toast.makeText(v.getContext(), "Item removed from wishlist.  ", Toast.LENGTH_SHORT).show();
                        i=0;
                    }

                }
            });
        }
    }
     void wishlist_create(String email,String id,String name,String price,String des,String image){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No Data Added.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email );
                data.put("id",id );
                data.put("name", name);
                data.put("price", price);
                data.put("des", des);
                data.put("image", image);
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }
    void wishlist_delete(String email,String id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
    int wishlist_finder(String email,String id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("True")){
                    e=0;
                }
                if(response.contains("False")){
                    e=1;
                }
                requestQueue.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Failed to select favourite.", Toast.LENGTH_SHORT).show();
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
        return e;
    }
}
