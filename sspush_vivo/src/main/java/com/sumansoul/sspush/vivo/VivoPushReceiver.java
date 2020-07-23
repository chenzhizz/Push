package com.sumansoul.sspush.vivo;

import android.content.Context;

import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.logs.PushLog;
import com.sumansoul.push.util.PushUtils;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

import static com.sumansoul.push.core.annotation.CommandType.TYPE_REGISTER;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;

public class VivoPushReceiver extends OpenClientPushMessageReceiver {


    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        PushLog.d(TAG + "[onNotificationMessageClicked]:" + upsNotificationMessage);
        SSPushCore.transmitNotificationClick(context, (int) upsNotificationMessage.getMsgId(), upsNotificationMessage.getTitle(), upsNotificationMessage.getContent(), upsNotificationMessage.getContent(), upsNotificationMessage.getParams());

    }
    /***
     * 当首次turnOnPush成功或regId发生改变时，回调此方法

     * 如需获取regId，请使用PushClient.getInstance(context).getRegId()

     * @param context 应用上下文

     * @param regId 注册id

     */

    @Override
    public void onReceiveRegId(Context context, String regId) {
        PushUtils.savePushToken(VivoPushClient.VIVO_PUSH_PLATFORM_NAME, regId);
        SSPushCore.transmitCommandResult(context, TYPE_REGISTER, RESULT_OK, regId, null, null);
    }
}
