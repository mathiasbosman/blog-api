package be.mathiasbosman.blog.domain;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.mathiasbosman.blog.AbstractSpringBootTest;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public abstract class AbstractMvcTest<E extends AbstractEntity> extends AbstractSpringBootTest {

  @Autowired
  protected MockMvc mvc;

  abstract String getCollectionName();

  abstract E createRandomEntity();

  String getEndpoint() {
    return "/" + getCollectionName();
  }

  @Test
  void getCollection_noResults() throws Exception {
    mvc.perform(get(getEndpoint()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded." + getCollectionName(), hasSize(0)));
  }

  @Test
  void getCollection_singleResult() throws Exception {
    createRandomEntity();

    mvc.perform(get(getEndpoint()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded." + getCollectionName(), hasSize(1)))
        .andExpect(jsonPath("$._embedded." + getCollectionName() + ".[0]").exists())
        .andExpect(jsonPath("$._links").exists())
        .andExpect(jsonPath("$.page").exists())
        .andExpect(jsonPath("$.page.totalPages", is(1)));
  }

  @Test
  void getCollection_multipleResults() throws Exception {
    int amount = 100;
    IntStream.range(0, amount)
        .forEach(i -> createRandomEntity());

    mvc.perform(get(getEndpoint() + "?size=20"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded." + getCollectionName(), hasSize(20)))
        .andExpect(jsonPath("$.page.totalElements", is(amount)));
  }
}
