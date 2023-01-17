package com.archivo.app.menu_fragments.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Profile fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new Advertisements();
            case 1: return new AboutMe();
            case 2: return new Reviews();
            default: return new Advertisements();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
