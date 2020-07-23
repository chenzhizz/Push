/*
 * Copyright (C) 2019 adminjys(adminjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.sumansoul.push.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.sumansoul.push.core.annotation.PushAction;


/**
 *
 * @ProjectName:    SumanSoulStudent
 * @Package:        com.sumansoul.push.util
 * @ClassName:      PushUtils
 * @Description:    数据传输工具
 * @Author:         Administrator
 * @CreateDate:     2020/7/17 16:26
 * @UpdateUser:     更新者
 * @UpdateDate:    2020/7/17 16:26
 * @UpdateRemark:   更新说明
 * @Version:
 */
public final class TransmitDataUtils {

    private static final String INTENT_DATA_PUSH = "com.admin.SSPush.util.push_data";

    private TransmitDataUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 发送数据
     *
     * @param context
     * @param action  动作
     * @param data    数据
     */
    public static void sendPushData(Context context, @PushAction String action, Parcelable data) {
        Intent intent = new Intent(action);
        intent.putExtra(INTENT_DATA_PUSH, data);
        intent.addCategory(context.getPackageName());
        context.sendBroadcast(intent);
    }

    /**
     * 发送数据【解决8.0之后静态广播注册失效的问题】
     *
     * @param context
     * @param action    动作
     * @param component 广播接收器的组件
     * @param data      数据
     */
    public static void sendPushData(Context context, @PushAction String action, @NonNull ComponentName component, Parcelable data) {
        Intent intent = new Intent(action);
        intent.putExtra(INTENT_DATA_PUSH, data);
        intent.setComponent(component);
        intent.addCategory(context.getPackageName());
        context.sendBroadcast(intent);
    }

    /**
     * 解析数据
     *
     * @param intent
     * @param <T>
     * @return
     */
    public static <T extends Parcelable> T parsePushData(Intent intent) {
        return intent.getParcelableExtra(INTENT_DATA_PUSH);
    }

}
