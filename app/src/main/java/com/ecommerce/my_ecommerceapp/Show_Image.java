package com.ecommerce.my_ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Show_Image extends AppCompatActivity {
    ImageView close,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__image);
        close=findViewById(R.id.close_show_imageview_id);
        image=findViewById(R.id.image_show_imageview_id);

        String s=getIntent().getStringExtra("image1");
        Picasso.get().load(s).into(image);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}