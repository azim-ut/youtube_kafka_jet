package com.itprom.jet.common.messages;

import com.itprom.jet.common.bean.AirPort;
import com.itprom.jet.common.bean.Source;
import com.itprom.jet.common.bean.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirPortStateMessage extends Message{

    private AirPort airPort;

    public AirPortStateMessage() {
        this.source = Source.AIRPORT;
        this.type = Type.STATE;
    }

    public AirPortStateMessage(AirPort airPort) {
        this();
        this.airPort = airPort;
    }
}
