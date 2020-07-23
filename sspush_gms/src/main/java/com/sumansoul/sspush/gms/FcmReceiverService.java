package com.sumansoul.sspush.gms;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.util.PushUtils;

import static com.sumansoul.push.core.annotation.CommandType.TYPE_REGISTER;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;

public class FcmReceiverService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("FcmReceiverService", "onMessageReceived");
        try {
            SSPushCore.transmitMessage(this, remoteMessage.getNotification().getBody(), null, remoteMessage.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewToken(String token) {
        PushUtils.savePushToken(FcmPushClient.FCM_PUSH_PLATFORM_NAME, token);
        SSPushCore.transmitCommandResult(this, TYPE_REGISTER, RESULT_OK, token, null, null);
    }

}
