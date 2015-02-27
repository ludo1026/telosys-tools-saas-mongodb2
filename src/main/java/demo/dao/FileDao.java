package demo.dao;

import demo.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

/**
 * Created by luchabou on 27/02/2015.
 */
@Repository
public class FileDao {

    @Autowired
    private GridFSDao gridFSDao;

    public InputStream load(File file, String database) {
        if(file.getGridFSId() == null) {
            return null;
        }
        InputStream in = gridFSDao.load(file.getGridFSId(), database);
        return in;
    }

    public void save(File file, InputStream in, String database) {
        if(file.getGridFSId() == null) {
            String gridFSId = gridFSDao.create(in, database);
            file.setGridFSId(gridFSId);
        } else {
            gridFSDao.update(file.getGridFSId(), in, database);
        }
    }

    public void remove(File file, String database) {
        if(file.getGridFSId() != null) {
            gridFSDao.remove(file.getGridFSId(), database);
        }
    }
}
