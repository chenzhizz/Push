package com.sumansoul.sspush.gms;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.core.IPushClient;
import com.sumansoul.push.util.PushUtils;

import static com.sumansoul.push.core.annotation.CommandType.TYPE_REGISTER;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;

/**
 * Created by Android Studio.
 * User: Administrator
 * Date: 2020/7/22
 * Time: 15:28
 */
public class FcmPushClient implements IPushClient {

    public static final String FCM_PUSH_PLATFORM_NAME = "FCMPush";
    public static final int FCM_PUSH_PLATFORM_CODE = 1006;

    @Override
    public void init(final Context context) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                           // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        PushUtils.savePushToken(FcmPushClient.FCM_PUSH_PLATFORM_NAME, token);
                        SSPushCore.transmitCommandResult(context, TYPE_REGISTER, RESULT_OK, token, null, null);
                    }
                });

    }

    @Override
    public void register() {

    }

    @Override
    public void unRegister() {

    }

    @Override
    public void bindAlias(String alias) {

    }

    @Override
    public void unBindAlias(String alias) {

    }

    @Override
    public void getAlias() {

    }

    @Override
    public void addTags(String... tag) {

    }

    @Override
    public void deleteTags(String... tag) {

    }

    @Override
    public void getTags() {

    }

    @Override
    public String getPushToken() {
         return PushUtils.getPushToken(FCM_PUSH_PLATFORM_NAME);
    }

    @Override
    public int getPlatformCode() {
        return FCM_PUSH_PLATFORM_CODE;
    }

    @Override
    public String getPlatformName() {
        return FCM_PUSH_PLATFORM_NAME;
    }
}
