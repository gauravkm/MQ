package com.zophop.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

public class RabbitTransport implements Transport {

    private Connection _connection;
    private Channel _channel;
    private String _exchangeName;
    private String _routingKey;

    public RabbitTransport(String username, String password, String virtualHost, String host, int port, String exchangeName, String routingKey) throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        _connection = connectionFactory.newConnection();
        _channel = _connection.createChannel();
        _channel.exchangeDeclare(exchangeName, "direct", true);
        String queueName = _channel.queueDeclare().getQueue();
        _channel.queueBind(queueName, exchangeName, routingKey);
        _exchangeName = exchangeName;
        _routingKey = routingKey;
    }

    @Override
    public void sendMessage(String message) throws IOException {
        _channel.basicPublish(_exchangeName, _routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }
}
