package com.qiaoxg.northfootball.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseFragment;
import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.event.UserLoginEvent;
import com.qiaoxg.northfootball.presenter.MinePresenter;
import com.qiaoxg.northfootball.ui.activity.mine.LoginActivity;
import com.qiaoxg.northfootball.ui.activity.news.SynNewsActivity;
import com.qiaoxg.northfootball.ui.iview.IMineView;
import com.qiaoxg.northfootball.utils.UIHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/8/25.
 */

public class MineFragment extends BaseFragment implements IMineView {

    @BindView(R.id.pageTitle_tv)
    TextView titleTv;
    Unbinder unbinder;
    @BindView(R.id.header_iv)
    ImageView headerIv;
    @BindView(R.id.userInfo_btn)
    RelativeLayout userInfoBtn;
    @BindView(R.id.history_btn)
    RelativeLayout historyBtn;
    @BindView(R.id.sweep_btn)
    RelativeLayout sweepBtn;
    @BindView(R.id.refresh_btn)
    RelativeLayout refreshBtn;
    @BindView(R.id.aboutUs_btn)
    RelativeLayout aboutUsBtn;
    @BindView(R.id.settings_btn)
    RelativeLayout settingsBtn;
    @BindView(R.id.comment_btn)
    RelativeLayout commentBtn;
    @BindView(R.id.userName_tv)
    TextView userNameTv;
    @BindView(R.id.loginDay_tv)
    TextView loginDayTv;
    @BindView(R.id.unLogin_ll)
    LinearLayout unLoginLl;
    @BindView(R.id.goLogin_btn)
    TextView goLoginBtn;
    @BindView(R.id.login_ll)
    LinearLayout loginLl;

    private MinePresenter mPresenter;
    private boolean mIsLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initPresenter();
        return view;
    }

    private void initPresenter() {
        mPresenter = new MinePresenter(this);
        mPresenter.getCurrLoginUser();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUserLogin(UserLoginEvent event) {
        if (event.isOk()) {
            mPresenter.getCurrLoginUser();
        }
    }

    private void initView() {
        titleTv.setText("我的");
    }

    private void updateView(UserInfo user) {
        if (mIsLogin) {
            UIHelper.showView(unLoginLl, false);
            UIHelper.showView(loginLl, true);
            userNameTv.setText(user.getUserName());
            loginDayTv.setText("连续登录" + user.getLoginDays() + "天");
        } else {
            UIHelper.showView(unLoginLl, true);
            UIHelper.showView(loginLl, false);
        }

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

    @OnClick({R.id.userInfo_btn, R.id.history_btn, R.id.sweep_btn,
            R.id.refresh_btn, R.id.aboutUs_btn, R.id.settings_btn,
            R.id.comment_btn, R.id.goLogin_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userInfo_btn:
                UIHelper.showToast("用户信息");
                break;
            case R.id.goLogin_btn:
                LoginActivity.intoActivity(getActivity());
                break;
            case R.id.history_btn:
                break;
            case R.id.sweep_btn:
                break;
            case R.id.refresh_btn:
                SynNewsActivity.intoActivity(getActivity());
                break;
            case R.id.aboutUs_btn:
                break;
            case R.id.settings_btn:
                break;
            case R.id.comment_btn:
                break;
        }
    }

    @Override
    public void showTipView(String string) {

    }

    @Override
    public void hiddenTipView() {

    }

    @Override
    public void onCheckLogin(boolean isLogin, Object obj) {
        this.mIsLogin = isLogin;
        if (isLogin) {
            updateView((UserInfo) obj);
        }
    }

}
