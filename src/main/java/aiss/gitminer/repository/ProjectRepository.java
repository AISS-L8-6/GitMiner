package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import aiss.gitminer.model.Project;
@Repository
public class ProjectRepository {
    List<Project> projects = new ArrayList<>();

    public ProjectRepository(){
        projects.add(new Project(
                UUID.randomUUID().toString(),
                "nombre ejemplo",
                "url ejemplo"
        ));
    }

    public List<Project> findAll() {
        return projects;
    }

    public Project findOne(String id){
        return projects.stream()
                .filter(Project -> Project.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Project create(Project project){
        Project newProject = new Project(
                UUID.randomUUID().toString(),
                project.getName(),
                project.getWebUrl()
        );
        projects.add(newProject);
        return newProject;
    }
}