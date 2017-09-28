package com.qiaoxg.northfootball.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;


import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.entity.SaiChengBean;
import com.qiaoxg.northfootball.entity.StickyListBean;
import com.qiaoxg.northfootball.ui.widget.stickyListHeaders.StickyListHeadersAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SaiChengAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer {

    private static final String TAG = "SaiChengAdapter";

    private ViewHolder mHolder;
    private LayoutInflater mInflater;
    private List<SaiChengBean> saiChengBeen;
    private Context context;

    public SaiChengAdapter(Context context) {
        this.context = context;
        this.saiChengBeen = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void setDateList(boolean isLoadMore, List<SaiChengBean> list) {
        Log.e(TAG, "setDateList: list size is " + (list == null ? 0 : list.size()));
        if (list == null) {
            return;
        }
        if (!isLoadMore) {
            saiChengBeen.clear();
        }
        saiChengBeen.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return saiChengBeen.size();
    }

    @Override
    public SaiChengBean getItem(int position) {
        return saiChengBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        mHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_saicheng, parent, false);
            mHolder = new ViewHolder();
            mHolder.rlContentWrapper = (RelativeLayout) convertView.findViewById(R.id.rl_content_wrapper);
            mHolder.zhuName = (TextView) convertView.findViewById(R.id.zhu_tv);
            mHolder.zhuLogo = (ImageView) convertView.findViewById(R.id.zhuLogo_iv);
            mHolder.keName = (TextView) convertView.findViewById(R.id.ke_tv);
            mHolder.keLogo = (ImageView) convertView.findViewById(R.id.keLogo_iv);
            mHolder.timeTv = (TextView) convertView.findViewById(R.id.time_tv);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        SaiChengBean bean = saiChengBeen.get(position);
        mHolder.zhuName.setText(bean.zhu);
        mHolder.keName.setText(bean.ke);
        mHolder.timeTv.setText(bean.time + " " + bean.match);
        Picasso.with(context).load(bean.keLogo).into(mHolder.keLogo);
        Picasso.with(context).load(bean.zhuLogo).into(mHolder.zhuLogo);
        return convertView;
    }

    public static class ViewHolder {
        RelativeLayout rlContentWrapper;
        TextView zhuName, keName, timeTv;
        ImageView zhuLogo, keLogo;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position < getCount()) {
            String dateString = getItem(position).getDate().substring(5, 7) + getItem(position).getDate().substring(8, 10);
            return Integer.parseInt(dateString);
        }
        return 0;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.item_sticky_header,
                    parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText = getItem(position).date;
        holder.text.setText(headerText);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if (position < getCount()) {
            String dateString = getItem(position).getDate().substring(5, 7) + getItem(position).getDate().substring(8, 10);
            return Integer.parseInt(dateString);
        }
        return 0;
    }

    static class HeaderViewHolder {
        TextView text;
    }

}
