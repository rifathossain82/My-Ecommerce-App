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

import com.ecommerce.my_ecommerceapp.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class Add_product_fra extends Fragment {
Button select,save,clear;
    ImageView imageView;
    EditText name,des,price;
    TextView tv;
    Spinner spinner;
    private static final int Storage_permission_code=4655;
    private int pic_image_request=1;
    private Uri filepath;
    private Bitmap bitmap;
    public static final String upload_url="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/addProduct.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_product_fra, container, false);

        requeststoragepermission();

        imageView=view.findViewById(R.id.imageviewid);
        name=view.findViewById(R.id.nameid);
        price=view.findViewById(R.id.priceid);
        des=view.findViewById(R.id.descriptionid);
        tv=view.findViewById(R.id.outtext);
        spinner=view.findViewById(R.id.spinnerid);
        select=view.findViewById(R.id.select_imageid);
        save=view.findViewById(R.id.save_id);
        clear=view.findViewById(R.id.clear_id);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFileChooser();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Product Name is Empty!", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }
                else if(name.getText().toString().length()>30){
                    Toast.makeText(getContext(), "Product Name must be under 30 character!", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }
                else if(price.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Product Price is Empty!", Toast.LENGTH_SHORT).show();
                    price.requestFocus();
                }
                else if(price.getText().toString().length()>10){
                    Toast.makeText(getContext(), "Product price must be under 10 character!", Toast.LENGTH_SHORT).show();
                    price.requestFocus();
                }
                else if(des.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Product Description is Empty!", Toast.LENGTH_SHORT).show();
                    des.requestFocus();
                }
                else if(des.getText().toString().length()>150){
                    Toast.makeText(getContext(), "Product Description must be under 150 character!", Toast.LENGTH_SHORT).show();
                    des.requestFocus();
                }
                else if(tv.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Product Image is Empty!", Toast.LENGTH_SHORT).show();
                    select.requestFocus();
                }
                else {
                    uploadImage();
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                price.setText("");
                des.setText("");
                tv.setText("");
                spinner.setSelection(0);
                imageView.setImageResource(R.drawable.bg_big1);
                name.requestFocus();
            }
        });

        return view;
    }
    private void requeststoragepermission(){

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Storage_permission_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == Storage_permission_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
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

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
                tv.setText(filepath.toString());
                // Toast.makeText(getApplicationContext(),getPath(filepath),Toast.LENGTH_LONG).show();
            } catch (Exception ex) {

            }
        }
    }
    private String getPath(Uri uri) {

        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
        );
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void uploadImage() {
        String name_1 =name.getText().toString().trim();
        String price_1 = price.getText().toString().trim();
        String des_1 = des.getText().toString().trim();
        String category =spinner.getSelectedItem().toString().trim();
        String path = getPath(filepath);
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, upload_url).addFileToUpload(path, "upload").addParameter("t1", name_1).addParameter("t2", price_1).addParameter("t3", des_1).addParameter("t4", category).addParameter("t5", tv.getText().toString().trim())
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(3)
                    .startUpload();

        } catch (Exception ex) {


        }

    }

}