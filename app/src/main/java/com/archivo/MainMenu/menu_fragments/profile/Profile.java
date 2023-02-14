package com.archivo.MainMenu.menu_fragments.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.airbnb.lottie.LottieAnimationView;
import com.archivo.MainMenu.menu_fragments.home.B_RecyclerViewAdapter;
import com.archivo.MainMenu.menu_fragments.home.Box;
import com.archivo.app.R;

import java.util.ArrayList;


public class Profile extends Fragment {


    //Se crea la lista de elementos "Box"
    private ArrayList<Box> boxes;
    //Se llama a la clase "RecyclerView"
    private RecyclerView recyclerView;
    //Se llama al Adapter
    private B_RecyclerViewAdapter adapter;
    //Otros Atributos
    boolean like;
    private Button btnAddSpace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Se inicializa el Arraylist
        boxes = new ArrayList<>();
        //Se inicializa el ryclerView haciendo un "findViewBy" al recycler que se creo en el fragment_home.xml
        recyclerView = view.findViewById(R.id.rvImageSlider);
        //Esto crea un LinearLayoutManager en el contexto que recibe como parametro, en este caso fragment_home
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //Metodo que crea los distintos elementos que irian dentro del recyclerView
        setUpBoxModels();
        //Se inicializa "B_RecyclerViewAdapter" la cual es la clase que va a introducir los elementos en el recyclerView
        adapter = new B_RecyclerViewAdapter(getContext(), boxes, R.layout.slide_item_container);
        //Aqui se establece el adapter creado previamente
        recyclerView.setAdapter(adapter);
        //Se le asigna valor al atributo like
        like = false;

        adapter.setOnItemClickListener(new B_RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onAnimationClick(int position, LottieAnimationView heart) {


                like = boxes.get(position).heart_animation(heart, R.raw.heart, like);


            }
        });

        //Envia el usuario a la actividad para que agregue su espacio
        btnAddSpace = view.findViewById(R.id.btnAddSpace);

        btnAddSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addSpace = new Intent(getContext(), AddSpace.class);
                startActivity(addSpace);

            }
        });


        //Retorna la vista
        return view;

    }



    private void setUpBoxModels() {


        for (int i = 0; i < 10; i++) {


            boxes.add(new Box("Alajuela, Costa Rica", "₡25,000", R.drawable.img_prueba));


        }
    }


}