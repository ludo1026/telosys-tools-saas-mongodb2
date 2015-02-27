package demo.service;

import demo.bean.Path;
import demo.dao.FileDao;
import demo.dao.ProjectDao;
import demo.dao.WorkspaceDao;
import demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by luchabou on 27/02/2015.
 */
@Component
public class WorkspaceService {

    @Autowired
    private WorkspaceDao workspaceDao;
    @Autowired
    private FileDao fileDao;

    /**
     * Create the workspace for the project
     * @param projectId Project id
     * @return Workspace
     */
    public Workspace createWorkspace(String projectId) {
        Workspace workspace = new Workspace();

        workspace.setModels(new RootFolder(Workspace.MODELS));
        workspace.setTemplates(new RootFolder(Workspace.TEMPLATES));
        workspace.setGenerateds(new RootFolder(Workspace.GENERATEDS));
        workspace.setSettings(new RootFolder(Workspace.SETTINGS));

        workspaceDao.save(workspace, projectId);

        return workspace;
    }

    /**
     * Save the workspace
     * @param workspace Workspace
     */
    public void saveWorkspace(Workspace workspace, String projectId) {
        workspaceDao.save(workspace, projectId);
    }

    /**
     * Return the workspace for the project
     * @param projectId Project id
     * @return Workspace
     */
    public Workspace getWorkspace(String projectId) {
        return workspaceDao.load(projectId);
    }

    /**
     * Create a new folder in the folder.
     * @param absolutePath Absolute path
     * @param projectId Project id
     */
    public Folder createFolder(String absolutePath, String projectId) {
        Workspace workspace = getWorkspace(projectId);
        Path path = Path.valueOf(absolutePath);
        Folder folder = new Folder(path);
        Folder folderParent = getFolderForPath(workspace, path.getParent());
        folderParent.addFolder(folder);
        workspaceDao.save(workspace, projectId);
        return folder;
    }

    /**
     * Create a new file in the folder.
     * @param absolutePath Absolute path
     * @param in File content
     * @param projectId Project id
     */
    public File createFile(String absolutePath, InputStream in, String projectId) {
        Workspace workspace = getWorkspace(projectId);
        Path path = Path.valueOf(absolutePath);
        File file = new File(path);
        Folder folderParent = getFolderForPath(workspace, path.getParent());
        folderParent.addFile(file);
        fileDao.save(file, in, projectId);
        workspaceDao.save(workspace, projectId);
        return file;
    }

    /**
     * Update a new file.
     * @param file File
     * @param in File content
     * @param projectId Project id
     */
    public void saveFile(File file, InputStream in, String projectId) {
        fileDao.save(file, in, projectId);
    }

    /**
     * Remove file from the folder.
     * @param absolutePath Absolute path
     * @param projectId Project id
     */
    public void removeFile(String absolutePath, String projectId) {
        Workspace workspace = getWorkspace(projectId);
        Path path = Path.valueOf(absolutePath);
        File file = getFileForPath(workspace, path.getParent());
        Folder folderParent = getFolderForPath(workspace, path);
        fileDao.remove(file, projectId);
        folderParent.getFiles().remove(file.getName());
        workspaceDao.save(workspace, projectId);
    }

    /**
     * Return the file extension in the file name.
     * @param filename file name
     * @return file extension
     */
    public String getFileExtension(String filename) {
        if(filename == null) {
            return null;
        }
        int posDot = filename.lastIndexOf(".");
        if(posDot != -1) {
            return filename.substring(posDot+1);
        }
        return File.FILE_EXTENSION_UNKNOWN;
    }

    /**
     * Indicates if the file exists ot not
     * @param workspace workspace
     * @param path path
     * @return boolean
     */
    public boolean exists(Workspace workspace, String path) {
        return getFolderForPath(workspace, Path.valueOf(path)) != null && getFolderForPath(workspace, Path.valueOf(path)) != null;
    }

    /**
     * Get sub folder of the folder belong the path
     * @param path Path
     * @return Sub folder
     */
    public Folder getFolderForPath(Workspace workspace, Path path) {
        Folder currentFolder = getRootFolderForPath(workspace, path);
        for(int i=1; i<path.getNameCount(); i++) {
            String name = path.getName(i);
            if(currentFolder.getFolders().containsKey(name)) {
                currentFolder = currentFolder.getFolders().get(name);
            } else {
                return null;
            }
        }
        return currentFolder;
    }

    /**
     * Get sub folder of the folder belong the path
     * @param path Path
     * @return Sub folder
     */
    public File getFileForPath(Workspace workspace, Path path) {
        Folder currentFolder = getRootFolderForPath(workspace, path);
        if(path.getNameCount() > 1) {
            for (int i = 1; i < path.getNameCount() - 1; i++) {
                String name = path.getName(i);
                if (currentFolder.getFolders().containsKey(name)) {
                    currentFolder = currentFolder.getFolders().get(name);
                } else {
                    return null;
                }
            }
        }
        if(path.getNameCount() > 0) {
            String name = path.getName(path.getNameCount() - 1);
            if(currentFolder.getFiles().containsKey(name)) {
                return currentFolder.getFiles().get(name);
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Return the root folder corresponding to the path
     * @param path Path
     * @return Root folder
     */
    public RootFolder getRootFolderForPath(Workspace workspace, Path path) {
        return workspace.getRootFolderByName(path.getRootName());
    }

}
