package com.macbeth.impl.platform.god;

import com.macbeth.impl.platform.PlatformApi;

/**
 * 高德平台API抽象
 * @author chen
 * @date 2020-12-26
 */
public interface GodWebPlatformApi extends PlatformApi {
    /**
     * 获取高德开放平台的KEY值
     * @return
     */
    String getKey();
}
