package boot_lab05;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AlunoRestController.class)
@ExtendWith(SpringExtension.class)
public class AlunoRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlunoRepository repository;

    @Mock
    private AlunoResourceAssembler assembler;

    @InjectMocks
    private AlunoRestController alunoRestController;

    private final ObjectMapper jsonParser = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alunoRestController).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Aluno> alunos = List.of(
            new Aluno(1L, "John", 11111, "john@john.com", new Date()),
            new Aluno(2L, "Steve", 22222, "steve@st.com", new Date())
        );

        when(repository.findAll()).thenReturn(alunos);

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGet() throws Exception {
        Aluno aluno = new Aluno(1L, "John", 11111, "john@john.com", new Date());
        when(repository.findById(1L)).thenReturn(Optional.of(aluno));

        mockMvc.perform(get("/alunos/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.nome", equalTo("John")));
    }

    @Test
    public void testCreate() throws Exception {
        Aluno aluno = new Aluno(6L, "Rodrigo", 66666, "rodrigo@email.com", new Date());

        when(repository.save(any(Aluno.class))).thenReturn(aluno);

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParser.writeValueAsString(aluno)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(6)))
                .andExpect(jsonPath("$.nome", equalTo("Rodrigo")));
    }

    @Configuration
    @ComponentScan(basePackages = "boot_lab05")
    static class TestConfig {
        // Classe de configuração para corrigir erro de contexto
    }
}