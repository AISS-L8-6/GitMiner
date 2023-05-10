package aiss.gitminer.repository;

import aiss.gitminer.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    @Query("select i from Issue i where i.AuthorId = :AuthorId")
    public List<Issue> findIssuesByAuthorId(String AuthorId);
}
