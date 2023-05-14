package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CommitRepository extends JpaRepository<Commit, String> {

    @Query("select c from Commit c where c.authorEmail = :email")
    public List<Commit> findByEmail(String email);

}
