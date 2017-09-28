package com.qiaoxg.northfootball.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/24.
 */

public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> mViews = new ArrayList<>();
    public final String[] TITLES;

    public MyViewPagerAdapter(List<View> mViews, String[] TITLES) {
        this.mViews = mViews;
        this.TITLES = TITLES;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
