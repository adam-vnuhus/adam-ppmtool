package com.adamvnuhus.ppmtool.services;

import com.adamvnuhus.ppmtool.domain.Backlog;
import com.adamvnuhus.ppmtool.domain.Project;
import com.adamvnuhus.ppmtool.exceptions.ProjectIdException;
import com.adamvnuhus.ppmtool.repositories.BacklogRepository;
import com.adamvnuhus.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ppmtool
 *
 * @author anhdt
 * @created_at 10/02/2021 - 10:04 PM
 * @created_by anhdt
 * @since 10/02/2021
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
        }
    }

    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist.");
        }

        return project;
    }

    public Iterable<Project> findAllProject() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException("Cannot delete Project with ID '"+projectId+"'. This project does not exist.");
        }

        projectRepository.delete(project);
    }
}
