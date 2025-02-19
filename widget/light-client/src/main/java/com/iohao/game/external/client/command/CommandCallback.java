/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.game.external.client.command;

import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.external.core.message.ExternalMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 回调命令
 *
 * @author 渔民小镇
 * @date 2023-07-08
 */
@Getter
@Setter
@Slf4j
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CommandCallback {
    int msgId;
    Class<?> responseClass;
    /** 回调 */
    InputCallback callback;
    /** 请求参数 */
    Object requestData;

    public void callback(ExternalMessage externalMessage) {

        CommandResult commandResult = new CommandResult();
        commandResult.externalMessage = externalMessage;
        commandResult.requestData = requestData;

        byte[] data = externalMessage.getData();

        // 解码服务器响应的业务数据
        if (Objects.nonNull(this.responseClass)) {
            commandResult.value = DataCodecKit.decode(data, responseClass);
        }

        if (Objects.nonNull(callback)) {
            callback.callback(commandResult);
        }
    }
}
