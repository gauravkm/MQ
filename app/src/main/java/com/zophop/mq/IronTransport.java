package com.zophop.mq;

import android.util.Log;

import java.io.IOException;

import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;
import io.iron.ironmq.Queue;

public class IronTransport implements Transport {

    private Client _client;
    private Queue _queue;

    public IronTransport(String projectId, String token, String scheme, String host, int port, Integer apiVersion, String queueName) {
        _client = new Client(projectId, token, new Cloud(scheme, host, port), apiVersion);
        _queue = _client.queue(queueName);
    }

    @Override
    public void sendMessage(String message) throws IOException {
        Log.d("IronTransport", "trying message " + message);
        try {
            String push = _queue.push(message);
            Log.d("IronTransport", "message sent: " + push);
        } catch (Exception e) {
            Log.e("IronTransport", "exception", e);
        }
    }
}
