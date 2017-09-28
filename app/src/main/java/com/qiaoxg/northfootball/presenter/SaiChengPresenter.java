package com.qiaoxg.northfootball.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.qiaoxg.northfootball.model.ISaiChengModel;
import com.qiaoxg.northfootball.model.imp.SaiChengModelImp;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.ui.iview.ISaiChengView;

import static com.qiaoxg.northfootball.service.SynNewsIntentService.MY_SERVICE_PARAM_KEY_OK;
import static com.qiaoxg.northfootball.service.SynSaiChengIntentService.MY_SERVICE_ACTION_SYN_SAICHENG;

/**
 * Created by admin on 2017/9/11.
 */

public class SaiChengPresenter {

    private static final String TAG = "SaiChengPresenter";

    private ISaiChengView iView;
    private ISaiChengModel iModel;
    private LocalBroadcastManager manager;
    private SaiChengReceiver mReceiver;
    private int mCurrIndex;

    public SaiChengPresenter(Context context, ISaiChengView iView) {
        this.iView = iView;
        this.iModel = new SaiChengModelImp();

        manager = LocalBroadcastManager.getInstance(context);
        mReceiver = new SaiChengReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MY_SERVICE_ACTION_SYN_SAICHENG);
        manager.registerReceiver(mReceiver, filter);
    }

    public void synSaiCheng(Context context) {
        iModel.synSaiCheng(context, null);
    }


    public class SaiChengReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case MY_SERVICE_ACTION_SYN_SAICHENG:
                    if (intent.getBooleanExtra(MY_SERVICE_PARAM_KEY_OK, false)) {
                        selectSaiCheng(0);
                    } else {
                    }
                    break;
            }
        }
    }

    public void selectSaiCheng(int padeIdx) {
        this.mCurrIndex = padeIdx;
        iModel.selectSaiChengList(mCurrIndex, 0, new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {
                iView.onSelectSaiCheng(true, obj);
            }

            @Override
            public void onFailed(String msgString) {
                iView.onSelectSaiCheng(false, msgString);
            }
        });
    }

}
