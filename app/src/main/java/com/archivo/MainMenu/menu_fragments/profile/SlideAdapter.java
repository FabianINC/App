package com.archivo.MainMenu.menu_fragments.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.archivo.app.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SliderViewHolder> {

    private final List<SliderItem> sliderItems;
    private ViewPager2 viewPager2;

    SlideAdapter(List<SliderItem> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        holder.setImageView(sliderItems.get(position));
        if(position == sliderItems.size() - 2){

            viewPager2.post(runnable);

        }

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    static class SliderViewHolder extends  RecyclerView.ViewHolder{


        private final RoundedImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageSlide);

        }

        void setImageView(SliderItem sliderItem){


            imageView.setImageResource(sliderItem.getImage());

        }


    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();

        }
    };



}
