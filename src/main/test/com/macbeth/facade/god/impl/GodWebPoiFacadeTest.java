package com.macbeth.facade.god.impl;

import com.google.common.collect.Lists;
import com.macbeth.Response;
import com.macbeth.TaskManager;
import com.macbeth.consumer.ConsumerTask;
import com.macbeth.facade.god.GodWebApiFacade;
import com.macbeth.impl.platform.god.impl.GodWebPoiWebPlatformApi;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class GodWebPoiFacadeTest {

    private String key = "d72806123ea2d5ae5c9daa51a150b027";
    private GodWebPoiWebPlatformApi godWebPoiWebPlatformApi;
    private GodWebPoiFacade godWebPoiFacade;

    private List<GodWebPoiFacade> godWebPoiFacades = Lists.newArrayList();


    @Before
    public void setUp() {
        List<String> resturants = Lists.newArrayList();
//        resturants.add("曹氏鸭脖");
        resturants.add("川西坝子");
        resturants.add("酒弋烧烤");
//        resturants.add("三顾冒菜");
        this.godWebPoiWebPlatformApi = GodWebPoiWebPlatformApi.builder()
                .key(key)
                .types("餐饮")
                .keyWords("川西坝子")
                .city("成都市")
                .cityLimit(true)
                .build();

        this.godWebPoiFacade = new GodWebPoiFacade(this.godWebPoiWebPlatformApi);
        resturants.stream().forEach(str -> {
            final GodWebPoiWebPlatformApi poiWebPlatformApi = GodWebPoiWebPlatformApi.builder()
                    .key(key)
                    .types("餐饮")
                    .keyWords(str)
                    .city("成都市")
                    .cityLimit(true)
                    .build();
            godWebPoiFacades.add(new GodWebPoiFacade(poiWebPlatformApi));
        });

    }

    @Test
    public void send() {
        final GodWebApiFacade godWebApiFacade = new GodWebPoiFacade(godWebPoiWebPlatformApi);
//        final Response response = godWebApiFacade.getResponse();
//        System.out.println(response.getContent());
    }


    @Test
    public void getNextPage() {
        final GodWebPoiFacade facade = new GodWebPoiFacade(this.godWebPoiWebPlatformApi);
        Response page = null;
        while (Objects.nonNull(page = facade.getNextPage())) {
            System.out.println(page.getContent());
        }
    }

    @Test
    public void getParallelAll() throws ExecutionException, InterruptedException {
        List<Response> responses = Lists.newArrayList();
        TaskManager.ProducerTaskGroup producerTaskGroup = null;
        for (int i = 0; i < godWebPoiFacades.size(); i++) {
            for (int j = 0; j < godWebPoiFacades.size(); j++) {
                final GodWebPoiFacade facade = godWebPoiFacades.get(j);
                final GodWebPoiFacade.GodWebPoiProducerTask producerTask = new GodWebPoiFacade.GodWebPoiProducerTask(facade, responses);
                producerTaskGroup = TaskManager.addProducerTask("godPoiProducerTask", producerTask);
            }
        }
        final List<Future> futures = producerTaskGroup.getFutures();
        System.out.println(futures.size());
        for (Future<List<Response>> future : futures) {
            if (Objects.isNull(future)) {
                System.out.println("为null");
                continue;
            }
//            TaskManager.addConsumerTask("godPoiConsumerTask", new GodWebPoiFacade.PoiConsumerTask(future));
            final List<Response> responseList = future.get();
//            responseList.stream().map(Response::getContent).forEach(System.out::println);
        }

//        while(! producerTaskGroup.isFinished()) {
//            TimeUnit.SECONDS.sleep(10);
//        }
    }


}