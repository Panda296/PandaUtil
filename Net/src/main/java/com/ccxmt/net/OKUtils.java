package com.ccxmt.net;

import java.util.Map;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class OKUtils {
    private static OKUtils utils;
    private OkHttpClient client;
    private Request.Builder builder;

    private OKUtils() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        builder = new Request.Builder();
    }

    public static OKUtils of() {
        if (utils == null) {
            utils = new OKUtils();
        }
        return utils;
    }

    public void Get(Map<String, String> params, String url, Callback callback) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        params.forEach(urlBuilder::addQueryParameter);
        HttpUrl httpUrl = urlBuilder.build();
        Request request = builder.url(httpUrl).get().build();
        client.newCall(request).enqueue(callback);
    }


    public void Post(Map<String, String> params, String url, Callback callback) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        params.forEach(bodyBuilder::addEncoded);
        FormBody body = bodyBuilder.build();
        Request request = builder.post(body).url(url).build();
        client.newCall(request).enqueue(callback);

    }

}
