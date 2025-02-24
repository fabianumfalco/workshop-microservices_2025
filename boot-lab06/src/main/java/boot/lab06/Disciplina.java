package boot.lab06;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String nome;
	Integer cargaHoraria;
	Date dataInicio;
	
}
