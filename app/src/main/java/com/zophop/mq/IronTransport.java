package com.zophop.mq;

import java.io.IOException;

import io.iron.ironmq.Client;
import io.iron.ironmq.Queue;

public class IronTransport implements Transport {

    private Client _client;
    private Queue _queue;

    public IronTransport(String queueName) {
        _client = new Client();
        _queue = _client.queue(queueName);
    }

    @Override
    public void sendMessage(String message) throws IOException {
        _queue.push(message);
    }
}
