package demo.dao;

import demo.domain.RootFolder;
import demo.domain.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by luchabou on 27/02/2015.
 */
@Repository
public class WorkspaceDao {

    @Autowired
    private RootFolderDao rootFolderDao;

    public Workspace load(String database) {
        Workspace workspace = new Workspace();
        refresh(workspace, database);
        return workspace;
    }

    public void refresh(Workspace workspace, String database) {

        RootFolder modelsFolder = rootFolderDao.findById(RootFolder.ID_PREFIX+Workspace.MODELS, database);
        RootFolder templatesFolder = rootFolderDao.findById(RootFolder.ID_PREFIX+Workspace.TEMPLATES, database);
        RootFolder generatedsFolder = rootFolderDao.findById(RootFolder.ID_PREFIX+Workspace.GENERATEDS, database);
        RootFolder settingsFolder = rootFolderDao.findById(RootFolder.ID_PREFIX+Workspace.SETTINGS, database);

        workspace.setModels(modelsFolder);
        workspace.setTemplates(templatesFolder);
        workspace.setGenerateds(generatedsFolder);
        workspace.setSettings(settingsFolder);
    }

    public void save(Workspace workspace, String database) {

        rootFolderDao.save(workspace.getModels(), database);
        rootFolderDao.save(workspace.getTemplates(), database);
        rootFolderDao.save(workspace.getGenerateds(), database);
        rootFolderDao.save(workspace.getSettings(), database);

    }

}
