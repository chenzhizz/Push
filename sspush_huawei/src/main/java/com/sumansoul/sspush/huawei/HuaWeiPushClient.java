package com.sumansoul.sspush.huawei;

import android.app.Application;
import android.content.Context;

import com.huawei.hms.aaid.HmsInstanceId;
import com.sumansoul.push.core.IPushClient;
import com.sumansoul.push.logs.PushLog;
import com.sumansoul.push.util.PushUtils;

/**
 *
 * @ProjectName:    SumanSoulStudent
 * @Package:        com.sumansoul.sspush.huawei
 * @ClassName:      HuaWeiPushClient
 * @Description:     创建华为推送
 * @Author:         Administrator
 * @CreateDate:     2020/7/17 16:37
 * @UpdateUser:     更新者
 * @UpdateDate:    2020/7/17 16:37
 * @UpdateRemark:   更新说明
 * @Version:
 */
public class HuaWeiPushClient  implements IPushClient {

    public static final String HUAWEI_PUSH_PLATFORM_NAME = "HuaweiPush";
    public static final int HUAWEI_PUSH_PLATFORM_CODE = 1002;

    private Application mApplication;



    @Override
    public void init(Context context) {
        if (context instanceof Application) {
            mApplication = (Application) context;
        } else {
            mApplication = (Application) context.getApplicationContext();
        }
    }

    @Override
    public void register() {

    }

    @Override
    public void unRegister() {
        final String token = getPushToken();
        new Thread() {
            @Override
            public void run() {
                try {
                  //  String appId = AGConnectServicesConfig.fromContext(mApplication).getString("client/app_id");
                    HmsInstanceId.getInstance(mApplication).deleteToken(BuildConfig.HUAWEIPUSH_APPID, "HCM");
                    PushUtils.deletePushToken(HUAWEI_PUSH_PLATFORM_NAME);
                    PushLog.d("deleteToken success." );
                } catch (Exception e) {
                    PushLog.d("deleteToken failed." + e);
                }
            }
        }.start();
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
        return PushUtils.getPushToken(HUAWEI_PUSH_PLATFORM_NAME);
    }

    @Override
    public int getPlatformCode() {

            return HUAWEI_PUSH_PLATFORM_CODE;
    }

    @Override
    public String getPlatformName() {
        return HUAWEI_PUSH_PLATFORM_NAME;
    }
}
