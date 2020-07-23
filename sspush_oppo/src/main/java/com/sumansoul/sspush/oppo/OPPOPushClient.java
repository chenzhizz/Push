package com.sumansoul.sspush.oppo;

import android.content.Context;
import android.text.TextUtils;

import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.heytap.msp.push.mode.ErrorCode;
import com.sumansoul.push.SSPushCore;
import com.sumansoul.push.core.IPushClient;
import com.sumansoul.push.core.annotation.ResultCode;
import com.sumansoul.push.logs.PushLog;
import com.sumansoul.push.util.PushUtils;

import static com.sumansoul.push.core.annotation.CommandType.TYPE_REGISTER;
import static com.sumansoul.push.core.annotation.CommandType.TYPE_UNREGISTER;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_ERROR;
import static com.sumansoul.push.core.annotation.ResultCode.RESULT_OK;

/**
 * Created by Android Studio.
 * User: Administrator
 * Date: 2020/7/21
 * Time: 9:15
 */
public class OPPOPushClient implements IPushClient {

    public static final String OPPO_PUSH_PLATFORM_NAME = "OPPOPush";
    public static final int OPPO_PUSH_PLATFORM_CODE = 1004;
    private static final String TAG = "OPPOPush-";



    private String mAppSecret;
    private String mAppKey;
    private Context mContext;

    public ICallBackResultService pushCallBack = new ICallBackResultService() {
        @Override
        public void onRegister(int code, String s) {
            if (code== ResultCode.RESULT_OK) {
                PushLog.d(TAG +mContext.getString(R.string.oppo_register_success)+ " reason:" + s);
                PushUtils.savePushToken(OPPOPushClient.OPPO_PUSH_PLATFORM_NAME, s);
            }else{
                PushLog.d(TAG + mContext.getString(R.string.oppo_register_fail) + " reason:" + s);
            }
            SSPushCore.transmitCommandResult(mContext,TYPE_REGISTER,
                    code== ErrorCode.SUCCESS ? RESULT_OK : RESULT_ERROR, s, null, null);
        }

        @Override
        public void onUnRegister(int code) {
            if (code== ResultCode.RESULT_OK) {
                PushLog.d(TAG +mContext.getString(R.string.oppo_unregister_success));
                PushUtils.deletePushToken(OPPO_PUSH_PLATFORM_NAME);
            }else{
                PushLog.d(TAG + mContext.getString(R.string.oppo_unregister_fail));
            }
            SSPushCore.transmitCommandResult(mContext,TYPE_UNREGISTER,
                    code== ErrorCode.SUCCESS ? RESULT_OK : RESULT_ERROR, code+"", null, null);
        }

        @Override
        public void onSetPushTime(int code, String s) {
            PushLog.d(TAG + mContext.getString(R.string.oppo_push_time)+"code="+code+",s="+s);
        }

        @Override
        public void onGetPushStatus(int code, int status) {
//            if (code == 0 && status == 0) {
//                showResult("Push状态正常", "code=" + code + ",status=" + status);
//                PushLog.d(TAG + mContext.getString(R.string.oppo_unregister_fail));
//            } else {
//                showResult("Push状态错误", "code=" + code + ",status=" + status);
//                PushLog.d(TAG + mContext.getString(R.string.oppo_unregister_fail));
//            }
            PushLog.d(TAG + mContext.getString(R.string.oppo_push_state)+"code="+code+",status="+status);
        }

        @Override
        public void onGetNotificationStatus(int code, int status) {
            PushLog.d(TAG + mContext.getString(R.string.oppo_notify_state)+"code="+code+",status="+status);
//            if (code == 0 && status == 0) {
//                showResult("通知状态正常", "code=" + code + ",status=" + status);
//                PushLog.d(TAG + mContext.getString(R.string.oppo_unregister_fail));
//            } else {
//                showResult("通知状态错误", "code=" + code + ",status=" + status);
//                PushLog.d(TAG + mContext.getString(R.string.oppo_unregister_fail));
//            }
        }
    };

    @Override
    public void init(Context context) {
        mAppSecret = BuildConfig.OPPOPUSH_APPSECRET;
        mAppKey = BuildConfig.OPPOPUSH_APPKEY;
        mContext = context.getApplicationContext();
        HeytapPushManager.init(mContext, BuildConfig.DEBUG);
    }

    @Override
    public void register() {
        if (TextUtils.isEmpty(mAppSecret) || TextUtils.isEmpty(mAppKey)) {
            return;
        }
        HeytapPushManager.register(mContext, mAppKey, mAppSecret, pushCallBack);
    }

    @Override
    public void unRegister() {
        HeytapPushManager.unRegister();
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
        return PushUtils.getPushToken(OPPO_PUSH_PLATFORM_NAME);
    }

    @Override
    public int getPlatformCode() {
        return OPPO_PUSH_PLATFORM_CODE;
    }

    @Override
    public String getPlatformName() {
        return OPPO_PUSH_PLATFORM_NAME;
    }
}
