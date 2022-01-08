package com.ecommerce.my_ecommerceapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ecommerce.my_ecommerceapp.adminPanel.Modify_product;
import com.ecommerce.my_ecommerceapp.adminPanel.My_adapter;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class UserInformation extends Fragment {
    DatePickerDialog.OnDateSetListener setListener1;
    String update_pass;
    String path0;
    private Dialog dialog,dialog1,dialog2,dialog3,dialog4,dialog5;
    private static final int Storage_permission_code=4655;
    private int pic_image_request=1;
    private Uri filepath;
    private Bitmap bitmap;
    String uname,upass,umobile,uemail,ugender,udateofbirth,uimage;
    private String file="myfile_extra";
    CircleImageView circleImageView;
    ImageView imageView;
    TextView name,password,mobile,email,gender,dateOfBirth,save;
    String db_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/user_info.php";
    String update_without_img="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/user_update.php";
    String update_pass_user="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/update_pass_user.php";
    String upload_file="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/user_update_image.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_information, container, false);

  try {
      requeststoragepermission();
      circleImageView = view.findViewById(R.id.profile_image);
      imageView = view.findViewById(R.id.changeimage_id);
      name = view.findViewById(R.id.name_user);
      password = view.findViewById(R.id.password_user);
      mobile = view.findViewById(R.id.mobile_user);
      email = view.findViewById(R.id.email_user);
      gender = view.findViewById(R.id.gender_user);
      dateOfBirth = view.findViewById(R.id.dateOfBirth_user);
      save = view.findViewById(R.id.save_user);
      save.setVisibility(View.GONE);

      //dialog code

      dialog = new Dialog(getContext());
      dialog1 = new Dialog(getContext());
      dialog2 = new Dialog(getContext());


      //end dialog code


      //find email
      try {
          FileInputStream fin = getContext().openFileInput(file);
          int c;
          String temp = "";
          while ((c = fin.read()) != -1) {
              temp = temp + Character.toString((char) c);
          }
          uemail = temp;
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }

      load_data(uemail);

      //set calendar

      Calendar calendar=Calendar.getInstance();
      final int year=calendar.get(Calendar.YEAR);
      final int month=calendar.get(Calendar.MONTH);
      final int day=calendar.get(Calendar.DAY_OF_MONTH);

      imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ShowFileChooser();

          }
      });

      save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(path0==null){
                  update_data_without_image();
              }
              else{
                  uploadImage();
              }
          }
      });

      name.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openFirstDialog();
          }
      });
      password.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              change_pass();

          }
      });
      mobile.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              check_pass();
          }
      });
      email.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              check_pass2();
          }
      });

      gender.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              gender_set();
          }
      });

      dateOfBirth.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              DatePickerDialog datePickerDialog=new DatePickerDialog(
                      getContext()
                      ,android.R.style.Theme_Holo_Dialog_MinWidth
                      ,setListener1,year,month,day
              );
              datePickerDialog.getWindow().setBackgroundDrawable(new
                      ColorDrawable(Color.TRANSPARENT));
              datePickerDialog.show();
          }
      });
      setListener1=new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, int year, int month, int day) {
              month=month+1;
              String date=""+day+"-"+month+"-"+year;
              dateOfBirth.setText(date);
              save.setVisibility(View.VISIBLE);



          }
      };

      //end set on click listener

  }catch (Exception ex){
      Toast.makeText(getContext(), "Get Exception.", Toast.LENGTH_SHORT).show();
  }
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
            path0=filepath.toString();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath);
                circleImageView.setImageBitmap(bitmap);
                save.setVisibility(View.VISIBLE);
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

    void load_data(String email0){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        uname=jsonObject.getString("Name");
                        upass=jsonObject.getString("Password");
                        umobile=jsonObject.getString("Mobile Number");
                        uemail=jsonObject.getString("Email");
                        ugender=jsonObject.getString("Gender");
                        udateofbirth=jsonObject.getString("DateOfBirth");
                        String uri=jsonObject.getString("Image");
                        if(uri.isEmpty()){
                            uimage="";
                        }
                        else{
                            uimage="https://acaulescent-signalm.000webhostapp.com/ecommerce_project/images/".concat(uri);
                        }

                    }
                    name.setText(uname);
                    mobile.setText(umobile);
                    email.setText(uemail);
                    gender.setText(ugender);
                    dateOfBirth.setText(udateofbirth);


                    if(uname.isEmpty()){
                        name.setText("Not Set");
                    }
                    if(ugender.isEmpty()){
                        gender.setText("Not Set");
                    }
                    if(udateofbirth.isEmpty()){
                        dateOfBirth.setText("Not Set");
                    }
                    if(uimage.isEmpty()){
                        Toast.makeText(getContext(), "User Image is Empty!!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Picasso.get().load(uimage).into(circleImageView);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email",email0);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    //custom dialog method 1

    private void openFirstDialog(){
        dialog.setContentView(R.layout.name_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog.findViewById(R.id.imageview_close1);
        Button buttonOk=dialog.findViewById(R.id.button_ok1);

        EditText name_p=dialog.findViewById(R.id.edit_name_1);

       imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
             }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name_p.getText().toString().isEmpty()){
                    name.setText("Not Set");
                }
                else {
                    name.setText(name_p.getText().toString());
                    save.setVisibility(View.VISIBLE);
                }

              dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void change_pass(){
        dialog.setContentView(R.layout.change_pass_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog.findViewById(R.id.imageview_close2);
        Button buttonOk=dialog.findViewById(R.id.button_ok2);

        EditText pass=dialog.findViewById(R.id.edit_pass_2);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().isEmpty()){

                }
                else {
                    if(pass.getText().toString().contains(upass)){
                        new_pass();
                    }
                    else{
                        Toast.makeText(getContext(), "Wrong Password.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void check_pass(){
        dialog.setContentView(R.layout.change_pass_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog.findViewById(R.id.imageview_close2);
        Button buttonOk=dialog.findViewById(R.id.button_ok2);

        EditText pass=dialog.findViewById(R.id.edit_pass_2);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Null Password.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(pass.getText().toString().contains(upass)){
                        change_mobile();
                    }
                    else{
                        Toast.makeText(getContext(), "Wrong Password.", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();

            }
        });
        dialog.show();

    }
    private void check_pass2(){
        dialog.setContentView(R.layout.change_pass_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog.findViewById(R.id.imageview_close2);
        Button buttonOk=dialog.findViewById(R.id.button_ok2);

        EditText pass=dialog.findViewById(R.id.edit_pass_2);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Null Password.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(pass.getText().toString().contains(upass)){
                        change_email();
                    }
                    else {
                        Toast.makeText(getContext(), "Wrong Password.", Toast.LENGTH_SHORT).show();
                    }
                }

                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void  new_pass(){
        dialog1.setContentView(R.layout.new_pass_layout);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog1.findViewById(R.id.imageview_close3);
        Button buttonOk=dialog1.findViewById(R.id.button_ok3);

        EditText npass=dialog1.findViewById(R.id.edit_new_pass_3);
        EditText cpass=dialog1.findViewById(R.id.edit_con_pass_3);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(npass.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "New Password is Empty!", Toast.LENGTH_SHORT).show();
                    npass.requestFocus();
                }
                else if(cpass.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Confirm Password is Empty!", Toast.LENGTH_SHORT).show();
                    cpass.requestFocus();
                }
                else if(npass.getText().toString().length()<6 || npass.getText().toString().length()>12){
                    Toast.makeText(getContext(), "Password must be 6 to 12 letters.", Toast.LENGTH_SHORT).show();
                    npass.requestFocus();
                }
                else if(cpass.getText().toString().length()<6 || cpass.getText().toString().length()>12){
                    Toast.makeText(getContext(), "Password must be 6 to 12 letters.", Toast.LENGTH_SHORT).show();
                    cpass.requestFocus();
                }
                else if(!npass.getText().toString().contains(cpass.getText().toString())){
                    Toast.makeText(getContext(), "Password Not Matching!", Toast.LENGTH_SHORT).show();
                    npass.setText("");
                    cpass.setText("");
                    npass.requestFocus();
                }
                else {
                    update_pass=npass.getText().toString();
                    update_p();
                    dialog1.dismiss();
                }


            }
        });
        dialog1.show();

    }
    private void change_mobile(){
        dialog2.setContentView(R.layout.change_mobile_layout);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog2.findViewById(R.id.imageview_close4);
        Button buttonOk=dialog2.findViewById(R.id.button_ok4);

        EditText mo=dialog2.findViewById(R.id.edit_mobile_4);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mo.getText().toString().isEmpty()){
                   mo.setText("Not Set");
                }
                else if(mo.getText().toString().length()!=11){
                    Toast.makeText(getContext(), "Invalid Mobile Number.", Toast.LENGTH_SHORT).show();
                    mo.requestFocus();
                }
                else {
                    mobile.setText(mo.getText().toString());
                    save.setVisibility(View.VISIBLE);
                }

                dialog2.dismiss();
            }
        });
        dialog2.show();

    }
    private void change_email(){
        dialog1.setContentView(R.layout.change_email_layout);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog1.findViewById(R.id.imageview_close5);
        Button buttonOk=dialog1.findViewById(R.id.button_ok5);

        EditText em=dialog1.findViewById(R.id.edit_mobile_5);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(em.getText().toString().isEmpty()){
                    email.setText("Not Set");
                    dialog1.dismiss();
                }
                else if(!em.getText().toString().contains("@gmail.com")){
                    Toast.makeText(getContext(), "Invalid Email!", Toast.LENGTH_SHORT).show();
                    em.requestFocus();
                }
                else {
                    email.setText(em.getText().toString());
                    save.setVisibility(View.VISIBLE);
                    dialog1.dismiss();
                }

            }
        });
        dialog1.show();

    }
    private void gender_set(){
        dialog1.setContentView(R.layout.gender_layout);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog1.findViewById(R.id.imageview_close6);
        Button buttonOk=dialog1.findViewById(R.id.button_ok6);

        RadioGroup radioGroup=dialog1.findViewById(R.id.radio_group_id);
        RadioButton male,female,others;
        male=dialog1.findViewById(R.id.male_id);
        female=dialog1.findViewById(R.id.female_id);
        others=dialog1.findViewById(R.id.others_id);

        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.male_id:
                        gender.setText(male.getText().toString());
                        break;
                    case R.id.female_id:
                        gender.setText(female.getText().toString());
                        break;
                    case R.id.others_id:
                        gender.setText(others.getText().toString());
                        break;
                    default:
                        gender.setText("Not Set");
                        break;
                }
                save.setVisibility(View.VISIBLE);
                dialog1.dismiss();
            }
        });
        dialog1.show();

    }

    //upload image method
    void update_data_without_image(){
        String name_1 =name.getText().toString().trim();
        String mobile1 = mobile.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String gender1 =gender.getText().toString().trim();
        String birthday1 =dateOfBirth.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_without_img, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),response, Toast.LENGTH_LONG).show();
                if(response.contains("Update Success.")){
                    //create file for save email
                    try {
                        FileOutputStream fout =getContext().openFileOutput(file, MODE_PRIVATE);
                        fout.write(email1.getBytes());
                        fout.close();
                        File filederectory = new File(getContext().getFilesDir(), file);


                        try {
                            FileInputStream fin = getContext().openFileInput(file);
                            int c;
                            String temp = "";
                            while ((c = fin.read()) != -1) {
                                temp = temp + Character.toString((char) c);
                            }
                            Toast.makeText(getContext(), ""+temp, Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    uemail=email1;
                }
                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("t1", email1);
                data.put("t2", mobile1);
                data.put("t3", name_1);
                data.put("t4", gender1);
                data.put("t5", birthday1);
                data.put("t6", uemail);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
    void update_p(){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_pass_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),response, Toast.LENGTH_LONG).show();
                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("t1", update_pass);
                data.put("t2", uemail);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void uploadImage() {
        String path=getPath(filepath);
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, upload_file).addFileToUpload(path, "upload").addParameter("t1", uemail)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(3)
                    .startUpload();

        } catch (Exception ex) {


        }

    }

}