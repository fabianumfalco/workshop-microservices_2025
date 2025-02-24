package boot_lab04;

import org.springframework.hateoas.Link;

import org.springframework.hateoas.EntityModel;

public class AlunoResource extends EntityModel<Aluno> {

    public AlunoResource() {
        this(new Aluno());
    }

    public AlunoResource(Aluno aluno, Link... links) {
        super(aluno); // Chama o construtor da classe pai (EntityModel) com o objeto Aluno
        add(links);   // Adiciona os links ao EntityModel
    }
}
