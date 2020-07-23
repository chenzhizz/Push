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

package com.sumansoul.push.core.annotation;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sumansoul.push.core.annotation.PushAction.RECEIVE_COMMAND_RESULT;
import static com.sumansoul.push.core.annotation.PushAction.RECEIVE_CONNECT_STATUS_CHANGED;
import static com.sumansoul.push.core.annotation.PushAction.RECEIVE_MESSAGE;
import static com.sumansoul.push.core.annotation.PushAction.RECEIVE_NOTIFICATION;
import static com.sumansoul.push.core.annotation.PushAction.RECEIVE_NOTIFICATION_CLICK;

/**
 * 推送广播动作
 *
 * @author admin
 * @since 2019-08-15 18:08
 */
@StringDef({RECEIVE_CONNECT_STATUS_CHANGED,
        RECEIVE_NOTIFICATION,
        RECEIVE_NOTIFICATION_CLICK,
        RECEIVE_MESSAGE,
        RECEIVE_COMMAND_RESULT
})
@Retention(RetentionPolicy.SOURCE)
public @interface PushAction {

    /**
     * 推送的连接状态发生改变
     */
    String RECEIVE_CONNECT_STATUS_CHANGED = "com.sumansoul.push.core.action.RECEIVE_CONNECT_STATUS_CHANGED";
    /**
     * 接收到通知
     */
    String RECEIVE_NOTIFICATION = "com.sumansoul.push.core.action.RECEIVE_NOTIFICATION";
    /**
     * 接收到通知点击
     */
    String RECEIVE_NOTIFICATION_CLICK = "com.sumansoul.push.core.action.RECEIVE_NOTIFICATION_CLICK";
    /**
     * 接收到自定义消息
     */
    String RECEIVE_MESSAGE = "com.sumansoul.push.core.action.RECEIVE_MESSAGE";
    /**
     * 接收到命令执行结果
     */
    String RECEIVE_COMMAND_RESULT = "com.sumansoul.push.core.action.RECEIVE_COMMAND_RESULT";

}
