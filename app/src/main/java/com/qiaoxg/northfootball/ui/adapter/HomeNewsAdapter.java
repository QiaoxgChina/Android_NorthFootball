package com.qiaoxg.northfootball.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.entity.NewsBean;
import com.qiaoxg.northfootball.ui.activity.news.NewsActivity;
import com.qiaoxg.northfootball.utils.UIHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "HomeNewsAdapter";

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_CONTENT = 1;

    private List<NewsBean> mHomeDataBeans;
    private Context mContext;

    public HomeNewsAdapter(Context context) {
        this.mContext = context;
        mHomeDataBeans = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CONTENT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_news, parent, false);
            return new HomeNewsVH(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_empty, parent, false);
            return new EmptyVH(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vHolder, int position) {
        if (vHolder instanceof HomeNewsVH) {
            HomeNewsVH holder = (HomeNewsVH) vHolder;
            NewsBean bean = mHomeDataBeans.get(position);
            holder.titleTv.setText(bean.getTitle());
            holder.dateTv.setText(bean.getDateTime());
            holder.fromTv.setText("来源: " + bean.getFrom());
            if (!TextUtils.isEmpty(bean.getImgUrl())) {
                UIHelper.showView(holder.newsImgIv, true);
                Picasso.with(mContext).load(bean.getImgUrl()).into(holder.newsImgIv);
            } else {
                UIHelper.showView(holder.newsImgIv, false);
            }

            if (!TextUtils.isEmpty(bean.getCommentCount())) {
                UIHelper.showView(holder.commentView, true);
                holder.countTv.setText(bean.getCommentCount());
            } else {
                UIHelper.showView(holder.commentView, false);
            }
            holder.parentView.setTag(bean);
        } else if (vHolder instanceof EmptyVH) {

        }

    }

    @Override
    public int getItemCount() {
        if (mHomeDataBeans == null || mHomeDataBeans.size() <= 0) {
            return 1;
        } else {
            return mHomeDataBeans.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHomeDataBeans == null || mHomeDataBeans.size() <= 0)
            return VIEW_TYPE_EMPTY;
        else
            return VIEW_TYPE_CONTENT;
    }

    public void setHomeDataList(boolean mIsLoadMore, List<NewsBean> homeBeans) {
        if (homeBeans == null || homeBeans.size() <= 0) {
            return;
        }
        if (mIsLoadMore) {
            this.mHomeDataBeans.addAll(homeBeans);
        } else {
            this.mHomeDataBeans = homeBeans;
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        NewsBean bean = (NewsBean) v.getTag();
        UIHelper.showToast(bean.getTitle());
        NewsActivity.intoThisActivity((Activity) mContext, bean.getLink(), bean.getUuid());
    }

    public class HomeNewsVH extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView dateTv;
        ImageView newsImgIv;
        TextView fromTv;
        View commentView;
        TextView countTv;
        View parentView;

        private HomeNewsVH(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            dateTv = (TextView) itemView.findViewById(R.id.date_tv);
            newsImgIv = (ImageView) itemView.findViewById(R.id.newsImg_iv);
            fromTv = (TextView) itemView.findViewById(R.id.from_tv);
            commentView = itemView.findViewById(R.id.count_ll);
            countTv = (TextView) itemView.findViewById(R.id.count_tv);
            parentView = itemView.findViewById(R.id.parent_view);
            parentView.setOnClickListener(HomeNewsAdapter.this);
        }
    }

    public class EmptyVH extends RecyclerView.ViewHolder {

        TextView titleTv;
        View parentView;

        private EmptyVH(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            parentView = itemView.findViewById(R.id.parent_view);
        }
    }

}
