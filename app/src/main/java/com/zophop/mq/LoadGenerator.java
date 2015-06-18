package com.zophop.mq;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoadGenerator {

    private Transport _transport;
    private ScheduledExecutorService _executorService;

    public LoadGenerator() {
        _executorService = Executors.newSingleThreadScheduledExecutor();
        if (BuildConfig.QUEUE_TYPE.equals("IRON")) {
            _transport = new IronTransport(BuildConfig.IRON_PROJECT_ID, BuildConfig.IRON_TOKEN,
                    BuildConfig.IRON_CLOUD, BuildConfig.IRON_API_VERSION, BuildConfig.IRON_QUEUE_NAME);
        }

    }

    public void generateLoad() {
        _executorService.scheduleAtFixedRate(new Runnable() {
            int counter = 0;

            @Override
            public void run() {
                try {
                    _transport.sendMessage("" + counter);
                    counter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}
