package com.ecommerce.my_ecommerceapp.adminPanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ecommerce.my_ecommerceapp.All_products_home;
import com.ecommerce.my_ecommerceapp.R;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Modify_product extends AppCompatActivity {
    Button select,update,delete,search,clear;
    ImageView imageView;
    EditText name,des,price,search_id;
    TextView tv,id_1;
    Spinner spinner;
    private static final int Storage_permission_code=4655;
    private int pic_image_request=1;
    private Uri filepath;
    private Bitmap bitmap;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/single_search.php";
    String delete_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete.php";
    String update_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/update.php";
    String update_without_img="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/update_without_image.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_product);

        requeststoragepermission();

        imageView=findViewById(R.id.imageviewid_1);
        id_1=findViewById(R.id.id_id);
        name=findViewById(R.id.nameid_1);
        price=findViewById(R.id.priceid_1);
        des=findViewById(R.id.descriptionid_1);
        tv=findViewById(R.id.outtext_1);
        spinner=findViewById(R.id.spinnerid_1);
        select=findViewById(R.id.select_imageid);
        update=findViewById(R.id.update_id);
        delete=findViewById(R.id.delete_id);
        search_id=findViewById(R.id.id_search_id);
        search=findViewById(R.id.search_id_button);
        clear=findViewById(R.id.clear_id_modify);

        id_1.setText(getIntent().getStringExtra("id"));
        name.setText(getIntent().getStringExtra("name"));
        price.setText(getIntent().getStringExtra("price"));
        des.setText(getIntent().getStringExtra("des"));
        spinner.setSelection(getIndex(spinner,getIntent().getStringExtra("cat")));
        Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_id.getText().toString().isEmpty()){
                    Toast.makeText(Modify_product.this, "Product Id is empty!", Toast.LENGTH_SHORT).show();
                    search_id.requestFocus();
                }
                else {
                    String id = search_id.getText().toString();
                    RequestQueue requestQueue = Volley.newRequestQueue(Modify_product.this.getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    id_1.setText(jsonObject.getString("Id"));
                                    name.setText(jsonObject.getString("Name"));
                                    price.setText(jsonObject.getString("Price"));
                                    des.setText(jsonObject.getString("Des"));
                                    String cat=jsonObject.getString("Category");
                                    spinner.setSelection(getIndex(spinner,cat));
                                    // spinner.setsele(jsonObject.getString("Category"));
                                    String uri="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/images/".concat(jsonObject.getString("Image"));
                                    Picasso.get().load(uri).into(imageView);
                                    tv.setText(jsonObject.getString("Image Path"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(Modify_product.this,response, Toast.LENGTH_LONG).show();
                            requestQueue.stop();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Modify_product.this, "Access Denied.", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<String, String>();
                            data.put("id", id);
                            return data;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFileChooser();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(name.getText().toString().isEmpty()){
                    Toast.makeText(Modify_product.this, "Product Name is Empty!", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }
                else if(name.getText().toString().length()>30){
                    Toast.makeText(Modify_product.this, "Product Name must be under 30 character!", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }
                else if(price.getText().toString().isEmpty()){
                    Toast.makeText(Modify_product.this, "Product Price is Empty!", Toast.LENGTH_SHORT).show();
                    price.requestFocus();
                }
                else if(price.getText().toString().length()>10){
                    Toast.makeText(Modify_product.this, "Product price must be under 10 character!", Toast.LENGTH_SHORT).show();
                    price.requestFocus();
                }
                else if(des.getText().toString().isEmpty()){
                    Toast.makeText(Modify_product.this, "Product Description is Empty!", Toast.LENGTH_SHORT).show();
                    des.requestFocus();
                }
                else if(des.getText().toString().length()>150){
                    Toast.makeText(Modify_product.this, "Product Description must be under 150 character!", Toast.LENGTH_SHORT).show();
                    des.requestFocus();
                }
                else{
                   if(tv.getText().toString().isEmpty()){
                       update_data_without_image();
                   }
                   else{
                       uploadImage();
                   }
                }


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = id_1.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(Modify_product.this.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, delete_file, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Modify_product.this,response, Toast.LENGTH_LONG).show();
                        requestQueue.stop();
                        if (response.contains("Delete Success")) {
                            search_id.setText("");
                            id_1.setText("");
                            name.setText("");
                            price.setText("");
                            des.setText("");
                            tv.setText("");
                            spinner.setSelection(0);
                            imageView.setImageResource(R.drawable.bg_big1);
                            search_id.requestFocus();
                        }
                        if (response.contains("Delete Failed")) {

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Modify_product.this, "Access Denied.", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<String, String>();
                        data.put("id", id);
                        return data;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_id.setText("");
                id_1.setText("");
                name.setText("");
                price.setText("");
                des.setText("");
                tv.setText("");
                spinner.setSelection(0);
                imageView.setImageResource(R.drawable.bg_big1);
                search_id.requestFocus();
            }
        });

    }
    private int getIndex(Spinner spinner, String cat) {
        for(int i=0;i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(cat)){
                return i;
            }
        }
        return 0;
    }

    private void requeststoragepermission(){

        if (ContextCompat.checkSelfPermission(Modify_product.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(Modify_product.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(Modify_product.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Storage_permission_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == Storage_permission_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Modify_product.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(Modify_product.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void ShowFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), pic_image_request);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pic_image_request && data != null && data.getData() != null) {

            filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
                tv.setText(filepath.toString());
                // Toast.makeText(getApplicationContext(),getPath(filepath),Toast.LENGTH_LONG).show();
            } catch (Exception ex) {

            }
        }
    }
    private String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
        );
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void uploadImage() {
        String id = id_1.getText().toString();
        String name_1 =name.getText().toString().trim();
        String price_1 = price.getText().toString().trim();
        String des_1 = des.getText().toString().trim();
        String category =spinner.getSelectedItem().toString().trim();
        String path=getPath(filepath);
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(this, uploadId, update_file).addFileToUpload(path, "upload").addParameter("t1", name_1).addParameter("t2", price_1).addParameter("t3", des_1).addParameter("t4", category).addParameter("t5", tv.getText().toString().trim()).addParameter("t6", id)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(3)
                    .startUpload();

        } catch (Exception ex) {


        }

    }
    void update_data_without_image(){
        String id = id_1.getText().toString();
        String name_1 =name.getText().toString().trim();
        String price_1 = price.getText().toString().trim();
        String des_1 = des.getText().toString().trim();
        String category =spinner.getSelectedItem().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(Modify_product.this.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_without_img, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Modify_product.this,response, Toast.LENGTH_LONG).show();
                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Modify_product.this, "Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("t5", id);
                data.put("t1", name_1);
                data.put("t2", price_1);
                data.put("t3", des_1);
                data.put("t4", category);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

}