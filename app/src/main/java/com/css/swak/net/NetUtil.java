package com.css.swak.net;

import android.content.Context;

import org.chromium.net.CronetEngine;
import org.chromium.net.UploadDataProvider;
import org.chromium.net.UploadDataProviders;
import org.chromium.net.UrlRequest;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.chromium.net.UrlRequest.Builder.REQUEST_PRIORITY_HIGHEST;
import static org.chromium.net.UrlRequest.Builder.REQUEST_PRIORITY_LOW;

public class NetUtil {

    private static final String GET="GET";
//    private static final String HEAD="HEAD";
//    private static final String DELETE="DELETE";
    private static final String POST="POST";
    private static final String PUT="PUT";

    private static CronetEngine cronetEngine;

    private static Executor executor = Executors.newSingleThreadExecutor();

    public static synchronized CronetEngine initCronetEngine(Context context) {
        // Lazily create the Cronet engine.
        if (cronetEngine == null) {
            CronetEngine.Builder myBuilder = new CronetEngine.Builder(context);
            // Enable caching of HTTP data and
            // other information like QUIC server information, HTTP/2 protocol and QUIC protocol.
            cronetEngine = myBuilder
                    .enableHttpCache(CronetEngine.Builder.HTTP_CACHE_IN_MEMORY, 100 * 1024)
                    .enableHttp2(true)
                    .enableQuic(true)
                    .build();

            executor=Executors.newFixedThreadPool(5);
        }

//        startNetLog();

        return cronetEngine;
    }


//    /**
//     * Method to start NetLog to log Cronet events
//     */
//    private static void startNetLog() {
//        File outputFile;
//        try {
//            outputFile = File.createTempFile("cronet", "log",
//                    Environment.getExternalStorageDirectory());
//            cronetEngine.startNetLogToFile(outputFile.toString(), false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public static void postReq(String url, JSONObject param,MyUrlRequestCallback callback){

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                url, callback, executor);

        requestBuilder.setHttpMethod(POST);
        requestBuilder.setPriority(REQUEST_PRIORITY_HIGHEST);
        requestBuilder.disableCache();

        UploadDataProvider provider=UploadDataProviders.create(param.toString().getBytes());
        requestBuilder.setUploadDataProvider(provider,executor);

        requestBuilder.addHeader("Content-Type","application/json");

        requestBuilder.build().start();
    }


    public static void putReq(String url, JSONObject param,MyUrlRequestCallback callback){

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                url, callback, executor);

        requestBuilder.setHttpMethod(PUT);
        requestBuilder.setPriority(REQUEST_PRIORITY_HIGHEST);
        requestBuilder.disableCache();

        UploadDataProvider provider=UploadDataProviders.create(param.toString().getBytes());
        requestBuilder.setUploadDataProvider(provider,executor);

        requestBuilder.addHeader("Content-Type","application/json");

        requestBuilder.build().start();
    }


    public static void getReq(String url,MyUrlRequestCallback callback){

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                url, callback, executor);

        requestBuilder.setHttpMethod(GET);
        requestBuilder.setPriority(REQUEST_PRIORITY_LOW);
        requestBuilder.disableCache();

        requestBuilder.build().start();
    }
}
