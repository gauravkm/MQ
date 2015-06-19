package com.zophop.mq;

import android.util.Log;

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
                    "https", "mq-aws-us-east-1.iron.io", 443, BuildConfig.IRON_API_VERSION, BuildConfig.IRON_QUEUE_NAME);
        } else {
            try {
                _transport = new RabbitTransport(BuildConfig.RABBIT_USERNAME, BuildConfig.RABBIT_PASSWORD,
                        BuildConfig.RABBIT_VIRTUAL_HOST, BuildConfig.RABBIT_HOST, BuildConfig.RABBIT_PORT,
                        BuildConfig.RABBIT_EXCHANGE_NAME, BuildConfig.RABBIT_ROUTING_KEY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("LoadGenerator", "transport init done" + _transport);

    }

    public void generateLoad() {
        _executorService.scheduleAtFixedRate(new Runnable() {
            int counter = 0;

            @Override
            public void run() {
                try {
                    Log.d("LoadGenerator", "sending message" + counter);
                    _transport.sendMessage("" + counter + ":" + System.currentTimeMillis());
                    Log.d("LoadGenerator", "message sent" + counter);
                    counter++;
                } catch (IOException e) {
                    Log.e("LoadGenerator", "failure", e);
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
}
