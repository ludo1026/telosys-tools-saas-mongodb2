package demo.dao;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

/**
 * Created by luchabou on 27/02/2015.
 */
@Repository
class GridFSDao {

    @Autowired
    private Mongo mongo;

    GridFS gridFS(String database) {
        DB db = mongo.getDB(database);
        return new GridFS(db);
    }

    public InputStream load(String gridFSId, String database) {
        GridFSDBFile gridFSDBFile =
                gridFS(database).findOne(gridFSId);
        if(gridFSDBFile == null) {
            throw new RuntimeException("File not found in GridFS : "+gridFSId);
        }
        return gridFSDBFile.getInputStream();
    }

    public String create(InputStream in, String database) {
        GridFSInputFile gridFSInputFile = gridFS(database).createFile(in);
        gridFSInputFile.save();

        return gridFSInputFile.getId().toString();
    }

    public void update(String gridFSId, InputStream in, String database) {
        GridFSInputFile gridFSInputFile = gridFS(database).createFile(in);
        gridFSInputFile.save();
    }

    public void remove(String gridFSId, String database) {
        gridFS(database).remove(gridFSId);
    }

}
