package nl.lunatech.emile.imdb;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImdbApplicationTests {

    @Autowired
    private MockMvc mockMvc;

	@Test
	public void getOneMovie() throws Exception {
		
		mockMvc.perform(get("/movie/findByPrimaryTitle?title=The Lotus Dancer"))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$.primaryTitle", is("The Lotus Dancer")));
	}
	@Test
	public void getMoreMovies() throws Exception {
		
		mockMvc.perform(get("/movie/findByTitleContaining?title=lotus dancer"))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].primaryTitle", is("The Lotus Dancer")));
	}
	@Test
	public void getOnePersonByNconst() throws Exception {
		mockMvc.perform(get("/person/findByNconst?nconst=nm0000429"))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$.primaryName", is("Melanie Griffith")));
	}
	@Test
	public void getOnePersonByPrimaryName() throws Exception {
		
		mockMvc.perform(get("/person/findByPrimaryName?name=Melanie Griffith"))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$.primaryName", is("Melanie Griffith")));
	}
	@Test
	public void getMorePersons() throws Exception {
		
		mockMvc.perform(get("/person/findByNameContaining?name=elanie Griffith"))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].primaryName", is("Melanie Griffith")));
	}
}
