package processor;


import messages.Message;

public interface MessageProcessor<T extends Message> {

    void process(String jsonMessage);
}
