package com.qiaoxg.northfootball.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.qiaoxg.basemodel.refreshview.XRefreshView;
import com.qiaoxg.basemodel.refreshview.listener.OnBottomLoadMoreTime;
import com.qiaoxg.basemodel.refreshview.listener.OnTopRefreshTime;
import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseFragment;
import com.qiaoxg.northfootball.entity.SaiChengBean;
import com.qiaoxg.northfootball.presenter.SaiChengPresenter;
import com.qiaoxg.northfootball.ui.adapter.SaiChengAdapter;
import com.qiaoxg.northfootball.ui.iview.ISaiChengView;
import com.qiaoxg.northfootball.ui.widget.stickyListHeaders.CustomerFooter;
import com.qiaoxg.northfootball.ui.widget.stickyListHeaders.SmileyHeaderView;
import com.qiaoxg.northfootball.ui.widget.stickyListHeaders.StickyListHeadersListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/8/25.
 */

public class SaiChengNewFragment extends BaseFragment implements ISaiChengView {

    private static final String TAG = "SaiChengFragment";

    @BindView(R.id.pageTitle_tv)
    TextView titleTv;
    Unbinder unbinder;

    private XRefreshView mXRefreshView;
    private SaiChengAdapter mSaiChengAdapter;

    private SaiChengPresenter mPresenter;

    private StickyListHeadersListView stickyLv;
    private int mTotalItemCount;
    private final int mPinnedTime = 1000;
    private int mPageIndex = 0;

    private boolean isLoadMore = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saicheng_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initAdapter();
        initRefreshView(view);
        initPresenter();
        return view;
    }

    private void initAdapter() {
        mSaiChengAdapter = new SaiChengAdapter(getActivity());
    }

    private void initRefreshView(View view) {
        stickyLv = (StickyListHeadersListView) view.findViewById(R.id.sticky_list);
        stickyLv.setAdapter(mSaiChengAdapter);
        mXRefreshView = (XRefreshView) view.findViewById(R.id.custom_view);
        mXRefreshView.setPullLoadEnable(true);
        mXRefreshView.setPinnedTime(mPinnedTime);
        mXRefreshView.setCustomHeaderView(new SmileyHeaderView(getActivity()));
        mXRefreshView.setCustomFooterView(new CustomerFooter(getActivity()));
        mXRefreshView.setOnTopRefreshTime(new OnTopRefreshTime() {

            @Override
            public boolean isTop() {
                if(stickyLv == null){
                    return true;
                }
                if (stickyLv.getFirstVisiblePosition() == 0) {
                    View firstVisibleChild = stickyLv.getListChildAt(0);
                    if(firstVisibleChild == null){
                        return true;
                    }
                    return firstVisibleChild.getTop() >= 0;
                }
                return false;
            }
        });
        mXRefreshView.setOnBottomLoadMoreTime(new OnBottomLoadMoreTime() {

            @Override
            public boolean isBottom() {
                if (stickyLv.getLastVisiblePosition() == mTotalItemCount - 1) {
                    View lastChild = stickyLv.getListChildAt(stickyLv.getListChildCount() - 1);
                    if(lastChild == null){
                        return true;
                    }
                    return (lastChild.getBottom() + stickyLv.getPaddingBottom()) <= stickyLv.getMeasuredHeight();
                }
                return false;
            }
        });
        stickyLv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                mTotalItemCount = totalItemCount;
            }
        });
        mXRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                mPageIndex = 0;
                isLoadMore = false;
                mPresenter.selectSaiCheng(mPageIndex);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                mPageIndex++;
                isLoadMore = true;
                mPresenter.selectSaiCheng(mPageIndex);
            }
        });
    }

    private void initPresenter() {
        mPresenter = new SaiChengPresenter(getActivity(), this);
        mPresenter.synSaiCheng(getContext());
    }

    private void initView() {
        titleTv.setText("赛程");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showTipView(String string) {
    }

    @Override
    public void hiddenTipView() {

    }

    @Override
    public void onSelectSaiCheng(boolean isOk, Object obj) {
        mXRefreshView.stopLoadMore();
        mXRefreshView.stopRefresh();
        if (isOk) {
            mSaiChengAdapter.setDateList(isLoadMore, (List<SaiChengBean>) obj);
        }
    }
}
