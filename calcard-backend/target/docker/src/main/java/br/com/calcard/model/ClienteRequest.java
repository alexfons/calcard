package br.com.calcard.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "All details about the Cliente. ")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
	private String nome;
	private String cpf;
	private String idade;
	private String sexo;
	private String estadoCivil;
	private String estado;
	private String dependentes;
	private String renda;
}
