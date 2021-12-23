package com.itprom.jet.common.messages;

import com.itprom.jet.common.bean.Route;
import com.itprom.jet.common.bean.Source;
import com.itprom.jet.common.bean.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeRouteMessage extends Message {

    private Route route;

    public OfficeRouteMessage() {
        this.source = Source.OFFICE;
        this.type = Type.ROUTE;
    }

    public OfficeRouteMessage(Route val) {
        this();
        this.route = val;
    }
}
