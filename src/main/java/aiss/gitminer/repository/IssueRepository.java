package aiss.gitminer.repository;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    @Query("select i from Issue i where i.author.id = :authorId")
    public List<Issue> findIssuesByAuthorId(String authorId);

    @Query("select i from Issue i where i.state = :state")
    public List<Issue> findIssuesByState(String state);

    @Modifying
    @Query("delete from User u where u.id = :id")
    public void deleteUser(String id);

    @Modifying
    @Query("delete from Comment c where c.id = :id")
    public void deleteComment(String id);
}
