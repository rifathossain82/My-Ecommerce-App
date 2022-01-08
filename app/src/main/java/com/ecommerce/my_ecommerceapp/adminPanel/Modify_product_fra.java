package com.ecommerce.my_ecommerceapp.adminPanel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ecommerce.my_ecommerceapp.Homepage;
import com.ecommerce.my_ecommerceapp.LoginPage;
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

public class Modify_product_fra extends Fragment {
    Button select, update, delete, search, clear;
    ImageView imageView;
    EditText name, des, price, search_id;
    TextView tv, id_1;
    Spinner spinner;
    private static final int Storage_permission_code = 4655;
    private int pic_image_request = 1;
    private Uri filepath;
    private Bitmap bitmap;
    String db_file = "https://acaulescent-signalm.000webhostapp.com/ecommerce_project/single_search.php";
    String delete_file = "https://acaulescent-signalm.000webhostapp.com/ecommerce_project/delete.php";
    String update_file = "https://acaulescent-signalm.000webhostapp.com/ecommerce_project/update.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modify_product_fra, container, false);
return view;
    }
}