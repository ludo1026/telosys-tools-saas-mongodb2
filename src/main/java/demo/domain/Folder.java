package demo.domain;

import demo.bean.Path;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.*;

/**
 * Created by luchabou on 27/02/2015.
 */
public class Folder implements Serializable {

    private final String absolutePath;
    private final String path;
    private final String name;
    private final Map<String, Folder> folders = new TreeMap<>();
    private final Map<String, File> files = new TreeMap<>();

    Folder() {
        this.absolutePath = null;
        this.path = null;
        this.name = null;
    }

    public Folder(String absolutePath) {
        this.absolutePath = absolutePath;
        Path path = Path.valueOf(absolutePath);
        this.path = path.getBasename();
        this.name = path.getFilename();
    }

    public Folder(Path path) {
        this.absolutePath = path.toString();
        this.path = path.getBasename();
        this.name = path.getFilename();
    }

    public void addFolder(Folder folder) {
        this.folders.put(folder.getName(), folder);
    }

    public void addFile(File file) {
        this.files.put(file.getName(), file);
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public Map<String, Folder> getFolders() {
        return folders;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public List<Folder> getFoldersAsList() {
        return new ArrayList<>(folders.values());
    }

    public List<File> getFilesAsList() {
        return new ArrayList<>(files.values());
    }
}
