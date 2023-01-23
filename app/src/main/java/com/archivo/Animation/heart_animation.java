package com.archivo.Animation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.archivo.app.R;


public class heart_animation extends Fragment {

    boolean like = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_heart_animation, container, false);

        LottieAnimationView imageView= view.findViewById(R.id.animation);
        heart_animation(imageView, R.raw.heart, like);

        return view;
    }

    private boolean heart_animation(LottieAnimationView imageView, int animation, boolean like) {

        if (!like) {

            imageView.setAnimation(animation);
            imageView.playAnimation();

        } else {


            imageView.setImageResource(R.drawable.ic_empty_heart);

        }

        return !like;

    }
}