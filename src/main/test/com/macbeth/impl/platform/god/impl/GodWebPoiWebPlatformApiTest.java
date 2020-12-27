package com.macbeth.impl.platform.god.impl;

import com.macbeth.BaseRequest;
import com.macbeth.Response;
import com.macbeth.impl.GetRequest;
import org.junit.Test;

public class GodWebPoiWebPlatformApiTest {

    @Test
    public void sendGodWebPoiRequest() {
        final GodWebPoiWebPlatformApi webPoiWebPlatformApi = GodWebPoiWebPlatformApi.builder()
//                .url("https://restapi.amap.com/v3/place/text")
//                .key("d72806123ea2d5ae5c9daa51a150b027")
                .city("成都市")
                .cityLimit(true)
                .types("餐饮")
                .keyWords("川西坝子")
                .build();
        final BaseRequest request = new GetRequest(webPoiWebPlatformApi);
        final Response response = request.send();
        System.out.println(response.getContent());
    }

}