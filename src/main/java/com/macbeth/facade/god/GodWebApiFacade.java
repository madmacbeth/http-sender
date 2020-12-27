package com.macbeth.facade.god;

import com.macbeth.BaseRequest;
import com.macbeth.Response;

import java.util.List;

/**
 * 高德地图API门面
 * @author chen
 * @date 2020-12-27
 */
public abstract class GodWebApiFacade {
    protected static final String STATUS = "status";
    protected static final String COUNT = "count";
    protected static final String POIS = "pois";

    protected BaseRequest request;
    protected Response response;

    protected Response getResponse() {

        this.response = this.request.send();
        return this.response;
    }

    public abstract Response getNextPage();

    public abstract List<Response> getAll();
}
