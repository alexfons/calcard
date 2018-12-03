package br.com.calcard.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@ApiModel(description = "All details about the Cliente. ")
@Data
@Builder
@AllArgsConstructor
@Entity
public class Cliente {

	@ApiModelProperty(notes = "The database generated Cliente ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String cpf;
	private String idade;
	private String sexo;
	private String estadoCivil;
	private String estado;
	private String dependentes;
	private String renda;
	private String resultado;
	private String limite;

	public Cliente() {
		super();
	}

	public Cliente(ClienteRequest request, ResultOption result) {
		super();
		nome = request.getNome();
		idade = request.getIdade();
		cpf = request.getCpf();
		sexo = request.getSexo();
		estadoCivil = request.getEstadoCivil();
		estado = request.getEstado();
		dependentes = request.getDependentes();
		renda = request.getRenda();
		resultado = result.getResultado();
		limite = result.getLimite();
	}
}
