package com.example.Labo2_Firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Map;

import Labo3_Firebase.R;

public class ListViewArrayAdapter extends ArrayAdapter<Exercice> {

    private final Context mContext;
    int mResource;


    public ListViewArrayAdapter(Context context, int resource, ArrayList<Exercice> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {

        MyDataBaseHelper maDB = new MyDataBaseHelper(mContext);

        //GET THE EXERCICE BEING LISTED
        Exercice exercice = getItem(position);

        //OBTENIR LES VALEURS DES ATTRIBUTS DE L'EXERCICE EN QUESTION

        String title = exercice.getTitle();
        String img = exercice.getImg();
        String repeat = exercice.getRepeat();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //OBTENIR LES ELEMENTS DE exercices_list_layout
        TextView tv_title = convertView.findViewById(R.id.textView_title);
        TextView tv_repeat = convertView.findViewById(R.id.textView_repeat);
        ImageView iv_img = convertView.findViewById(R.id.imageView_img);

        TextView btn_modifier = convertView.findViewById(R.id.textView_modifier);
        TextView btn_favorie = convertView.findViewById(R.id.ImageButton_favorite);

        //AFFICHER LES VALEURS DANS LE LISTVIEW
        tv_title.setText(title);
        tv_repeat.setText(repeat);
        int imgDR = mContext.getResources().getIdentifier(img, "drawable", mContext.getPackageName());
        iv_img.setImageResource(imgDR);


        //SET IMAGE FOR FAVORITE
        if (exercice.getFavorite().equals("0")) {
            btn_favorie.setBackgroundResource(R.drawable.non_favoris);
        } else if (exercice.getFavorite().equals("1")) {
            btn_favorie.setBackgroundResource(R.drawable.favoris);
        }


        //OnCLICK LISTENER FOR FAVORITE
        btn_favorie.setOnClickListener(view -> {
            //Modifier Favori dans l'exercice
            if (exercice.getFavorite().equals("0")) {
                exercice.setFavorite("1");
                maDB.setFavoris(exercice.get_id());
            } else {
                exercice.setFavorite("0");
                maDB.unsetFavoris(exercice.get_id());
            }
        });



        //OnCLICK LISTENER FOR UPDATE
        btn_modifier.setOnClickListener(view -> {

            //Get CategorieActivity
            Intent intentToCategorie = new Intent(mContext, ModifierActivity.class);

            //Send CategorieChoisie to CategorieActivity
            intentToCategorie.putExtra("ModifierCetExercice", exercice);


            //Go To CategorieActivity
            mContext.startActivity(intentToCategorie);


        });


        return convertView;
    }


}