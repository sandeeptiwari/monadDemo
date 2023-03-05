package org.example;

import org.example.supports.HttpHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppServiceImpl implements AppService {

    private final HttpHelper httpHelper;

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public AppServiceImpl(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    /*Function<String, IO<String>> callApi = host -> IO.of(() -> {

    })*/
}
