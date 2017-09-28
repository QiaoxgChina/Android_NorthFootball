package com.qiaoxg.northfootball.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseActivity;
import com.qiaoxg.northfootball.entity.UserInfo;
import com.qiaoxg.northfootball.event.UserLoginEvent;
import com.qiaoxg.northfootball.presenter.RegisterPresenter;
import com.qiaoxg.northfootball.ui.iview.IRegisterView;
import com.qiaoxg.northfootball.utils.UIHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRegisterView {

    @BindView(R.id.backBtn_ll)
    LinearLayout mBackBtn;
    @BindView(R.id.pageTitle_tv)
    TextView mTitleTv;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.register_btn)
    TextView mRegisterBtn;

    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        initPresenter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUserLogin(UserLoginEvent event) {
        if (event.isOk()) {
            this.finish();
        }
    }

    private void initPresenter() {
        mPresenter = new RegisterPresenter(this);
    }

    private void initView() {
        mTitleTv.setText("用户注册");
        UIHelper.showView(mBackBtn, true);
    }

    @OnClick({R.id.backBtn_ll, R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn_ll:
                this.finish();
                break;
            case R.id.register_btn:
                mPresenter.register(phoneEt.getText().toString(), passwordEt.getText().toString());
                break;
        }
    }

    /**
     * 进入此activity的入口
     *
     * @param activity
     */
    public static void intoActivity(Activity activity) {
        Intent i = new Intent(activity, RegisterActivity.class);
        activity.startActivity(i);
    }

    @Override
    public void showTipView(String string) {

    }

    @Override
    public void hiddenTipView() {

    }

    @Override
    public void onRegisterResult(boolean isOk, Object obj) {
        if (isOk) {
        } else {
            UIHelper.showToast((String) obj);
        }
    }
}
