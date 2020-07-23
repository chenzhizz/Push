package com.sumansoul.sspush.oppo;

import android.content.Context;

import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.service.CompatibleDataMessageCallbackService;
import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.logs.PushLog;

/*透传消息处理，应用可以打开页面或者执行命令,如果应用不需要处理透传消息，则不需要重写此方法*/
public class OPPOPushService extends CompatibleDataMessageCallbackService {
    private static final String TAG = "OPPOPush-";
    @Override
    public void processMessage(Context context, DataMessage dataMessage) {
        super.processMessage(context, dataMessage);
        String msg = dataMessage.getContent();
        PushLog.d(TAG + "[onPushMsg]:" + msg);
        SSPushCore.transmitMessage(this, msg, null, null);
//        SSPushCore.transmitNotification(context,
//                dataMessage.getNotifyID(),
//                dataMessage.getTitle(),
//                dataMessage.getDescription(),
//                dataMessage.getContent(),
//                dataMessage.getExtra());

    }
}
