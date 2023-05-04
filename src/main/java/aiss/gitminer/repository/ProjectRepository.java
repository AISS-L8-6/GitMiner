package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import aiss.gitminer.model.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    /*
    public default Project create(Project project){
        Project newProject = new Project(
                UUID.randomUUID().toString(),
                project.getName(),
                project.getWebUrl(),
                project.getCommits(),
                project.getIssues());

        projects.add(newProject);
        return newProject;
    }


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

    public Project findOne(long id){
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

    public void update(Project updatedProject, long id) {
        Project existing = findOne(id);
        int i = projects.indexOf(existing);
        updatedProject.setId(existing.getId());
        projects.set(i, updatedProject);
    }

    public void delete(){projects.removeIf(project -> project.getId().equals(id)} */
}
