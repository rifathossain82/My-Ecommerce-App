package com.ecommerce.my_ecommerceapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class All_products_home extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_products_home, container, false);

        viewPager = view.findViewById(R.id.view_pager_id);
        tabLayout = view.findViewById(R.id.tabs);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        /*ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Offer");
        arrayList.add("Tab2");
        arrayList.add("Experience");
        arrayList.add("Hello");
        arrayList.add("Tab5");
        arrayList.add("Tab6");

        prepareviewpager(viewPager,arrayList);
        tabLayout.setupWithViewPager(viewPager);

*/
        return view;
    }

}