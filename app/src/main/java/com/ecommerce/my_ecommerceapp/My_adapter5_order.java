package com.ecommerce.my_ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_adapter5_order extends RecyclerView.Adapter<My_adapter5_order.MyViewHolder> {

    private List<String> l_id;
    private List<String> l_name;
    private List<String> l_price;
    private List<String> l_status;
    private List<String> l_date;
    private Context context;
String e;
    RecyclerView recyclerView;

    private String file="myfile_extra";

    CardView cardView;
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete_order_list.php";
    String db_file3="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete_ordered_pro.php";


    public My_adapter5_order(Context context, List<String> l_name, List<String> l_price, List<String> l_status, List<String> l_date,List<String> l_id) {
        this.context = context;
        this.l_name = l_name;
        this.l_price = l_price;
        this.l_status = l_status;
        this.l_date = l_date;
        this.l_id = l_id;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.grid_items5_order,parent,false);
        cardView=view.findViewById(R.id.item_cardviewid5);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText("Products Name: "+l_name.get(position));
        holder.price.setText("Total Price: "+l_price.get(position));
        holder.status.setText("Status: "+l_status.get(position));
        holder.date.setText("Order Date: "+l_date.get(position));
        holder.id=l_id.get(position);

       /* String s=l_status.get(position);
        if(s.contains("Delivered")){
            holder.p_date.setVisibility(View.GONE);
            holder.cancel.setVisibility(View.GONE);
        }*/

        //find email
        try {
            FileInputStream fin =context.openFileInput(file);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            holder.email=temp;
            e=temp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.i=position;

      cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Details_ordered_product.class);
                intent.putExtra("id",l_id.get(position));
                intent.putExtra("email",e);
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


        TextView name;
        TextView price;
        TextView status;
        TextView date,p_date;
        Button cancel;
        String email,id;


        int i=0,p=0;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name_order_id);
            price=itemView.findViewById(R.id.price_order_id);
            status=itemView.findViewById(R.id.status_order_id);
            date=itemView.findViewById(R.id.o_date_order_id);
            p_date=itemView.findViewById(R.id.d_date_order_id);
            cancel=itemView.findViewById(R.id.cancel_order_id);


            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    l_id.remove(getAdapterPosition());
                    l_name.remove(getAdapterPosition());
                    l_price.remove(getAdapterPosition());
                    l_date.remove(getAdapterPosition());
                    l_status.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    orderList_delete(email,id);
                    ordered_pro_delete(email,id);
                }
            });
        }
    }
    void orderList_delete(String email,String id){
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
    void ordered_pro_delete(String email,String sl0){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file3, new Response.Listener<String>() {
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
                data.put("sl",sl0 );
                return data;
            }
        };
        requestQueue.add(stringRequest);

    }

}
