package com.qiaoxg.northfootball.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseActivity;
import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.event.UserLoginEvent;
import com.qiaoxg.northfootball.presenter.LoginPresenter;
import com.qiaoxg.northfootball.ui.iview.ILoginView;
import com.qiaoxg.northfootball.utils.UIHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.backBtn_ll)
    LinearLayout mBackBtn;
    @BindView(R.id.pageTitle_tv)
    TextView mTitleTv;
    @BindView(R.id.phone_et)
    EditText mPhoneEt;
    @BindView(R.id.password_et)
    EditText mPasswordEt;
    @BindView(R.id.login_btn)
    TextView mLoginBtn;
    @BindView(R.id.register_btn)
    TextView mRegisterBtn;
    @BindView(R.id.findPassword_btn)
    TextView mFindPasswordBtn;
    @BindView(R.id.weiXin_btn)
    RelativeLayout mWeiXinBtn;
    @BindView(R.id.QQ_btn)
    RelativeLayout mQQBtn;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUserLogin(UserLoginEvent event) {
        if (event.isOk()) {
            this.finish();
        }
    }

    private void initView() {
        mTitleTv.setText("登录");
        UIHelper.showView(mBackBtn, true);
    }

    @OnClick({R.id.backBtn_ll, R.id.login_btn, R.id.register_btn, R.id.findPassword_btn, R.id.weiXin_btn, R.id.QQ_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn_ll:
                this.finish();
                break;
            case R.id.login_btn:
                mPresenter.login(mPhoneEt.getText().toString(), mPasswordEt.getText().toString());
                break;
            case R.id.register_btn:
                RegisterActivity.intoActivity(this);
                break;
            case R.id.findPassword_btn:
                break;
            case R.id.weiXin_btn:
                break;
            case R.id.QQ_btn:
                break;
        }
    }

    /**
     * 进入此activity的入口
     *
     * @param activity
     */
    public static void intoActivity(Activity activity) {
        Intent i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
    }

    @Override
    public void showTipView(String string) {

    }

    @Override
    public void hiddenTipView() {

    }

    @Override
    public void onLoginResult(boolean isOk, Object obj) {
        if (isOk) {

        } else {
            UIHelper.showToast((String) obj);
        }
    }
}
