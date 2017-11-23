package com.qiaoxg.northfootball.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiaoxg.basemodel.utils.SettingUtil;
import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseFragment;
import com.qiaoxg.northfootball.entity.NewsBean;
import com.qiaoxg.northfootball.event.UpdateNewsEvent;
import com.qiaoxg.northfootball.presenter.HomePresenter;
import com.qiaoxg.northfootball.ui.adapter.HomeNewsAdapter;
import com.qiaoxg.northfootball.ui.adapter.MyViewPagerAdapter;
import com.qiaoxg.northfootball.ui.iview.IHomeView;
import com.qiaoxg.northfootball.ui.widget.CustomRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_ALL;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_DONGQIUDI;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_HOT;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_HUPU;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_SINA;
import static com.qiaoxg.northfootball.app.AppConstants.NEWS_TYPE_ZHIBOBA;

/**
 * Created by admin on 2017/8/25.
 */

public class HomeFragment extends BaseFragment implements IHomeView, ViewPager.OnPageChangeListener {

    RecyclerView mCurrNewsRv;
    List<RecyclerView> mRecyclerViewList;
    List<HomeNewsAdapter> mAdapterList;
    List<CustomRefreshLayout> mXRefreshViewList;

    Unbinder unbinder;

    @BindView(R.id.pageTitle_tv)
    TextView mTitleTv;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mViewPagerTab;
//    @BindView(R.id.viewpagertab)
//    SmartTabLayout mViewPagerTab;

    private CustomRefreshLayout mXRefreshView;

    private HomeNewsAdapter mCurrAdapter;
    private HomePresenter mPresenter;

    private List<View> views;
    private int mCurrPageIdx = 0;
    private int mCurrNewsType = NEWS_TYPE_ALL;
    private boolean mIsLoadMore = false;

    public final String[] TITLES = {
            "推荐",
            "懂球帝",
            "直播吧",
            "新浪",
            "虎扑"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initPresenter();
        showView(0);
        return view;
    }

    private void initPresenter() {
        mPresenter = new HomePresenter(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUpdateNews(UpdateNewsEvent event) {
        if (event.isUpdate()) {
            getFirstPageNews();
        }
    }


    private void initView() {
        mTitleTv.setText("首页");

        views = new ArrayList<>();
        mRecyclerViewList = new ArrayList<>();
        mAdapterList = new ArrayList<>();
        mXRefreshViewList = new ArrayList<>();

        MyViewPagerAdapter myAdapter = new MyViewPagerAdapter(views, TITLES);
        for (int i = 0; i < TITLES.length; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_pager_account_detail, null);
            RecyclerView rv = (RecyclerView) view.findViewById(R.id.news_rv);
            mRecyclerViewList.add(rv);

            HomeNewsAdapter adapter = new HomeNewsAdapter(getActivity());
            mAdapterList.add(adapter);

            CustomRefreshLayout xRefreshView = (CustomRefreshLayout) view.findViewById(R.id.refreshview_xscrooll);
            mXRefreshViewList.add(xRefreshView);

            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(manager);
            rv.setAdapter(adapter);

            //防止scrollView自动滑动到底部
//            View parentView = view.findViewById(R.id.parent_View);
//            parentView.setFocusable(true);
//            parentView.setFocusableInTouchMode(true);
//            parentView.requestFocus();

            views.add(view);
        }
        mViewPager.setAdapter(myAdapter);
        mViewPager.setOnPageChangeListener(this);
//        mViewPagerTab.setViewPager(mViewPager);
        mViewPagerTab.setupWithViewPager(mViewPager);

    }

    @Override
    public void onResume() {
        super.onResume();
        getFirstPageNews();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mXRefreshView.setRefreshing(false);
                mXRefreshView.setLoadMore(false);
            }
        },1000);

    }

    @Override
    public void onSelectNews(boolean isOk, Object obj) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mXRefreshView.setRefreshing(false);
                mXRefreshView.setLoadMore(false);
            }
        },1000);
        if (isOk) {
            List<NewsBean> beans = (List<NewsBean>) obj;
            mCurrAdapter.setHomeDataList(mIsLoadMore, beans);
        } else {
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        showView(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showView(int position) {
//        View view = views.get(position);
//        BannelView bannel = (BannelView) view.findViewById(R.id.bannelViewLayout);
        mXRefreshView = mXRefreshViewList.get(position);
        mCurrAdapter = mAdapterList.get(position);
        mCurrNewsRv = mRecyclerViewList.get(position);

        mCurrNewsRv.setHasFixedSize(true);

        mXRefreshView.setFooterView(createFootView());
        mXRefreshView.setCanRefresh(true);
        mXRefreshView.setLoadMore(true);
        mXRefreshView.setOnPullRefreshListener(new CustomRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                getFirstPageNews();
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        mXRefreshView.setOnPushLoadMoreListener(new CustomRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mIsLoadMore = true;
                mCurrPageIdx++;
                mPresenter.getNewsList(mCurrPageIdx, mCurrNewsType);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
//        mXRefreshView.set(new XRefreshView.SimpleXRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getFirstPageNews();
//            }
//
//            @Override
//            public void onLoadMore(boolean isSilence) {
//                mIsLoadMore = true;
//                mCurrPageIdx++;
//                mPresenter.getNewsList(mCurrPageIdx, mCurrNewsType);
//            }
//        });
        if (position == 0) {
            mCurrNewsType = NEWS_TYPE_HOT;
//            UIHelper.showView(bannel, true);
//            List<Info> infos = new ArrayList<>();
//            infos.add(new Info("", "http://img1.dongqiudi.com/fastdfs2/M00/04/B0/640x256/crop/-/ChNy21msYJSAXa2tAAFqQ0irMzA033.jpg"));
//            infos.add(new Info("", "http://img1.dongqiudi.com/fastdfs2/M00/04/9B/640x256/crop/-/ChOqM1msQHKAShacAAD4804EzVQ157.jpg"));
//            infos.add(new Info("", "http://img1.dongqiudi.com/fastdfs2/M00/04/B9/640x256/crop/-/ChOqM1msxliAdryxAANwgKy_qWk564.jpg"));
//            infos.add(new Info("", "http://img1.dongqiudi.com/fastdfs2/M00/04/C0/640x256/crop/-/ChNy21mssZ2ACf-bAACJf09rV6E207.jpg"));
//            bannel.setData(infos, new BannelView.ImageCycleViewListener() {
//                @Override
//                public void onImageClick(Info info) {
//
//                }
//            });
        } else if (position == 1) {
            mCurrNewsType = NEWS_TYPE_DONGQIUDI;
        } else if (position == 2) {
            mCurrNewsType = NEWS_TYPE_ZHIBOBA;
        } else if (position == 3) {
            mCurrNewsType = NEWS_TYPE_SINA;
        } else if (position == 4) {
            mCurrNewsType = NEWS_TYPE_HUPU;
        }
        getFirstPageNews();
    }

    private void getFirstPageNews() {
        mIsLoadMore = false;
        mCurrPageIdx = 0;
        mPresenter.getNewsList(mCurrPageIdx, mCurrNewsType);
    }

    //创建加载更多布局
    private View footView;
    private ProgressBar footBar;
    private TextView footText;
    private View createFootView() {
        footView = LayoutInflater.from(mXRefreshView.getContext()).inflate(R.layout.recycler_load_more_layout, new RelativeLayout(getActivity()), false);
        footBar = (ProgressBar) footView.findViewById(R.id.bar);
        footText = (TextView) footView.findViewById(R.id.tv_rv_foot_item);
        return footView;
    }

}
