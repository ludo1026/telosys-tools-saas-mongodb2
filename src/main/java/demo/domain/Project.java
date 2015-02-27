package demo.domain;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by luchabou on 27/02/2015.
 */
public class Project implements Serializable {

    public static final String ID = "project";

    @Id
    private final String id = ID;
    private final String name;

    public Project(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
