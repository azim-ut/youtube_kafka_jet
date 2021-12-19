package messages;

import bean.Type;
import bean.Source;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Message {

    protected Type type;
    protected Source source;

    public String getCode(){
        return source.name() + "_" + type.name();
    }
}
