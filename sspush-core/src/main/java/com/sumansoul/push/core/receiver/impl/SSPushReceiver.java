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

package com.sumansoul.push.core.receiver.impl;

import android.content.Context;

import com.sumansoul.push.core.IPushClient;
import com.sumansoul.push.core.SSPushManager;
import com.sumansoul.push.entity.SSPushCommand;
import com.sumansoul.push.entity.SSPushMsg;

/**
 * 消息推送统一接收管理
 *
 * @author admin
 * @since 2019-08-17 15:58
 */
public class SSPushReceiver extends AbstractPushReceiver {

    @Override
    public void onNotification(Context context, SSPushMsg msg) {
        if (msg == null) {
            return;
        }

        SSPushManager.get().notifyNotification(msg.toNotification());
    }

    /**
     * 收到通知点击事件
     *
     * @param context
     * @param msg     消息
     */
    @Override
    public void onNotificationClick(Context context, SSPushMsg msg) {
        if (msg == null) {
            return;
        }

        SSPushManager.get().notifyNotificationClick(msg.toNotification());
    }

    /**
     * 收到自定义消息
     *
     * @param context
     * @param msg     消息
     */
    @Override
    public void onMessageReceived(Context context, SSPushMsg msg) {
        if (msg == null) {
            return;
        }

        SSPushManager.get().notifyMessageReceived(msg.toCustomMessage());
    }

    /**
     * IPushClient执行命令的结果返回
     *
     * @param context
     * @param command 命令实体
     * @see IPushClient#register()
     * @see IPushClient#unRegister()
     * @see IPushClient#addTags(String...)
     * @see IPushClient#deleteTags(String...)
     * @see IPushClient#getTags()
     * @see IPushClient#bindAlias(String)
     * @see IPushClient#unBindAlias(String)
     * @see IPushClient#getAlias()
     */
    @Override
    public void onCommandResult(Context context, SSPushCommand command) {
        if (command == null) {
            return;
        }

        SSPushManager.get().notifyCommandResult(command);
    }
}
