
package br.com.calcard.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClienteNaiveBayesTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldGetAprovadoEntre100e500() throws Exception {

		final StringBuilder content = new StringBuilder("{")
				.append("\"nome\": \"Frodo\",")
				.append("\"cpf\": \"12345678901\",")
				.append("\"idade\": \"17\",")
				.append("\"sexo\": \"f\",")
				.append("\"estadoCivil\": \"solteiro\",")
				.append("\"estado\": \"sp\",")
				.append("\"dependentes\": \"0\",")
				.append("\"renda\": \"1000\"")
				.append("}");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cliente/avaliaProposta")
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.resultado").value("aprovado"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.limite").value("entre 100 - 500")).andReturn();
	}

	@Test
	public void shouldGetAprovadoEntre500e1000() throws Exception {

		final StringBuilder content = new StringBuilder("{")
				.append("\"nome\": \"Frodo\",")
				.append("\"cpf\": \"12345678901\",")
				.append("\"idade\": \"28\",")
				.append("\"sexo\": \"m\",")
				.append("\"estadoCivil\": \"solteiro\",")
				.append("\"estado\": \"sc\",")
				.append("\"dependentes\": \"0\",")
				.append("\"renda\": \"2500\"")
				.append("}");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cliente/avaliaProposta")
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.resultado").value("aprovado"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.limite").value("entre 500 - 1000")).andReturn();
	}

	@Test
	public void shouldGetNegadoReprovado() throws Exception {

		final StringBuilder content = new StringBuilder("{")
				.append("\"nome\": \"Frodo\",")
				.append("\"cpf\": \"12345678901\",")
				.append("\"idade\": \"56\",")
				.append("\"sexo\": \"m\",")
				.append("\"estadoCivil\": \"divorciado\",")
				.append("\"estado\": \"rj\",")
				.append("\"dependentes\": \"2\",")
				.append("\"renda\": \"2000\"")
				.append("}");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cliente/avaliaProposta")
				.content(content.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.resultado").value("negado"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.limite").value("reprovado")).andReturn();
	}
}
