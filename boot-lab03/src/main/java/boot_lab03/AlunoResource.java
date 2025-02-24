package boot_lab03;

import org.springframework.hateoas.Link;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import org.springframework.hateoas.EntityModel;

@XmlRootElement
@XmlSeeAlso({Aluno.class})
public class AlunoResource extends EntityModel<Aluno> {

    public AlunoResource() {
        this(new Aluno());
    }

    public AlunoResource(Aluno aluno, Link... links) {
        super(aluno); // Chama o construtor da classe pai (EntityModel) com o objeto Aluno
        add(links);   // Adiciona os links ao EntityModel
    }
}
