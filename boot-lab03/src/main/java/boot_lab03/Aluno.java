package boot_lab03;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propriedades desconhecidas ao desserializar JSON
public class Aluno implements Serializable{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    @NotBlank(message = "O nome não pode estar vazio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
	private String nome;

    @JsonProperty("matricula")
    @NotNull(message = "A matrícula não pode ser nula")
    @Min(value = 1, message = "A matrícula deve ser um número positivo")
	private Integer matricula;
    
    @JsonProperty("email")
    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "Formato de e-mail inválido")
	private String email;
	
	public Aluno() {
		super();
	}
	
	public Aluno(Long id, String nome, Integer matricula, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getMatricula() {
		return matricula;
	}
	
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
    }

    @Override
    public String toString() {
    return "Aluno [id=" + id + ", nome=" + nome + ", matricula=" + matricula + ", email=" + email + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Aluno aluno = (Aluno) obj;
        return id.equals(aluno.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
