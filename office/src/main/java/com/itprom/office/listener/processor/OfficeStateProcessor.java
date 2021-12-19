package com.itprom.office.listener.processor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.itprom.office.provider.AirPortsProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messages.AirPortStateMessage;
import messages.OfficeStateMessage;
import processor.MessageProcessor;
import processor.MessagesConverter;

@Slf4j
@Component("OFFICE_STATE")
@RequiredArgsConstructor
public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {

    private final MessagesConverter messagesConverter;
    private final AirPortsProvider airPortsProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void process(String jsonMessage) {
        airPortsProvider.getPorts().forEach(airPort -> {
            kafkaTemplate.sendDefault(messagesConverter.toJson(new AirPortStateMessage(airPort)));
        });
    }
}
