package com.sumansoul.sspush.xiaomi;

import android.content.Context;

import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.logs.PushLog;
import com.sumansoul.push.util.PushUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

import static com.sumansoul.push.core.annotation.CommandType.TYPE_ADD_TAG;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_BIND_ALIAS;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_DEL_TAG;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_REGISTER;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_UNBIND_ALIAS;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_UNREGISTER;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_ERROR;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;
import static com.sumansoul.sspush.xiaomi.XiaoMiPushClient.MIPUSH_PLATFORM_NAME;

public class XiaoMiPushReceiver extends PushMessageReceiver {
    private static final String TAG = "MiPush-";

    /*方法用来接收服务器向客户端发送的透传消息。*/
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        PushLog.d(TAG + "[onReceivePassThroughMessage]:" + miPushMessage);
        SSPushCore.transmitMessage(context, miPushMessage.getContent(), miPushMessage.getDescription(), miPushMessage.getExtra());
    }

    /*方法用来接收服务器向客户端发送的通知消息，这个回调方法会在用户手动点击通知后触发。*/
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        PushLog.d(TAG + "[onNotificationMessageClicked]:" + miPushMessage);
        SSPushCore.transmitNotificationClick(context, miPushMessage.getNotifyId(), miPushMessage.getTitle(), miPushMessage.getDescription(), miPushMessage.getContent(), miPushMessage.getExtra());
    }

    /*方法用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。*/
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        PushLog.d(TAG + "[onNotificationMessageArrived]:" + miPushMessage);
        SSPushCore.transmitNotification(context, miPushMessage.getNotifyId(), miPushMessage.getTitle(), miPushMessage.getDescription(), miPushMessage.getContent(), miPushMessage.getExtra());
    }
    /*方法用来接收客户端向服务器发送命令后的响应结果。*/

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage commandMessage) {
        super.onCommandResult(context, commandMessage);
        String command = commandMessage.getCommand();
        List<String> arguments = commandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        String log;

        int commandType = -1;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            commandType = TYPE_REGISTER;
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                //保存push token
                PushUtils.savePushToken(MIPUSH_PLATFORM_NAME, cmdArg1);
                log = context.getString(R.string.xiaomi_register_success);
            } else {
                log = context.getString(R.string.xiaomi_register_fail, commandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNREGISTER.equals(command)) {
            commandType = TYPE_UNREGISTER;
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.xiaomi_unregister_success);
            } else {
                log = context.getString(R.string.xiaomi_unregister_fail, commandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            commandType = TYPE_BIND_ALIAS;
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.xiaomi_set_alias_success, cmdArg1);
            } else {
                log = context.getString(R.string.xiaomi_set_alias_fail, commandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            commandType = TYPE_UNBIND_ALIAS;
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.xiaomi_unset_alias_success, cmdArg1);
            } else {
                log = context.getString(R.string.xiaomi_unset_alias_fail, commandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.xiaomi_set_account_success, cmdArg1);
            } else {
                log = context.getString(R.string.xiaomi_set_account_fail, commandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.xiaomi_unset_account_success, cmdArg1);
            } else {
                log = context.getString(R.string.xiaomi_unset_account_fail, commandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            commandType = TYPE_ADD_TAG;
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.xiaomi_subscribe_topic_success, cmdArg1);
            } else {
                log = context.getString(R.string.xiaomi_subscribe_topic_fail, commandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            commandType = TYPE_DEL_TAG;
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.xiaomi_unsubscribe_topic_success, cmdArg1);
            } else {
                log = context.getString(R.string.xiaomi_unsubscribe_topic_fail, commandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (commandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.xiaomi_set_accept_time_success, cmdArg1, cmdArg2);
            } else {
                log = context.getString(R.string.xiaomi_set_accept_time_fail, commandMessage.getReason());
            }
        } else {
            log = commandMessage.getReason();
        }

        PushLog.d(TAG + "[onCommandResult] is called. " + commandMessage.toString() + " reason:" + log);
        if (commandType != -1) {
            SSPushCore.transmitCommandResult(context, commandType,
                    commandMessage.getResultCode() == ErrorCode.SUCCESS ? RESULT_OK : RESULT_ERROR,
                    cmdArg1, null, commandMessage.getReason());
        }
    }
}
