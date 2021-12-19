package bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    private String boardName;
    private List<RoutePath> path;


    public boolean notAssigned(){
        return boardName == null;
    }
}
