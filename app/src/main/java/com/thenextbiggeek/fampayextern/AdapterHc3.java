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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class AdapterHc3 extends RecyclerView.Adapter<AdapterHc3.MyViewHolder> {

    private final ArrayList<CardGroup> cardGroup;
    private final Context mContext;
    private FragmentMain mHomeFragment;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView hc3BgImage;
        public TextView hc3Title, hc3Subtitle;
        public FancyButton hc3Button;
        public FrameLayout hc3Base;

        public MyViewHolder(View view) {
            super(view);
            hc3BgImage = (ImageView) view.findViewById(R.id.item_hc3_bg_image);
            hc3Button = (FancyButton) view.findViewById(R.id.item_hc3_button);
            hc3Title = (TextView) view.findViewById(R.id.item_hc3_title);
            hc3Subtitle = (TextView) view.findViewById(R.id.item_hc3_sub_title);
            hc3Base = (FrameLayout) view.findViewById(R.id.item_hc3_base);

        }
    }

    public AdapterHc3(ArrayList<CardGroup> cardGroup, Context mContext) {
        this.cardGroup = cardGroup;
        this.mContext = mContext;

    }

    @Override
    public AdapterHc3.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hc3, parent, false);
        return new AdapterHc3.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterHc3.MyViewHolder holder, final int position) {
        //background colour and background img
        Card card = cardGroup.get(position).getCards().get(0);
        holder.hc3Base.setBackgroundColor(Color.parseColor(card.getBg_color()));
        Glide.with(mContext)
                .asBitmap()
                .load(card.getBg_image().getImage_url())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.hc3BgImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        //title and subtitle
        holder.hc3Title.setText(card.getFormatted_title().getText());
        holder.hc3Title.setTextColor((getComplementaryColor(Color.parseColor(card.getBg_color()))));

        holder.hc3Subtitle.setText(card.getFormatted_description().getText());
        //cta
        holder.hc3Button.setText(card.getCta().get(0).getText());
        holder.hc3Button.setTextColor(getComplementaryColor(Color.parseColor(card.getCta().get(0).getBg_color())));
        holder.hc3Button.setBackgroundColor(Color.parseColor(card.getCta().get(0).getBg_color()));
        holder.hc3Button.setTextColor(Color.parseColor(card.getCta().get(0).getText_color()));
        holder.hc3Button.setOnClickListener(view -> {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(card.getCta().get(0).getUrl())));
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