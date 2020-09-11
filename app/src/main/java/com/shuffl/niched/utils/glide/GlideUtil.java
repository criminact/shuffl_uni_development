package com.shuffl.niched.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuffl.niched.R;

public class GlideUtil {

    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable cpd) {
        RequestOptions options = new RequestOptions()
                .placeholder(cpd)
                .centerCrop()
                .error(R.drawable.ic_launcher_background);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, int url, CircularProgressDrawable cpd) {
        RequestOptions options = new RequestOptions()
                .placeholder(cpd)
                .centerCrop()
                .error(R.drawable.ic_launcher_background);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(50f);
        cpd.start();
        return cpd;
    }
}

