package com.archivo.MainMenu.menu_fragments.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.airbnb.lottie.LottieAnimationView;
import com.archivo.app.R;

import java.util.ArrayList;
//Se crea la clase del Adapatador
//Se le da la extension "RecyclerView.Adapter" y entre "<>" se pone la clase anidada, la cual identifico cada elemento del "reciclerview_element"
public class B_RecyclerViewAdapter extends RecyclerView.Adapter<B_RecyclerViewAdapter.MyViewHolder> {

    //Atributos
    private Context context;
    private ArrayList<Box> boxes;
    private OnItemClickListener mListener;
    //Se identifica el layout que funcionara como ejemplo para crear todos los elementos
    private int id;



    public interface OnItemClickListener{

        void onAnimationClick(int position, LottieAnimationView heart);

    }

    public void setOnItemClickListener(OnItemClickListener listener){

        mListener = listener;

    }


    //Constructor
   public B_RecyclerViewAdapter(Context context, ArrayList<Box> boxes, int id){

     this.context = context;
     this.boxes = boxes;
     this.id = id;

   }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       //El "LayoutInflater" es el responsable de encontrar el contexto al que le deseamos aplicar los cambios en este caso seria "fragment_home"
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean attached = false;

        /*
        El inflater.inflate lo que hace es poner los atributos en un ViewGroup, esto lo hace pasando el "id" del layout que funcionara como clase("recycler_element")
        posteriormente recibira el contenedor el cual sera como un objeto de la clase que se paso previamente y finalmente se pasa un booleano que se pone false para
        evitar que se cree dos veces el mismo conjunto de Views
         */
        View view = inflater.inflate(this.id, parent , attached);
        //Finalmente se retorna "MyViewHolder" creado previamente con el argumento view que se explico previamente
        return new MyViewHolder(view, mListener);
    }


    //Este metodo es el encargado de renderizar cada objeto en su respectiva posicion
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       holder.tvLocation.setText(boxes.get(position).getLocation());
       holder.tvPrice.setText(boxes.get(position).getPrice());
       holder.imageView.setImageResource(boxes.get(position).getImage());

    }

    //Retorna la cantidad de elementos
    @Override
    public int getItemCount() {
        return boxes.size();
    }




    //Se crea una clase `anidada con la extension "RecyclerView.ViewHolder" identificara cada elemento
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        boolean like;
        ImageView imageView;
        TextView tvLocation,tvPrice;
        LottieAnimationView heart;

         public MyViewHolder(@NonNull View itemView, OnItemClickListener listener){
             super(itemView);

             imageView = itemView.findViewById(R.id.recicler_img);
             tvLocation = itemView.findViewById(R.id.txt_Location);
             tvPrice = itemView.findViewById(R.id.txt_Price);
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
