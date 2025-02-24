package boot_lab05;

import java.util.List;
import java.util.Optional;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Gerenciamento de alunos")
public class AlunoRestController {

    @Autowired
	AlunoRepository repository;
	
	AlunoResourceAssembler assembler = new AlunoResourceAssembler();

    public AlunoRestController(AlunoResourceAssembler assembler) {
        this.assembler = assembler;
    }

    @PostConstruct
    public void init() {
        if (repository.count() == 0) { // 🔹 Só adiciona os alunos se o banco estiver vazio
            repository.save(new Aluno(null, "John", 11111, "john@john.com", new Date()));
            repository.save(new Aluno(null, "Steve", 22222, "steve.stevent@st.com", new Date()));
            repository.save(new Aluno(null, "Mary", 33333, "mary@robinson.com", new Date()));
            repository.save(new Aluno(null, "Kate", 44444,"kate@kate.com", new Date()));
            repository.save(new Aluno(null, "Diana", 55555,"diana@doll.com", new Date()));		
        }
    }

    @Operation(summary = "Retorna a lista de alunos")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Aluno>>> getAll() {
        List<Aluno> alunos = repository.findAll();
        return ResponseEntity.ok(assembler.toCollectionModel(alunos));
    }

    @Operation(summary = "Retorna as informações do aluno pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Aluno>> get(@PathVariable Long id) {
        Optional<Aluno> alunoOpt = repository.findById(id);
        return alunoOpt.map(aluno -> ResponseEntity.ok(assembler.toModel(aluno)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    } 

    @Operation(summary = "Cria um novo aluno")
	@PostMapping
    public ResponseEntity<EntityModel<Aluno>> create(@RequestBody Aluno aluno) {
        Aluno novoAluno = repository.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novoAluno));
    }  

    @Operation(summary = "Atualiza as informações do aluno pelo id")
	@PutMapping("/{id}")
    public ResponseEntity<EntityModel<Aluno>> update(@PathVariable Long id, @RequestBody Aluno aluno) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        aluno.setId(id);
        Aluno alunoAtualizado = repository.save(aluno);
        return ResponseEntity.ok(assembler.toModel(alunoAtualizado));
    }

    @Operation(summary = "Remove um determinado aluno pelo id")
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca alunos pelo nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<CollectionModel<EntityModel<Aluno>>> findByNome(@PathVariable String nome) {
        List<Aluno> alunos = repository.findByNomeContaining(nome);
        return ResponseEntity.ok(assembler.toCollectionModel(alunos));
    }

    @Operation(summary = "Retorna alunos com aniversário no mês corrente")
    @GetMapping("/nascimento/mes/corrente")
    public ResponseEntity<CollectionModel<EntityModel<Aluno>>> findByDataNascimentoAtMesCorrente() {
        int mesCorrente = Calendar.getInstance().get(Calendar.MONTH) + 1;
        List<Aluno> alunos = repository.findByDataNascimentoAtMesCorrente(mesCorrente);
        return ResponseEntity.ok(assembler.toCollectionModel(alunos));
    }    


}
