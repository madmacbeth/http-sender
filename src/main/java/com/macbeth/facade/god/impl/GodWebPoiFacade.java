package com.macbeth.facade.god.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.macbeth.Response;
import com.macbeth.TaskManager;
import com.macbeth.consumer.ConsumerTask;
import com.macbeth.facade.god.GodWebApiFacade;
import com.macbeth.impl.GetRequest;
import com.macbeth.impl.platform.god.impl.GodWebPoiWebPlatformApi;
import com.macbeth.producer.ProducerTask;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen
 * @date 2020-12-27
 */
public class GodWebPoiFacade extends GodWebApiFacade {

    private AtomicInteger page = new AtomicInteger(0);
    private static String url = "https://restapi.amap.com/v3/place/text";
    private AtomicBoolean isFirstPage = new AtomicBoolean(true);

    private GodWebPoiWebPlatformApi godWebPoiWebPlatformApi;

    public GodWebPoiFacade(GodWebPoiWebPlatformApi godWebPoiWebPlatformApi) {

        godWebPoiWebPlatformApi.setUrl(url);
        this.godWebPoiWebPlatformApi = godWebPoiWebPlatformApi;
        this.request = new GetRequest(godWebPoiWebPlatformApi);
    }

    @Override
    public Response getNextPage() {

        if (Objects.isNull(this.godWebPoiWebPlatformApi.getOffset())) {
            this.godWebPoiWebPlatformApi.setOffset(25);
        }

        Integer page = this.godWebPoiWebPlatformApi.getPage();

        if (Objects.isNull(page)) {
            page = 1;
            this.page.set(1);
            this.godWebPoiWebPlatformApi.setPage(1);
        } else {
            this.page.set(page + 1);
            this.godWebPoiWebPlatformApi.setPage(this.page.get());
        }

        Response response = super.getResponse();

        if (response.getCode().intValue() != 200) {
            return response;
        }
        final String content = response.getContent();
        final JSONObject jsonObject = JSON.parseObject(content);
        final Integer status = jsonObject.getInteger(STATUS);
        final Integer count = jsonObject.getInteger(COUNT);
        final Integer offset = this.godWebPoiWebPlatformApi.getOffset();
        final JSONArray pois = jsonObject.getJSONArray(POIS);

        if (status.intValue() == 0 || Objects.isNull(pois) || pois.size() <= 0) {
            return null;
        }

        final int totalPage = (count + offset - 1) / offset;
        if (page > totalPage) return null;

        return response;
    }

    @Override
    public List<Response> getAll() {

        Response response = null;
        List<Response> result = Lists.newArrayList();
        while (Objects.nonNull(response = this.getNextPage())) {
            result.add(response);
        }
        return result;
    }

    public static class GodWebPoiProducerTask implements ProducerTask<List<Response>> {

        private GodWebPoiFacade facade;
        private List<Response> responses;

        public GodWebPoiProducerTask(GodWebPoiFacade facade, List<Response> responses) {
            this.facade = facade;
            this.responses = responses;
        }

        @Override
        public List<Response> call() {
            final List<Response> responses = this.facade.getAll();
            return responses;
        }
    }
    public static class PoiConsumerTask implements ConsumerTask<List<Response>> {

        private Future<List<Response>> future;
        public PoiConsumerTask(Future<List<Response>> future) {
            this.future = future;
        }
        @Override
        public List<Response> call() throws Exception {
            final List<Response> responses = future.get();
            responses.stream().map(Response::getContent).forEach(System.out::println);
            return null;
        }
    }
}
