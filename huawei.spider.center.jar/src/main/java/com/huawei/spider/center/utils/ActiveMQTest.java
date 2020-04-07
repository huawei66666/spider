package com.huawei.spider.center.utils;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQTest {

    public void producerSendText() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory("guest", "guest", "tcp://10.211.55.7:61616");
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue("my-mq");
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage("哈哈哈哈");
            producer.send(message);
            System.out.println("消息已发送！");

            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consumerGetMessage() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory("guest", "guest", "tcp://10.211.55.7:61616");
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("my-mq");
            MessageConsumer consumer = session.createConsumer(destination);
            Message message = consumer.receive();
            TextMessage textMessage = (TextMessage) message;
            System.out.println("消息已收到：" + textMessage.getText());

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ActiveMQTest test = new ActiveMQTest();
//        test.producerSendText();
        test.consumerGetMessage();
    }

}
