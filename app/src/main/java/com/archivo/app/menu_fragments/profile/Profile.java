package com.archivo.app.menu_fragments.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.archivo.app.R;

import java.util.ArrayList;
import java.util.List;


public class Profile extends Fragment {

private ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        viewPager2 = view.findViewById(R.id.viewPager_profile);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.img_prueba));
        sliderItems.add(new SliderItem(R.drawable.img_prueba));
        sliderItems.add(new SliderItem(R.drawable.img_prueba));
        viewPager2.setAdapter(new SlideAdapter(sliderItems, viewPager2));


        // Inflate the layout for this fragment
        return view;
    }
}