package com.qiaoxg.northfootball.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.qiaoxg.northfootball.model.INewsModel;
import com.qiaoxg.northfootball.model.imp.NewsModelImp;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.ui.iview.IBaseView;

import static com.qiaoxg.northfootball.service.SynNewsIntentService.MY_SERVICE_ACTION_SYN_NEWS;
import static com.qiaoxg.northfootball.service.SynNewsIntentService.MY_SERVICE_PARAM_KEY_OK;
import static com.qiaoxg.northfootball.service.SynNewsIntentService.MY_SERVICE_PARAM_KEY_TIP;

/**
 * Created by admin on 2017/8/31.
 */

public class SynNewsPresenter {

    private static final String TAG = "SynNewsPresenter";

    private IBaseView iView;
    private INewsModel iModel;
    private MyBroadcastReceiver mReceiver;
    private Context mContext;
    LocalBroadcastManager manager;

    public SynNewsPresenter(IBaseView iview, Context context) {
        this.iModel = new NewsModelImp();
        this.iView = iview;
        this.mContext = context;

        manager = LocalBroadcastManager.getInstance(mContext);
        mReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MY_SERVICE_ACTION_SYN_NEWS);
        manager.registerReceiver(mReceiver, filter);
    }

    /**
     * 同步数据
     *
     * @param type
     * @param context
     */
    public void startSynNews(int type, Context context) {
        iModel.startSynNews(type, context, new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {

            }

            @Override
            public void onFailed(String msgString) {

            }
        });
    }

    public void onDestroy() {
        manager.unregisterReceiver(mReceiver);
    }

    public void clearData() {
        iModel.clearNewsData(new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {

            }

            @Override
            public void onFailed(String msgString) {

            }
        });
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case MY_SERVICE_ACTION_SYN_NEWS:
                    if (intent.getBooleanExtra(MY_SERVICE_PARAM_KEY_OK, false)) {
                        iView.hiddenTipView();
                    } else {
                        iView.showTipView(intent.getStringExtra(MY_SERVICE_PARAM_KEY_TIP));
                    }
                    break;
            }
        }
    }
}
