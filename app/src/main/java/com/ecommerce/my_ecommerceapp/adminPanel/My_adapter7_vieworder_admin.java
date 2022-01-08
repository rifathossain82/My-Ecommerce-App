package com.ecommerce.my_ecommerceapp.adminPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
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
import com.ecommerce.my_ecommerceapp.Details_ordered_product;
import com.ecommerce.my_ecommerceapp.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_adapter7_vieworder_admin extends RecyclerView.Adapter<My_adapter7_vieworder_admin.MyViewHolder> {

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
    private Context context;
String e;
    RecyclerView recyclerView;

    private String file="myfile_extra";

    CardView cardView;
    String db_file2="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/update_order_list.php";
    String db_file3="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete_ordered_pro.php";


    public My_adapter7_vieworder_admin(Context context,List<String> l_name, List<String> l_phone, List<String> l_address, List<String> l_d_place, List<String> l_pname, List<String> l_price, List<String> l_status, List<String> l_date, List<String> l_id, List<String> l_email) {
        this.context = context;
        this.l_name = l_name;
        this.l_phone = l_phone;
        this.l_address = l_address;
        this.l_d_place = l_d_place;
        this.l_pname = l_pname;
        this.l_price = l_price;
        this.l_status = l_status;
        this.l_date = l_date;
        this.l_id = l_id;
        this.l_email = l_email;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.grid_items5_order_items_admin,parent,false);
        cardView=view.findViewById(R.id.item_cardviewid_ad);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText("Bill No.: "+l_id.get(position));
        holder.name.setText("Buyer Name: "+l_name.get(position));
        holder.phone.setText("Phone Number: "+l_phone.get(position));
        holder.address.setText("Address: "+l_address.get(position));
        holder.d_place.setText("Receiving Place: "+l_d_place.get(position));
        holder.p_name.setText("Products Name: "+l_pname.get(position));
        holder.price.setText("Total Price: "+l_price.get(position));
        holder.date.setText("Order Date: "+l_date.get(position));
        holder.status.setSelection(getIndex(holder.status,l_status.get(position)));
        e=l_email.get(position);
        //holder.status.setSelection(Integer.parseInt(l_status.get(position)));

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


        TextView id,name,phone,address,d_place,p_name,price,date;
        Spinner status;
        String email;


        int i=0,p=0;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.id_ad_item);
            name=itemView.findViewById(R.id.name_ad_item);
            phone=itemView.findViewById(R.id.phone_ad_item);
            address=itemView.findViewById(R.id.address_ad_item);
            d_place=itemView.findViewById(R.id.d_place_ad_item);
            p_name=itemView.findViewById(R.id.pname_ad_item);
            price=itemView.findViewById(R.id.price_ad_item);
            date=itemView.findViewById(R.id.o_date_ad_item);
            status=itemView.findViewById(R.id.spinnerid_ad_item);


            status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    update_orderList(status.getSelectedItem().toString(),l_id.get(getAdapterPosition()),e);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
    void update_orderList(String ns,String id,String email){
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
                data.put("t1",id );
                data.put("t2",email );
                data.put("t3",ns );
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
    private int getIndex(Spinner spinner, String cat) {
        for(int i=0;i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(cat)){
                return i;
            }
        }
        return 0;
    }

}
