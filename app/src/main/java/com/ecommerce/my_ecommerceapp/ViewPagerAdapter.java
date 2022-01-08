package com.ecommerce.my_ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return new All_items();
           case 1:
               return new Offer_items();
           case 2:
               return new Electronics_items();
           case 3:
               return new Lifestyle_items();
           case 4:
               return new Home_appliances_items();
           case 5:
               return new Books_items();
           case 6:
               return new More_items();
           default:
               return new All_items();
       }
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="All";
                break;
            case 1:
                title="OFFERS";
                break;
            case 2:
                title="ELECTRONICS";
                break;
            case 3:
                title="LIFESTYLE";
                break;
            case 4:
                title="HOME APPLIANCES";
                break;
            case 5:
                title="BOOKS & MORE";
                break;
            case 6:
                title="MORE";
                break;
            default:
                title="All";
        }
        return title;
    }
}
