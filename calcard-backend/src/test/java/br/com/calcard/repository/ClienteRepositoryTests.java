
package br.com.calcard.repository;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClienteRepositoryTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ClienteRepository clienteRepository;

	@Before
	public void deleteAllBeforeTests() throws Exception {
		clienteRepository.deleteAll();
	}

	@Test
	public void shouldCreateEntity() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cliente")
				.content("{\"nome\": \"Frodo\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("cliente/")));
	}

	@Test
	public void shouldDeleteEntity() throws Exception {

		final MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/cliente")
						.content("{ \"nome\": \"Bilbo\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(MockMvcRequestBuilders.delete(location))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

		mockMvc.perform(MockMvcRequestBuilders.get(location))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {

		final MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/cliente")
						.content("{\"nome\": \"Frodo\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(MockMvcRequestBuilders.patch(location)
				.content("{\"nome\": \"Bilbo Jr.\"}"))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

		mockMvc.perform(MockMvcRequestBuilders.get(location))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Bilbo Jr."));
	}

	@Test
	public void shouldQueryEntity() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cliente")
				.content("{ \"nome\": \"Frodo\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cliente/search/findByNome?q={nome}", "Frodo"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cliente[0].nome").value("Frodo"));
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {

		final MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/cliente")
						.content("{\"nome\": \"Frodo\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(MockMvcRequestBuilders.get(location))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Frodo"));
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cliente"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$._links.profile").exists());
	}

	@Test
	public void shouldUpdateEntity() throws Exception {
		final MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/cliente")
						.content("{\"nome\": \"Frodo\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(MockMvcRequestBuilders.put(location)
				.content("{\"nome\": \"Bilbo\"}"))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

		mockMvc.perform(MockMvcRequestBuilders.get(location))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Bilbo"));
	}
}
