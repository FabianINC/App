package com.archivo.MainMenu.menu_fragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.archivo.app.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FirebaseAdapter extends FirestoreRecyclerAdapter<Box, FirebaseAdapter.ViewHolder> {

    private OnItemClickListener mListener;

    public interface OnItemClickListener{

        void onAnimationClick(int position, LottieAnimationView heart);

    }

    public void setOnItemClickListener(FirebaseAdapter.OnItemClickListener listener){

        mListener = listener;

    }

    public FirebaseAdapter(@NonNull FirestoreRecyclerOptions<Box> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Box model) {

        holder.location.setText(model.getLocation());
        holder.price.setText(model.getPrice());

        Glide.with(holder.img.getContext())
                .load(model.getImgPath())
                .placeholder(R.drawable.img_perfil2)
                .error(R.drawable.img_perfil)
                .into(holder.img);




    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //El "LayoutInflater" es el responsable de encontrar el contexto al que le deseamos aplicar los cambios en este caso seria "fragment_home"
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        boolean attached = false;

        /*
        El inflater.inflate lo que hace es poner los atributos en un ViewGroup, esto lo hace pasando el "id" del layout que funcionara como clase("recycler_element")
        posteriormente recibira el contenedor el cual sera como un objeto de la clase que se paso previamente y finalmente se pasa un booleano que se pone false para
        evitar que se cree dos veces el mismo conjunto de Views
         */
        View view = inflater.inflate(R.layout.reciclerview_element, parent , attached);
        //Finalmente se retorna "MyViewHolder" creado previamente con el argumento view que se explico previamente
        return new ViewHolder(view, mListener);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        boolean like;
        TextView location,price;
        ImageView img;
        LottieAnimationView heart;

        public ViewHolder(@NonNull View itemView , OnItemClickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.addImg);
            location = itemView.findViewById(R.id.txt_Location);
            price = itemView.findViewById(R.id.txt_Price);
            heart = itemView.findViewById(R.id.animation);
            like = false;

            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listener != null){


                        int position = getBindingAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                           listener.onAnimationClick(position, heart);

                        }


                    }

                }
            });



        }





    }


}
