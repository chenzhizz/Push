package com.sumansoul.sspush.xiaomi;

import android.content.Context;
import android.text.TextUtils;

import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.core.IPushClient;
import com.sumansoul.push.util.PushUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import static com.sumansoul.push.core.annotation.CommandType.TYPE_GET_ALIAS;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_GET_TAG;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;

/**
 * Created by Android Studio.
 * User: Administrator
 * Date: 2020/7/20
 * Time: 14:53
 */
public class XiaoMiPushClient implements IPushClient {
    private String mAppId;
    private String mAppKey;
    private Context mContext;

    public static final String MIPUSH_PLATFORM_NAME = "MIPush";
    public static final int MIPUSH_PLATFORM_CODE = 1003;

    private static final String MIPUSH_APPID = "MIPUSH_APPID";
    private static final String MIPUSH_APPKEY = "MIPUSH_APPKEY";

    @Override
    public void init(Context context) {
        mContext = context.getApplicationContext();
        mAppId = BuildConfig.XIAOMIPUSH_APPID;
        mAppKey = BuildConfig.XIAOMIPUSH_APPKEY;
    }

    @Override
    public void register() {
        if (TextUtils.isEmpty(mAppId) || TextUtils.isEmpty(mAppKey)) {
            throw new IllegalArgumentException("xiaomi push appId or appKey is not init," +
                    "check you AndroidManifest.xml is has MIPUSH_APPID or MIPUSH_APPKEY meta-data flag please");
        }
        MiPushClient.registerPush(mContext, mAppId, mAppKey);
    }

    @Override
    public void unRegister() {
        String token = getPushToken();
        if (!TextUtils.isEmpty(token)) {
            MiPushClient.unregisterPush(mContext);
            PushUtils.deletePushToken(MIPUSH_PLATFORM_NAME);
        }
    }

    @Override
    public void bindAlias(String alias) {
        MiPushClient.setAlias(mContext, alias, null);
    }

    @Override
    public void unBindAlias(String alias) {

        MiPushClient.unsetAlias(mContext, alias, null);
    }

    @Override
    public void getAlias() {
        List<String> alias = MiPushClient.getAllAlias(mContext);
        SSPushCore.transmitCommandResult(mContext, TYPE_GET_ALIAS,
                RESULT_OK,
                PushUtils.collection2String(alias), null, null);
    }

    @Override
    public void addTags(String... tag) {
        MiPushClient.subscribe(mContext, tag[0], null);
    }

    @Override
    public void deleteTags(String... tag) {
        MiPushClient.unsubscribe(mContext, tag[0], null);
    }

    @Override
    public void getTags() {
        List<String> tags = MiPushClient.getAllTopic(mContext);
        SSPushCore.transmitCommandResult(mContext, TYPE_GET_TAG,
                RESULT_OK,
                PushUtils.collection2String(tags), null, null);
    }

    @Override
    public String getPushToken() {
        return PushUtils.getPushToken(MIPUSH_PLATFORM_NAME);
    }

    @Override
    public int getPlatformCode() {
        return MIPUSH_PLATFORM_CODE;
    }

    @Override
    public String getPlatformName() {
        return MIPUSH_PLATFORM_NAME;
    }
}
