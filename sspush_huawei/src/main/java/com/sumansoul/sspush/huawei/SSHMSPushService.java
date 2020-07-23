package com.sumansoul.sspush.huawei;

import android.content.Intent;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.logs.PushLog;
import com.sumansoul.push.util.PushUtils;

import static com.sumansoul.push.core.annotation.CommandType.TYPE_REGISTER;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;

public class SSHMSPushService extends HmsMessageService {
    private static final String TAG = "SSHMSPushService";


    public SSHMSPushService() {
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.i(TAG, "receive token:" + token);
        sendTokenToDisplay(token);
        PushLog.d(TAG + "[onToken]:" + token);
        PushUtils.savePushToken(HuaWeiPushClient.HUAWEI_PUSH_PLATFORM_NAME, token);
        SSPushCore.transmitCommandResult(this, TYPE_REGISTER, RESULT_OK, token, null, null);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().length() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        String msg = remoteMessage.getData();
        PushLog.d(TAG + "[onPushMsg]:" + msg);
        SSPushCore.transmitMessage(this, msg, null, null);
    }

    private void sendTokenToDisplay(String token) {
        Intent intent=new Intent("com.huawei.codelabpush.ON_NEW_TOKEN");
        intent.putExtra("token", token);
        sendBroadcast(intent);
    }
}
