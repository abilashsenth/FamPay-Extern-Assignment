package com.thenextbiggeek.fampayextern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class AdapterHc1 extends RecyclerView.Adapter<AdapterHc1.MyViewHolder> {


    /**
     * RecyclerView Adapter for handling hc1 cards
     */

    private final ArrayList<Card> cards;
    private final Context mContext;
    private FragmentMain mHomeFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView hc1Image;
        public RelativeLayout hc1Base;
        public LinearLayout hc1Parent;
        public TextView hc1Title, hc1SubTitle;

        public MyViewHolder(View view) {
            super(view);
            hc1Image = view.findViewById(R.id.item_hc1_icon);
            hc1Base = view.findViewById(R.id.item_hc1_base);
            hc1Parent = view.findViewById(R.id.item_hc1_parent);
            hc1Title = view.findViewById(R.id.item_hc1_title);
            hc1SubTitle = view.findViewById(R.id.item_hc1_subtitle);


        }
    }

    public AdapterHc1(ArrayList<Card> cards, Context mContext) {
        this.cards = cards;
        this.mContext = mContext;

    }

    @Override
    public AdapterHc1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hc1, parent, false);
        return new AdapterHc1.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterHc1.MyViewHolder holder, final int position) {
        //background colour and background img
        Card card = cards.get(position);
        holder.hc1Base.setBackgroundColor(Color.parseColor((card.getBg_color()!=null? card.getBg_color():"#FBAF03")));
        Glide.with(mContext)
                .asBitmap()
                .load(card.getIcon().getImage_url())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.hc1Image.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        //title and description
        if(card.getFormatted_title().getText().isEmpty()){
            holder.hc1Title.setText(card.getFormatted_title().getText());
        }else if(!(card.getFormatted_title().getText().isEmpty())){
            holder.hc1Title.setText(card.getTitle());
        }
        if(card.getFormatted_description()!=null){
            if(card.getFormatted_description().getText() ==null){
                holder.hc1SubTitle.setText(card.getFormatted_title().getText());
            }else if(card.getFormatted_description().getText()!= null){
                holder.hc1SubTitle.setText(card.getDescription());
            }else{
                holder.hc1SubTitle.setVisibility(View.INVISIBLE);
            }
        }else{
            holder.hc1SubTitle.setVisibility(View.GONE);
        }


        //cta
        holder.hc1Parent.setOnClickListener(view -> {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(card.getUrl())));
        });


    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}