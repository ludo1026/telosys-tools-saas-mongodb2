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
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private WorkspaceService workspaceService;

    /**
     * Create a new project.
     * @param projectId ProjectId
     * @return project
     */
    public Project createProject(String projectId) {
        Project project = new Project(projectId);
        projectDao.save(project, projectId);
        workspaceService.createWorkspace(projectId);
        return project;
    }

    public Project removeProject(String projectId) {
        Project project = new Project(projectId);
        projectDao.remove(projectId);
        return project;
    }

}
