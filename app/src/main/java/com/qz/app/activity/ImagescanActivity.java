package com.qz.app.activity;

import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.qz.app.R;
import com.qz.app.adapter.ViewPagerAdapter;
import com.qz.app.base.BaseActivity;
import com.qz.app.constant.Constant;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.utils.GlideUtils;
import com.qz.app.view.PopWinWithList;

import java.util.ArrayList;

public class ImagescanActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagescan);
        final Parcelable parcelables[] = getIntent().getParcelableArrayExtra(Constant.PHOTO_PATH);
        int postion = getIntent().getIntExtra(Constant.PHOTO_POS, 0);
        final LocalFileEntity[] paths = new LocalFileEntity[parcelables.length];
        for (int m = 0; m < parcelables.length; m++) {
            paths[m] = (LocalFileEntity) parcelables[m];
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.contenterView);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return paths.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LocalFileEntity localFileEntity = paths[position];
                PhotoView view = new PhotoView(ImagescanActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                container.addView(view);
                if (localFileEntity.fromNet) {
                    GlideUtils.setImage(getBaseContext(), localFileEntity.url, R.drawable.transparent, R.drawable.transparent, view);
                } else {
                    GlideUtils.setImageURI(getBaseContext(), localFileEntity.path, R.drawable.transparent, R.drawable.transparent, view);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
//
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        viewPager.setCurrentItem(postion);
    }


}
