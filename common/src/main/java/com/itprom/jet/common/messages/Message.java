package com.itprom.jet.common.messages;

import com.itprom.jet.common.bean.Source;
import com.itprom.jet.common.bean.Type;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Message {
    protected Type type;
    protected Source source;

    public String getCode() {
        return source.name() + "_" + type.name();
    }
}
