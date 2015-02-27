package demo.domain;

import demo.bean.Path;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * File : gridFSId is the identifier of the file in GridFS.
 */
public class File implements Serializable {

    public static final String FILE_EXTENSION_UNKNOWN = "[unknown]";

    /**
     * Path to the file with the file name
     */
    private final String absolutePath;
    /**
     * Path to the file
     */
    private final String path;
    /**
     * Name of the file (contains the file extension)
     */
    private final String name;
    /**
     * File extension
     */
    private final String ext;
    /**
     * GridFS identifier
     */
    private String gridFSId;

    File() {
        this.absolutePath = null;
        this.path = null;
        this.name = null;
        this.ext = null;
    }

    public File(String absolutePath) {
        this.absolutePath = absolutePath;
        Path path = Path.valueOf(absolutePath);
        this.path = path.getBasename();
        this.name = path.getFilename();
        this.ext = getFileExtension(name);
    }

    public File(Path path) {
        this.absolutePath = path.toString();
        this.path = path.getBasename();
        this.name = path.getFilename();
        this.ext = getFileExtension(name);
    }

    /**
     * Indicate if the file is already saved in database.
     * @return exists
     */
    public boolean isStored() {
        return this.getGridFSId() != null;
    }

    /**
     * Return the file extension from the file name.
     * @param filename file name
     * @return file extension
     */
    public static final String getFileExtension(String filename) {
        if(filename == null) {
            return null;
        }
        int posDot = filename.lastIndexOf(".");
        if(posDot != -1) {
            return filename.substring(posDot+1);
        }
        return File.FILE_EXTENSION_UNKNOWN;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public String getGridFSId() {
        return gridFSId;
    }

    public void setGridFSId(String gridFSId) {
        this.gridFSId = gridFSId;
    }
}
