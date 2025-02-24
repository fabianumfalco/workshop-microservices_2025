package boot_lab05;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import boot_lab05.Aluno;
import boot_lab05.AlunoRepository;

@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlunoRepository repository;

    @Test
    public void testSaveAluno() {
        Aluno aluno = Aluno.builder()
                .nome("Rodrigo").matricula(67676)
                .email("rodrigo@email.com").dataNascimento(new Date()).build();

        aluno = repository.save(aluno);

        assertNotNull(aluno);
        assertNotNull(aluno.getId()); // Verifica se ID foi gerado
    }

    @Test
    public void testDeleteAluno() {
        Aluno aluno = entityManager.persistFlushFind(
                Aluno.builder()
                        .nome("Rodrigo").matricula(67676)
                        .email("rodrigo@email.com")
                        .dataNascimento(new Date())
                        .build());

        repository.delete(aluno);

        Aluno alunoExcluido = repository.findById(aluno.getId()).orElse(null);

        assertNull(alunoExcluido); // Verifica que o aluno foi removido
    }

    @Test
    public void testFindByNome() {
        entityManager.persistFlushFind(
                Aluno.builder()
                        .nome("Rodrigo").matricula(67676)
                        .email("rodrigo@email.com")
                        .dataNascimento(new Date())
                        .build());

        List<Aluno> alunos = repository.findByNomeContaining("Rodrigo");

        assertNotNull(alunos);
        assertFalse(alunos.isEmpty());
        assertEquals("Rodrigo", alunos.get(0).getNome()); // Importação correta
    }

}
