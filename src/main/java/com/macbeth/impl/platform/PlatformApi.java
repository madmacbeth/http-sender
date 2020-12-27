package com.macbeth.impl.platform;

import com.macbeth.enumeration.MediaTypeEnum;

import java.util.Map;

/**
 * 桥接模式 平台API实现的抽象
 * 主要关注点：
 *  1、平台
 *  2、参数
 *  3、媒体类型
 *  4、url
 * @author chen
 * @date 2020-12-26
 */
public interface PlatformApi {
    /**
     * 获取参数
     * @return
     */
    Map<String, Object> getParameters();
    /**
     * 获取媒体类型
     * @return
     */
    MediaTypeEnum getMediaType();

    /**
     * 获取URL
     * @return
     */
    String getUrl();
}
