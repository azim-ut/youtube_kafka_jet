package com.itprom.jet.common.processor;

import com.google.gson.Gson;
import com.itprom.jet.common.messages.Message;

public class MessageConverter {

    private final Gson gson = new Gson();

    public String extractCode(String data) {
        return gson.fromJson(data, Message.class).getCode();
    }

    public <T extends Message> T extractMessage(String data, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }

    public String toJson(Object message) {
        return gson.toJson(message);
    }
}
