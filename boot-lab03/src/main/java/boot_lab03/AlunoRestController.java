package boot_lab03;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Gerenciamento de alunos")
public class AlunoRestController {

    private final List<Aluno> list = new ArrayList<>();
    private final AlunoResourceAssembler assembler;

    public AlunoRestController(AlunoResourceAssembler assembler) {
        this.assembler = assembler;
    }

	@PostConstruct
	public void init() {
		list.add(new Aluno(1l, "John", 11111, "john@john.com"));
		list.add(new Aluno(2l, "Steve", 22222, "steve.stevent@st.com"));
		list.add(new Aluno(3l, "Mary", 33333, "mary@robinson.com"));
		list.add(new Aluno(4l, "Kate", 44444,"kate@kate.com"));
		list.add(new Aluno(5l, "Diana", 55555,"diana@doll.com"));		
	}

    @Operation(summary = "Retorna a lista de alunos")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Aluno>>> getAll() {
        List<EntityModel<Aluno>> alunosModel = list.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(alunosModel,
                linkTo(methodOn(AlunoRestController.class).getAll()).withSelfRel()));
    }

    @Operation(summary = "Retorna as informações do aluno pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Aluno>> get(@PathVariable Long id) {
        Optional<Aluno> alunoOpt = list.stream()
                .filter(aluno -> aluno.getId().equals(id))
                .findFirst();

        return alunoOpt.map(aluno -> ResponseEntity.ok(assembler.toModel(aluno)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }    

    @Operation(summary = "Cria um novo aluno")
    @PostMapping
    public ResponseEntity<EntityModel<Aluno>> create(@RequestBody Aluno aluno) {
        list.add(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(aluno));
    }


    @Operation(summary = "Atualiza as informações do aluno pelo id")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Aluno>> update(@PathVariable Long id, @RequestBody Aluno aluno) {
        OptionalInt indexOpt = IntStream.range(0, list.size())
                .filter(i -> list.get(i).getId().equals(id))
                .findFirst();

        if (indexOpt.isPresent()) {
            list.set(indexOpt.getAsInt(), aluno);
            return ResponseEntity.ok(assembler.toModel(aluno));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Remove um determinado aluno pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        OptionalInt indexOpt = IntStream.range(0, list.size())
                .filter(i -> list.get(i).getId().equals(id))
                .findFirst();

        if (indexOpt.isPresent()) {
            list.remove(indexOpt.getAsInt());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }    

}
