package com.qiaoxg.northfootball.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by admin on 2017/8/31.
 */

public class UpdateNewsEvent extends EventBus {

    boolean isUpdate;

    public UpdateNewsEvent(boolean isOk) {
        this.isUpdate = isOk;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

}
