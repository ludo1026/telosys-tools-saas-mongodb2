package demo.service;

import demo.Application;
import demo.domain.Workspace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Integration Test : project service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ProjectServiceIT {

    @Autowired
    ProjectService projectService;

    @Autowired
    WorkspaceService workspaceService;

    @Test
    public void testEmptyProjects() {

        projectService.createProject("project");
        //projectService.removeProject("project");

    }

    //@Test
    public void test100EmptyProjects() {

        for(int i=0; i<100; i++) {
            projectService.createProject("project_"+i);
        }
        for(int i=0; i<100; i++) {
            projectService.removeProject("project_"+i);
        }

    }

    // @Test
    public void test100Projects() {

        for(int i=0; i<100; i++) {
            String projectId = "project_"+i;
            projectService.createProject(projectId);
            Workspace workspace = workspaceService.getWorkspace(projectId);
            String path = "models";
            for(int j=0; j<100; j++) {
                path += "/" + String.valueOf(j);
                workspaceService.createFolder(path, projectId);
                byte[] byteArray = new byte[500000];
                for(int k=0; k<500000; k++) {
                    byteArray[k] = (byte)k;
                }
                ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
                workspaceService.createFile(path, in, projectId);

            }
            workspaceService.saveWorkspace(workspace, projectId);
        }
        for(int i=0; i<100; i++) {
            projectService.removeProject("project_"+i);
        }

    }

}
