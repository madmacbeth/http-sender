package com.macbeth.impl;

import com.macbeth.BaseRequest;
import com.macbeth.Response;
import com.macbeth.impl.platform.PlatformApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GetRequest extends BaseRequest {

    private Integer timeout;

    public GetRequest(PlatformApi platformApi) {
        super(platformApi);
    }

    @Override
    protected Response perform() {
        String content = null;
        try {
            final Map<String, Object> parameters = this.platformApi.getParameters();
            final String parameterStr = parameters.entrySet().stream()
                    .map(entry -> entry.getKey().toLowerCase() + "=" + entry.getValue().toString())
                    .collect(Collectors.joining("&"));

            final URL url = new URL(this.platformApi.getUrl() + "?" + parameterStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            if (Objects.nonNull(this.timeout))
                connection.setConnectTimeout(this.timeout);

            final InputStream is = connection.getInputStream();
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int index = 0;
            while ((index = is.read(buff)) != -1) {
                bos.write(buff, 0, index);
            }
            return Response.builder()
                    .code(200)
                    .msg("接口访问成功")
                    .content(bos.toString("UTF-8"))
                    .build();
        } catch (MalformedURLException e) {
            content = e.getMessage();
        } catch (IOException e) {
            content = e.getMessage();
        }
        return Response.builder()
                .content("400")
                .msg("接口访问失败")
                .content(content)
                .build();
    }
}
