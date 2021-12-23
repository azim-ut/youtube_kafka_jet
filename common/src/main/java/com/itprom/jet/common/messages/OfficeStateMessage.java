package com.itprom.jet.common.messages;

import com.itprom.jet.common.bean.Source;
import com.itprom.jet.common.bean.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeStateMessage extends Message {

    public OfficeStateMessage() {
        this.source = Source.OFFICE;
        this.type = Type.STATE;
    }
}
