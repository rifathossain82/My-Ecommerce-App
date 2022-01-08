package com.ecommerce.my_ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ecommerce.my_ecommerceapp.adminPanel.Add_product_fra;
import com.ecommerce.my_ecommerceapp.adminPanel.Admin_view_order;
import com.ecommerce.my_ecommerceapp.adminPanel.Get_product_fra;
import com.ecommerce.my_ecommerceapp.adminPanel.Modify_product_fra;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private String file="myfile_extra";
    private Dialog dialog;
    String db_file=unit.wishlist_insert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar=findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.mynavID);
        String dd=getIntent().getStringExtra("data");
        if(dd.contains("1")){
            navigationView.inflateMenu(R.menu.nav_admin);
        }
        if(dd.contains("0")){
            navigationView.inflateMenu(R.menu.nav);
        }

        String s=getIntent().getStringExtra("email");

        //create file for save email
        try {
            FileOutputStream fout =getApplicationContext().openFileOutput(file, MODE_PRIVATE);
            fout.write(s.getBytes());
            fout.close();
            File filederectory = new File(getApplicationContext().getFilesDir(), file);


            try {
                FileInputStream fin = getApplicationContext().openFileInput(file);
                int c;
                String temp = "";
                while ((c = fin.read()) != -1) {
                    temp = temp + Character.toString((char) c);
                }
                Toast.makeText(Homepage.this, "Email :"+temp, Toast.LENGTH_SHORT).show();
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

        //dialog code

        dialog=new Dialog(this);

        //end dialog code

        //initial id

        drawerLayout=findViewById(R.id.mydrawerID);
        NavigationView navigationView=findViewById(R.id.mynavID);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation_drawer,
                R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
       if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new All_products_home()).commit();
            toolbar.setSubtitle("Home ");
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            AlertDialog.Builder builder=new AlertDialog.Builder(Homepage.this);
            builder.setTitle("Confirm Message")
                    .setMessage("Are you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create();
            builder.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()) {
           case R.id.nav_home:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                       new All_products_home()).commit();
               toolbar.setSubtitle("Home");
               break;
           case R.id.nav_share:
               try {
                   Intent it = new Intent(Intent.ACTION_SEND);
                   it.setType("text/plain");
                   it.putExtra(Intent.EXTRA_SUBJECT, "eshop");
                   String sharemessage = "https://play.google.com/store/apps/details?=" + BuildConfig.APPLICATION_ID + "\n\n";
                   it.putExtra(Intent.EXTRA_TEXT, sharemessage);
                   startActivity(Intent.createChooser(it, "share by"));
               }catch (Exception e){
                   Toast.makeText(Homepage.this, "Error occurred", Toast.LENGTH_SHORT).show();
               }
                break;
           case R.id.nav_logout:
               finish();
               System.exit(0);
               break;
          case R.id.nav_add_product:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                       new Add_product_fra()).commit();
               toolbar.setSubtitle("Add Product ");
               break;
           case R.id.nav_show:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                       new Get_product_fra()).commit();
               toolbar.setSubtitle("Show All Product ");
               break;
           case R.id.nav_modify:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                       new Admin_view_order()).commit();
               toolbar.setSubtitle("Ordered Products");
               break;
             case R.id.nav_wishlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Wishlist()).commit();
                toolbar.setSubtitle("Wishlist ");
                break;
            case R.id.nav_cart:
                Intent it=new Intent(Homepage.this,Add_to_cart.class);
                startActivity(it);
                break;
           case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserInformation()).commit();
                toolbar.setSubtitle("User Information ");
                break;
            case R.id.nav_about:
                final AlertDialog.Builder builder=new AlertDialog.Builder(Homepage.this);
                LayoutInflater inflater=Homepage.this.getLayoutInflater();
                View view=inflater.inflate(R.layout.about_layout,null);
                builder.setView(view)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                break;
            case R.id.nav_terms:
                final AlertDialog.Builder builder1=new AlertDialog.Builder(Homepage.this);
                LayoutInflater inflater1=Homepage.this.getLayoutInflater();
                View view1=inflater1.inflate(R.layout.terms_condition_layout,null);
                builder1.setView(view1)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder1.show();
                break;
             case R.id.nav_help:
                 final AlertDialog.Builder builder2=new AlertDialog.Builder(Homepage.this);
                 LayoutInflater inflater2=Homepage.this.getLayoutInflater();
                 View view2=inflater2.inflate(R.layout.help_center_layout,null);
                 builder2.setView(view2)
                         .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                             }
                         });
                 builder2.show();
                break;
            case R.id.nav_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new My_Order_list()).commit();
                toolbar.setSubtitle("My Order ");
                break;
            /* case R.id.nav_share:
                try {
                    Intent it = new Intent(Intent.ACTION_SEND);
                    it.setType("text/plain");
                    it.putExtra(Intent.EXTRA_SUBJECT, "My Calculator");
                    String sharemessage = "https://play.google.com/store/apps/details?=" + BuildConfig.APPLICATION_ID + "\n\n";
                    it.putExtra(Intent.EXTRA_TEXT, sharemessage);
                    startActivity(Intent.createChooser(it, "share by"));
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_feedback:
                try {
                    Intent it = new Intent(Intent.ACTION_SEND);
                    it.putExtra(Intent.EXTRA_EMAIL, new String[]{"rhrifat940279@gmail.com"});
                    it.putExtra(Intent.EXTRA_SUBJECT, "WINCAL Calculator Feedback");
                    it.putExtra(Intent.EXTRA_TEXT, "Enter Your Feedback Here......");
                    it.setType("message/rfc822");

                    startActivity(it);
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_contact:
                openFirstDialog();
                break;
            case R.id.nav_about:
                about_showclass dd3=new about_showclass();
                dd3.show(getSupportFragmentManager(),"about class");
                break;*/

        }
        drawerLayout.closeDrawer(GravityCompat.START);


       return true;
    }

/* private void openFirstDialog(){
        dialog.setContentView(R.layout.option_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewclose=dialog.findViewById(R.id.imageView_close_ob);
        ImageView buttonOk1=dialog.findViewById(R.id.button_ok_ob1);
        ImageView buttonOk2=dialog.findViewById(R.id.button_ok_ob2);
        ImageView buttonOk3=dialog.findViewById(R.id.button_ok_ob3);


        imageViewclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonOk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlink("https://www.facebook.com/rh.rifat.33633");
                dialog.dismiss();
            }
        });
        buttonOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlink("https://twitter.com/Rifatho25073502");

                dialog.dismiss();
            }
        });
        buttonOk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlink("https://www.instagram.com/rh_rifat220/?hl=en");

                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void openlink(String s){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(s));
        startActivity(intent);
    }*/
private void openFirstDialog(){
    dialog.setContentView(R.layout.about_layout);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.show();

}


}