package com.archivo.MainMenu.menu_fragments.home;

import com.airbnb.lottie.LottieAnimationView;
import com.archivo.app.R;

public class Box {

    private final String location;
    private final String price;
    private final int image;



    public Box(String location, String price, int image) {
        this.location = location;
        this.price = price;
        this.image = image;
    }

    public boolean heart_animation(LottieAnimationView heart, int animation, boolean like) {



        if (!like) {

            heart.setAnimation(animation);
            heart.playAnimation();

        } else {


            heart.setImageResource(R.drawable.heart_twitter);

        }

        return !like;

    }



    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
