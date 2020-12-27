package com.macbeth.impl.platform.god.impl;

import com.macbeth.impl.platform.god.GodWebPlatformApi;

/**
 * 高德开放平台 web服务API
 * @author chen
 * @date 2020-12-26
 */
public abstract class BaseGodWebPlatformApi implements GodWebPlatformApi {
    private String key;
    private String url;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
