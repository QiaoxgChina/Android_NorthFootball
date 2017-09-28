package com.qiaoxg.northfootball.model.imp;

import android.content.Context;
import android.content.Intent;

import com.qiaoxg.northfootball.model.ISaiChengModel;
import com.qiaoxg.northfootball.model.local.SaiChengDbUtils;
import com.qiaoxg.northfootball.presenter.callback.BaseCallback;
import com.qiaoxg.northfootball.service.SynSaiChengIntentService;

import java.util.List;

/**
 * Created by admin on 2017/9/11.
 */

public class SaiChengModelImp implements ISaiChengModel {

    @Override
    public void synSaiCheng(Context context, BaseCallback callback) {
        context.startService(new Intent(context, SynSaiChengIntentService.class));
    }

    @Override
    public void selectSaiChengList(int pageIdx, int type, BaseCallback callback) {
        if (callback == null) {
            callback.onFailed("查询失败");
            return;
        }
        List list = SaiChengDbUtils.select(pageIdx);
        callback.onSuccess(list);

    }
}
