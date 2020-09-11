package com.shuffl.niched.view.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.shuffl.niched.utils.glide.GlideUtil;
import com.shuffl.niched.R;

public class ViewPagerImageAdapter extends PagerAdapter {

    int[] mImages = new int[]{R.mipmap.one, R.mipmap.two};
    Context mContext;

    public ViewPagerImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        GlideUtil.loadImage(imageView, mImages[position], GlideUtil.getProgressDrawable(mContext));
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
