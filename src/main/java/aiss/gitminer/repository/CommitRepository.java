package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommitRepository extends JpaRepository<Commit, String> {

    @Query("select c from Commit where c.email = :email")
    public List<Commit> findByEmail(String email);
/*
    List<Commit> commits = new ArrayList<>();
    public CommitRepository(){
        commits.add(new Commit(
                UUID.randomUUID().toString(),
                "title",
                "hola",
                "ejemplo@mcejemplo.com",
                LocalDateTime.now().toString()
        ));
    }

    public List<Commit> findAll() {
        return commits;
    }

    public Commit findOne(String id){
        return commits.stream()
                .filter(Commit -> Commit.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Commit create(Commit commit){
        Commit newCommit = new Commit(
                UUID.randomUUID().toString(),
                commit.getTitle(),
                commit.getMessage(),
                commit.getAuthorEmail(),
                commit.getAuthoredDate()
        );
        commits.add(newCommit);
        return newCommit;
    }


 */
}
