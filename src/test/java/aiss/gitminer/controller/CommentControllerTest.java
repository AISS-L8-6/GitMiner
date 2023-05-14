package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CommentControllerTest {

/*
    ################

    Intentamos realizar los test de JUnit pero despues de mucho probar no fuimos capaces de realizarlos
    Aquí se muestra una parte de como intentamos realizarlos
    Suponemos que necesitamos el uso de la tecnología de Mockito de la cual no tenemos conocimiento

    ################
 */


/*
    @Autowired
    CommentController controller;

    @Autowired
    CommentRepository repository;

    public CommentControllerTest(CommentRepository repository){
        this.repository = repository;
    }

    User author1 = new User();
    User author2 = new User();

    Comment save1 = repository.save(new Comment("1234567890", "Body 1", author1, "Ayer", "Hoy"));
    Comment save2 = repository.save(new Comment("0987654321", "Body 2", author2, "Ayer", "Hoy"));

    @Test
    void findAll() {
        List<Comment> comments = controller.findAll();
        System.out.println(comments);
        assertTrue(!comments.isEmpty(), "No hay comentarios en la BD");
        assertTrue(comments.size() == 2, "Número de  comentarios incorrecto");
    }

    @Test
    void findOne() {
        Comment comment = controller.findOne("1234567890");
        assertTrue(!comment.equals(null), "Comentario no encontrado");
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    */
}