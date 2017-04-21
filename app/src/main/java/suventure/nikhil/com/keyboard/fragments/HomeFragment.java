package suventure.nikhil.com.keyboard.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import suventure.nikhil.com.keyboard.R;
import suventure.nikhil.com.keyboard.adapter.ViewPagerAdapter;


public class HomeFragment extends Fragment {


    ViewPager viewPager;
    TabLayout tabLayout;


    ViewPagerAdapter adapter;

    public static List<String> titleList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);


        if(titleList != null)
        {
            titleList.clear();
        }

        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout)view. findViewById(R.id.tabs);
/*
        LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);*/

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        titleList.add("Chats");
        titleList.add("Contacts");
        titleList.add("Favourites");
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        for(int i = 0; i < titleList.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            HomeSubFragment fragment = new HomeSubFragment();
            fragment.setArguments(bundle);
            adapter.addFrag(fragment, titleList.get(i));
        }

        viewPager.setAdapter(adapter);
    }








}
