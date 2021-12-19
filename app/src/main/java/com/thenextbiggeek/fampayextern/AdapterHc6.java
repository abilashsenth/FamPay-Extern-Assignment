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
import android.widget.FrameLayout;
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

import mehdi.sakout.fancybuttons.FancyButton;

public class AdapterHc6  extends RecyclerView.Adapter<AdapterHc6.MyViewHolder> {

    private final ArrayList<CardGroup> cardGroup;
    private final Context mContext;
    private FragmentMain mHomeFragment;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView hc6Icon;
        public TextView hc6Title;
        public RelativeLayout hc6Base;
        public LinearLayout hc6Parent;

        public MyViewHolder(View view) {
            super(view);
            hc6Icon = (ImageView) view.findViewById(R.id.item_hc6_icon);
            hc6Title = (TextView) view.findViewById(R.id.item_hc6_title);
            hc6Base = (RelativeLayout) view.findViewById(R.id.item_hc6_base);
            hc6Parent = (LinearLayout) view.findViewById(R.id.item_hc6_parent);

        }
    }

    public AdapterHc6(ArrayList<CardGroup> cardGroup, Context mContext) {
        this.cardGroup = cardGroup;
        this.mContext = mContext;

    }

    @Override
    public AdapterHc6.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hc6, parent, false);
        return new AdapterHc6.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterHc6.MyViewHolder holder, final int position) {
        //background colour and background img
        Card card = cardGroup.get(position).getCards().get(0);
        Glide.with(mContext)
                .asBitmap()
                .load(card.getIcon().getImage_url())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.hc6Icon.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        //title
        holder.hc6Title.setText(card.getFormatted_title().getText());
        //cta
        holder.hc6Parent.setOnClickListener(view -> {
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
        return cardGroup.size();
    }
}