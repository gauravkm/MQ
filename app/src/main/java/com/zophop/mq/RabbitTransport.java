package com.zophop.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class RabbitTransport implements Transport {

    private Connection _connection;
    private Channel _channel;
    private String _exchangeName;
    private String _routingKey;

    public RabbitTransport(String username, String password, String virtualHost, String host, int port) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        String uri = "amqp://ooduzhuy:K-YYchxMQBAQqi8h7JlBcmMyusyYQJrE@owl.rmq.cloudamqp.com/ooduzhuy";
        connectionFactory.setUri(uri);
        _connection = connectionFactory.newConnection();
        _channel = _connection.createChannel();
        String queue = "hello1";     //queue name
        boolean durable = true;    //durable - RabbitMQ will never lose the queue if a crash occurs
        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
        _channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
        _exchangeName = "";
        _routingKey = "hello1";
    }

    @Override
    public void sendMessage(String message) throws IOException {
        _channel.basicPublish(_exchangeName, _routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }
}
