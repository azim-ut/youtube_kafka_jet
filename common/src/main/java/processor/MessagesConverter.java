package processor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import messages.Message;

public class MessagesConverter {
    private final Gson gson = new Gson();

    public String extractCode(String data){
        return gson.fromJson(data, Message.class).getCode();
    }

    public <T extends Message> T extractMessage(String data, Class<T> clazz){
        return gson.fromJson(data, clazz);
    }

    public String toJson(Object message){
        return gson.toJson(message);
    }
}
