package com.archivo.MainMenu.menu_fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.archivo.app.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class Home extends Fragment {

    //Se crea la lista de elementos "Box"
    private ArrayList<Box> boxes;
    //Se llama a la clase "RecyclerView"
    private RecyclerView recyclerView;
    //Se llama al Adapter
    private FirebaseAdapter adapter;
    //Firestore
    FirebaseFirestore firestore;
    //Atributo like
    boolean like;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Esta es la vista del fragment_home
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("Places");


        FirestoreRecyclerOptions<Box> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Box>()
                        .setQuery(query, Box.class)
                        .build();

        adapter = new FirebaseAdapter(firestoreRecyclerOptions);
        recyclerView.setAdapter(adapter);

        //Se le asigna valor al atributo like
        like = false;

       adapter.setOnItemClickListener(new FirebaseAdapter.OnItemClickListener() {
            @Override
            public void onAnimationClick(int position, LottieAnimationView heart) {

                Toast.makeText(getContext(), "Hi", Toast.LENGTH_SHORT).show();

                 like = boxes.get(position).heart_animation(heart, R.raw.heart, like);

            }
        });

        //Retorna la vista
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}