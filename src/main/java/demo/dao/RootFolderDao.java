package demo.dao;

import com.mongodb.Mongo;
import demo.domain.RootFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by luchabou on 27/02/2015.
 */
@Repository
public class RootFolderDao {

    private static final String COLLECTION_FOLDERS = "docs";

    @Autowired
    private Mongo mongo;

    MongoTemplate mongoTemplate(String database) {
        return new MongoTemplate(mongo, database);
    }

    public RootFolder findById(String folderId, String database) {
        return mongoTemplate(database)
                .findById(folderId, RootFolder.class, COLLECTION_FOLDERS);
    }

    public void save(RootFolder folder, String database) {
        mongoTemplate(database)
                .save(folder, COLLECTION_FOLDERS);
    }

    public void clean(String folderId, String database) {
        RootFolder folder = findById(folderId, database);
        mongoTemplate(database)
                .remove(folder, COLLECTION_FOLDERS);
        // TODO : suppression des fichiers dans gridfs
    }

}
