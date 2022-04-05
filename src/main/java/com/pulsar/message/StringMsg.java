package com.pulsar.message;

/**
 * The class StringMsg is a business message definition.It can contain more information with request.
 */
public class StringMsg {
    
    String topic;
    String body; // message data

    public StringMsg() {
    }

    public StringMsg(String topic, String body) {
        this.topic = topic;
        this.body = body;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "StringMsg{" +
                "topic='" + topic + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
