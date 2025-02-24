package boot_lab03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import boot_lab03.Aluno;
import boot_lab03.AlunoRestController;

@WebMvcTest(AlunoRestController.class)
public class AlunoRestControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper jsonParser;

    @Mock
    private AlunoResourceAssembler assembler; // Mock para evitar erro

    @InjectMocks
    private AlunoRestController alunoRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(assembler.toModel(any(Aluno.class))).thenAnswer(invocation -> {
            Aluno aluno = invocation.getArgument(0);
            return new AlunoResource(aluno);
        });
    }	

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
	public void testGet() throws Exception {
		mockMvc.perform(get("/alunos/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.nome", equalTo("John")));
	}

	@Test
	public void testCreate() throws Exception {
		Aluno aluno = new Aluno(6l, "Rodrigo", 66666, "rodrigo@email.com");
		mockMvc.perform(post("/alunos").contentType(MediaType.APPLICATION_JSON)
				.content(jsonParser.writeValueAsString(aluno)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", equalTo(6)))
				.andExpect(jsonPath("$.nome", equalTo("Rodrigo")));
	}
}
