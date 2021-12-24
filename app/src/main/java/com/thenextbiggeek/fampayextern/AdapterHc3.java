package com.thenextbiggeek.fampayextern;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import mehdi.sakout.fancybuttons.FancyButton;

public class AdapterHc3 extends RecyclerView.Adapter<AdapterHc3.MyViewHolder> {


    /**
     * RecyclerView Adapter for handling hc3 cards
     */

    private final ArrayList<CardGroup> cardGroup;
    private final Context mContext;
    private FragmentMain mHomeFragment;
    boolean opened = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView hc3BgImage;
        public TextView hc3Title, hc3Subtitle;
        public FancyButton hc3Button;
        public FrameLayout hc3Base, hc3menu;
        public LinearLayout hc3Layout;
        public CardView hc3Parent;
        public FancyButton hc3BaseDismiss, hc3BaseSnooze;


        public MyViewHolder(View view) {
            super(view);
            hc3BgImage = view.findViewById(R.id.item_hc3_bg_image);
            hc3Button = view.findViewById(R.id.item_hc3_button);
            hc3Title = view.findViewById(R.id.item_hc3_title);
            hc3Subtitle = view.findViewById(R.id.item_hc3_sub_title);
            hc3Base = view.findViewById(R.id.item_hc3_base);
            hc3menu = view.findViewById(R.id.item_hc3_menu);
            hc3Parent = view.findViewById(R.id.item_hc3_parent);
            hc3BaseDismiss = view.findViewById(R.id.item_hc3_menu_btn_dismiss);
            hc3BaseSnooze = view.findViewById(R.id.item_hc3_menu_btn_snooze);
            hc3Layout = view.findViewById(R.id.item_hc3_linearlayout);


        }
    }

    public AdapterHc3(ArrayList<CardGroup> cardGroup, Context mContext, FragmentMain fragment) {
        this.cardGroup = cardGroup;
        this.mContext = mContext;
        this.mHomeFragment = fragment;

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

        //onclick for the view
        setUpSlidingMenuAndClick(holder, card);


    }

    private void setUpSlidingMenuAndClick(MyViewHolder holder, Card card) {
        holder.hc3Base.setOnLongClickListener(view -> {
            vibrateQuick(50);
            if (!opened) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 500f);
                animation.setDuration(250);
                animation.start();
            } else {
                ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 0f);
                animation.setDuration(250);
                animation.start();
            }
            opened = !opened;
            return true;
        });

        holder.hc3Base.setOnClickListener(view -> {
            if (opened) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 0f);
                animation.setDuration(250);
                animation.start();
                opened = !opened;

            } else {
                //usual url opening
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(card.getUrl())));
            }
        });

        holder.hc3BaseSnooze.setOnClickListener(view -> {
            Log.e("Button", "snooze");
            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(holder.hc3Parent, "alpha", 1f, 0f);
            fadeAnim.setDuration(250);
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mHomeFragment.hideHc3(false);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(fadeAnim);
            animatorSet.start();
        });

        holder.hc3BaseDismiss.setOnClickListener(view -> {
            Log.e("Button", "dismiss");
            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(holder.hc3Parent, "alpha", 1f, 0f);
            fadeAnim.setDuration(250);
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mHomeFragment.hideHc3(true);

                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(fadeAnim);
            animatorSet.start();
        });


    }

    private void vibrateQuick(int i) {
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(i, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(i);
        }
    }

    private int getComplementaryColor(int color) {
        int R = color & 255;
        int G = (color >> 8) & 255;
        int B = (color >> 16) & 255;
        int A = (color >> 24) & 255;
        R = 255 - R;
        G = 255 - G;
        B = 255 - B;
        return R + (G << 8) + (B << 16) + (A << 24);
    }

    @Override
    public int getItemCount() {
        return cardGroup.size();
    }
}