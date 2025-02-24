package boot_lab03;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AlunoResourceAssembler implements RepresentationModelAssembler<Aluno, EntityModel<Aluno>> {

    public AlunoResourceAssembler() {
        super();
    }

    @Override
    public EntityModel<Aluno> toModel(Aluno aluno) {
        return EntityModel.of(aluno,
                linkTo(methodOn(AlunoRestController.class).get(aluno.getId())).withSelfRel(),
                linkTo(methodOn(AlunoRestController.class).getAll()).withRel("alunos"));
    }
}
