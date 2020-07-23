package com.sumansoul.sspush.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.util.PushUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.helper.Logger;

import static cn.jpush.android.api.JPushInterface.EXTRA_MESSAGE;
import static cn.jpush.android.api.JPushInterface.EXTRA_PUSH_ID;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_REGISTER;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_ERROR;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;

/**
 * Created by Android Studio.
 * User: Administrator
 * Date: 2020/7/23
 * Time: 9:54
 */
public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "PushReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String registrationId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + registrationId);
                //send the Registration Id to your server...

                SSPushCore.transmitCommandResult(context, TYPE_REGISTER,
                        TextUtils.isEmpty(registrationId) ? RESULT_ERROR : RESULT_OK,
                        registrationId, null, null);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(EXTRA_MESSAGE));
                SSPushCore.transmitMessage(context, bundle.getString(EXTRA_MESSAGE), bundle.getString(EXTRA_MESSAGE),  printBundle(bundle));
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                try {
                    SSPushCore.transmitNotificationClick(context, bundle.getInt(EXTRA_PUSH_ID),bundle.getString(EXTRA_MESSAGE), bundle.getString(EXTRA_MESSAGE), bundle.getString(EXTRA_MESSAGE),
                            PushUtils.toMap(new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))));
                } catch (Exception e) {
                    e.printStackTrace();
                    SSPushCore.transmitNotificationClick(context, bundle.getInt(EXTRA_PUSH_ID),bundle.getString(EXTRA_MESSAGE), bundle.getString(EXTRA_MESSAGE), bundle.getString(EXTRA_MESSAGE),
                           null);
                }
            } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e){

        }


    }

    // 打印所有的 intent extra 数据
    private static HashMap printBundle(Bundle bundle) {

        HashMap<String ,String> map = new HashMap();
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                //sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                // sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    //CommonUtils.CustLogI(TAG, "This message has no Extra data");
                    continue;
                }


                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");

                        map.put(myKey, json.optString(myKey));
                    }
                } catch (JSONException e) {
                    // CommonUtils.CustLog(TAG, "Get message extra JSON error!");
                }

            } else {
                //sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return map;
    }


}
