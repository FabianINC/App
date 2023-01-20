package com.archivo.MainMenu.menu_fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.archivo.app.R;

import java.util.ArrayList;


public class Home extends Fragment {

    ArrayList<Box> boxes;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        boxes = new ArrayList<>();
        recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setUpBoxModels();

        B_RecyclerViewAdapter adapter = new B_RecyclerViewAdapter(getContext(), boxes);
        recyclerView.setAdapter(adapter);



        return view;
    }

    private void setUpBoxModels() {


        for (int i = 0; i < 10; i++) {


            boxes.add(new Box("Alajuela, Costa Rica", "₡25,000", R.drawable.img_prueba));


        }
    }

}