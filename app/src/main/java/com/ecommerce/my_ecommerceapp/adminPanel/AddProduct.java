package com.ecommerce.my_ecommerceapp.adminPanel;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.my_ecommerceapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class AddProduct extends AppCompatActivity {
    /*ImageView imageView;
    EditText name,des,price;
    TextView tv;
    Spinner spinner;
    private static final int Storage_permission_code=4655;
    private int pic_image_request=1;
    private Uri filepath;
    private Bitmap bitmap;
    public static final String upload_url="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/addProduct.php";
   */ @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

      /*  requeststoragepermission();

        imageView=findViewById(R.id.imageviewid);
        name=findViewById(R.id.nameid);
        price=findViewById(R.id.priceid);
        des=findViewById(R.id.descriptionid);
        tv=findViewById(R.id.outtext);
        spinner=findViewById(R.id.spinnerid);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_id);
        bottomNavigationView.setSelectedItemId(R.id.add_product_id);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Show_product_id:
                        startActivity(new Intent(getApplicationContext(),
                                getproduct.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.add_product_id:
                        return true;
                    //case R.id.modify_product_id:
                        //startActivity(new Intent(getApplicationContext(),
                        //        getproduct.class));
                       // overridePendingTransition(0,0);
                       // return true;
                }
                return false;
            }
        });*/

    }
  /*  private void requeststoragepermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Storage_permission_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == Storage_permission_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public void selectimage(View view) {
        ShowFileChooser();
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
        String name_1 =name.getText().toString().trim();
        String price_1 = price.getText().toString().trim();
        String des_1 = des.getText().toString().trim();
        String category =spinner.getSelectedItem().toString().trim();
        String path = getPath(filepath);
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(this, uploadId, upload_url).addFileToUpload(path, "upload").addParameter("t1", name_1).addParameter("t2", price_1).addParameter("t3", des_1).addParameter("t4", category)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(3)
                    .startUpload();

        } catch (Exception ex) {


        }

    }

    public void save(View view) {
        uploadImage();
    }*/
}