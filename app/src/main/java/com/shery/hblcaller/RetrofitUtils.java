package com.shery.hblcaller;

public class RetrofitUtils {
    private RetrofitUtils(){}
    public static final String BASE_URL = "http://192.168.0.110:8000/";

    public static NodeService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(NodeService.class);
    }
}
