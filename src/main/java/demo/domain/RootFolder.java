package demo.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by luchabou on 27/02/2015.
 */
public class RootFolder extends Folder {

    public static final String ID_PREFIX = "rootFolder:";

    @Id
    private final String id;

    public RootFolder(String name) {
        super(name);
        this.id = ID_PREFIX + this.getName();
    }

    public String getId() {
        return id;
    }

}
