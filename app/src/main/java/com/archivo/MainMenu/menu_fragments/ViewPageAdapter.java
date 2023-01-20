package com.archivo.MainMenu.menu_fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.archivo.MainMenu.menu_fragments.favorites.Favorite;
import com.archivo.MainMenu.menu_fragments.home.Home;
import com.archivo.MainMenu.menu_fragments.profile.Profile;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position){

            case 0 : return new Home();
            case 1: return new Favorite();
            case 2: return new Profile();
            default: return new Home();


        }


    }

    @Override
    public int getItemCount() {
        return 3 ;
    }
}
