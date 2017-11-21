package com.qiaoxg.northfootball.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseActivity;
import com.qiaoxg.northfootball.ui.fragment.DataFragment;
import com.qiaoxg.northfootball.ui.fragment.HomeFragment;
import com.qiaoxg.northfootball.ui.fragment.MineFragment;
import com.qiaoxg.northfootball.ui.fragment.SaiChengNewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.mainPage_iv)
    ImageView mainPageIv;
    @BindView(R.id.mainPage_tv)
    TextView mainPageTv;
    @BindView(R.id.mainPage_btn)
    LinearLayout mainPageBtn;
    //    @BindView(R.id.collectionPage_iv)
//    ImageView collectionPageIv;
//    @BindView(R.id.collectionPage_tv)
//    TextView collectionPageTv;
    @BindView(R.id.minePage_iv)
    ImageView minePageIv;
    @BindView(R.id.minePage_tv)
    TextView minePageTv;
    @BindView(R.id.dataPage_iv)
    ImageView dataPageIv;
    @BindView(R.id.dataPage_tv)
    TextView dataPageTv;
    @BindView(R.id.saiChengPage_iv)
    ImageView saiChengPageIv;
    @BindView(R.id.saiChengPage_tv)
    TextView saiChengPageTv;
    @BindView(R.id.mine_information)
    RelativeLayout mineInformation;
    @BindView(R.id.profit_rl)
    RelativeLayout profitRl;
    @BindView(R.id.account_rl)
    RelativeLayout accountRl;
    @BindView(R.id.ly_leftView)
    LinearLayout lyLeftView;
    @BindView(R.id.main_drawerLayout)
    DrawerLayout mDrawerLayout;

    private List<ImageView> mMainImgList;
    private List<TextView> mMainTextList;

    private Fragment mHomeFragment, mSaiChengFragmentNew, mMineFragment, mDataFragment;
    private final String FRAGMENT_TAG_HOME = "home";
    private final String FRAGMENT_TAG_DATA = "data";
    private final String FRAGMENT_TAG_MINE = "mine";
    private final String FRAGMENT_TAG_SAICHENG = "saiCheng";
    //    private final String FRAGMENT_TAG_COLLECTION = "collection";
    private final String[] fragmentTags = new String[]{FRAGMENT_TAG_HOME, FRAGMENT_TAG_SAICHENG,
            FRAGMENT_TAG_DATA, FRAGMENT_TAG_MINE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        mainPageBtn.performClick();
    }

    private void initView() {
        mMainImgList = new ArrayList<>();
        mMainImgList.add(mainPageIv);
        mMainImgList.add(saiChengPageIv);
//        mMainImgList.add(collectionPageIv);
        mMainImgList.add(dataPageIv);
        mMainImgList.add(minePageIv);

        mMainTextList = new ArrayList<>();
        mMainTextList.add(mainPageTv);
        mMainTextList.add(saiChengPageTv);
//        mMainTextList.add(collectionPageTv);
        mMainTextList.add(dataPageTv);
        mMainTextList.add(minePageTv);

    }

    private void updateBottomState(int selectIdx) {
        for (int i = 0; i < mMainImgList.size(); i++) {
            ImageView iv = mMainImgList.get(i);
            TextView tv = mMainTextList.get(i);
            if (i == selectIdx) {
                iv.setSelected(true);
                tv.setTextColor(getResources().getColor(R.color.appMainColor));
            } else {
                iv.setSelected(false);
                tv.setTextColor(getResources().getColor(R.color.textColor));
            }
        }
    }

    public void switchFragmentView(View view) {
        int id = view.getId();
        int selectIdx = 0;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(manager, transaction);
        switch (id) {
            case R.id.mainPage_btn:
                selectIdx = 0;
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fragmentContain_view, mHomeFragment, FRAGMENT_TAG_HOME);
                }
                transaction.show(mHomeFragment);
                break;
//            case R.id.collectionPage_btn:
//                selectIdx = 1;
//                if (mCollectionFragment == null) {
//                    mCollectionFragment = new CollectionFragment();
//                    transaction.add(R.id.fragmentContain_view, mCollectionFragment, FRAGMENT_TAG_COLLECTION);
//                }
//                transaction.show(mCollectionFragment);
//                break;
            case R.id.saiChengPage_btn:
                selectIdx = 1;
                if (mSaiChengFragmentNew == null) {
                    mSaiChengFragmentNew = new SaiChengNewFragment();
                    transaction.add(R.id.fragmentContain_view, mSaiChengFragmentNew, FRAGMENT_TAG_SAICHENG);
                }
                transaction.show(mSaiChengFragmentNew);
                break;
            case R.id.dataPage_btn:
                selectIdx = 2;
                if (mDataFragment == null) {
                    mDataFragment = new DataFragment();
                    transaction.add(R.id.fragmentContain_view, mDataFragment, FRAGMENT_TAG_DATA);
                }
                transaction.show(mDataFragment);
                break;
            case R.id.minePage_btn:
                selectIdx = 3;
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.fragmentContain_view, mMineFragment, FRAGMENT_TAG_MINE);
                }
                transaction.show(mMineFragment);
                break;
        }
        updateBottomState(selectIdx);
        transaction.commit();
    }

    private void hideFragments(FragmentManager manager, FragmentTransaction transaction) {
        for (int i = 0; i < fragmentTags.length; i++) {
            Fragment fragment = manager.findFragmentByTag(fragmentTags[i]);
            if (fragment != null && fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
    }

    @OnClick({R.id.mine_information, R.id.profit_rl, R.id.account_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_information:
                break;
            case R.id.profit_rl:
                break;
            case R.id.account_rl:
                break;
        }
        mDrawerLayout.closeDrawer(lyLeftView);
    }
}
