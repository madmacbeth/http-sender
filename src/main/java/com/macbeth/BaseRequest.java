package com.macbeth;

import com.macbeth.impl.platform.PlatformApi;

/**
 * 桥接模式抽象类   关于http连接的请求发送的抽象
 * @author chen
 * @date 2020-12-26
 */
public abstract class BaseRequest {
    protected PlatformApi platformApi;

    public BaseRequest(PlatformApi platformApi) {
        this.platformApi = platformApi;
    }

    protected abstract Response perform();

    public Response send() {
        return this.perform();
    };
}
