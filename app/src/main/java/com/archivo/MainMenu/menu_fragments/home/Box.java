package com.archivo.MainMenu.menu_fragments.home;

import com.airbnb.lottie.LottieAnimationView;
import com.archivo.app.R;

public class Box {

    private String imgPath, location, price;


    public Box(){

    }

    public Box(String location, String price, String imgPath) {
        this.location = location;
        this.price = price;
        this.imgPath = imgPath;
    }




    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(String price) {
        this.price = price;
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


}
