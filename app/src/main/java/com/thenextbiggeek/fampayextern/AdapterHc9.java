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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class AdapterHc9 extends RecyclerView.Adapter<AdapterHc9.MyViewHolder> {

    private final ArrayList<Card> cards;
    private final Context mContext;
    private FragmentMain mHomeFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView hc9Image;
        public RelativeLayout hc9Base;
        public LinearLayout hc9Parent;

        public MyViewHolder(View view) {
            super(view);
            hc9Image = (ImageView) view.findViewById(R.id.item_hc9_image);
            hc9Base = (RelativeLayout) view.findViewById(R.id.item_hc9_base);
            hc9Parent = (LinearLayout) view.findViewById(R.id.item_hc9_parent);

        }
    }

    public AdapterHc9(ArrayList<Card> cards, Context mContext) {
        this.cards = cards;
        this.mContext = mContext;

    }

    @Override
    public AdapterHc9.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hc9, parent, false);
        return new AdapterHc9.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterHc9.MyViewHolder holder, final int position) {
        //background colour and background img
        Card card = cards.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(card.getBg_image().getImage_url())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.hc9Image.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        //cta
        holder.hc9Parent.setOnClickListener(view -> {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(card.getUrl())));
        });


    }

    private int getComplementaryColor( int color) {
        int R = color & 255;
        int G = (color >> 8) & 255;
        int B = (color >> 16) & 255;
        int A = (color >> 24) & 255;
        R = 255 - R;
        G = 255 - G;
        B = 255 - B;
        return R + (G << 8) + ( B << 16) + ( A << 24);
    }


    @Override
    public int getItemCount() {
        return cards.size();
    }
}