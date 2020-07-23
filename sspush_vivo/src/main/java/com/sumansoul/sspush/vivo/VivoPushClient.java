package com.sumansoul.sspush.vivo;

import android.content.Context;

import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.core.IPushClient;
import com.sumansoul.push.util.PushUtils;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

import static com.sumansoul.push.core.annotation.CommandType.TYPE_GET_ALIAS;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_REGISTER;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;

/**
 * Created by Android Studio.
 * User: Administrator
 * Date: 2020/7/22
 * Time: 11:48
 */
public class VivoPushClient implements IPushClient {
    public Context mContext;
    public static final String VIVO_PUSH_PLATFORM_NAME = "VIVOPush";
    public static final int VIVO_PUSH_PLATFORM_CODE = 1005;
    @Override
    public void init(Context context) {
        mContext=context;
    }

    @Override
    public void register() {
        PushClient.getInstance(mContext).initialize();
        PushUtils.savePushToken(VivoPushClient.VIVO_PUSH_PLATFORM_NAME,  PushClient.getInstance(mContext).getRegId());
        SSPushCore.transmitCommandResult(mContext, TYPE_REGISTER, RESULT_OK, PushClient.getInstance(mContext).getRegId(), null, null);
        PushClient.getInstance(mContext.getApplicationContext()).turnOnPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int state) {
//                LogUtils.i(TAG, "vivo 打开push getRegId: end state：" + state + " isSuc:" + (state == 0));
                SSPushCore.transmitMessage(mContext, "vivo 打开push", null, null);
            }
        });
    }

    @Override
    public void unRegister() {
        PushUtils.deletePushToken(VIVO_PUSH_PLATFORM_NAME);
    }

    @Override
    public void bindAlias(String alias) {
        PushClient.getInstance(mContext.getApplicationContext()).bindAlias(alias, new IPushActionListener() {
            @Override
            public void onStateChanged(int state) {
//                final PushDataBean pushData = new PushDataBean(PushConstants.HandlerWhat.WHAT_PUSH_ALIAS, state);
//                String reason;
//                if (state == VivoResultCode.VIVO_SUCCESS) {
//                    pushData.setAlias(alias);
//                    reason = "Vivo bindAlias Result SUCCESS msg=" + alias;
//                } else {
//                    reason = "Vivo bindAlias Result Failed code=" + state;
//                }
//                pushData.setReason(reason);
//                sendPushDataToService(context, pushData);

            }
        });
    }

    @Override
    public void unBindAlias(String alias) {
        PushClient.getInstance(mContext.getApplicationContext()).unBindAlias(alias, new IPushActionListener() {
            @Override
            public void onStateChanged(int state) {
//                final PushDataBean pushData = new PushDataBean(PushConstants.HandlerWhat.WHAT_PUSH_UNALIAS, state);
//                String reason;
//                if (state == VivoResultCode.VIVO_SUCCESS) {
//                    pushData.setAlias(alias);
//                    reason = "Vivo unBindAlias Result SUCCESS msg=" + alias;
//                } else {
//                    reason = "Vivo unBindAlias Result Failed code=" + state;
//                }
//                pushData.setReason(reason);
//                sendPushDataToService(context, pushData);
            }
        });
    }

    @Override
    public void getAlias() {
        SSPushCore.transmitCommandResult(mContext, TYPE_GET_ALIAS,
                RESULT_OK,
                PushClient.getInstance(mContext).getAlias(), null, null);

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
         return PushUtils.getPushToken(VIVO_PUSH_PLATFORM_NAME);
    }

    @Override
    public int getPlatformCode() {
        return VIVO_PUSH_PLATFORM_CODE;
    }

    @Override
    public String getPlatformName() {
        return VIVO_PUSH_PLATFORM_NAME;
    }
}
