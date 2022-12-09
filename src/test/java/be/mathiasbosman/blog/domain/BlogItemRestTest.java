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
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
class BlogItemRestTest extends AbstractSpringBootTest {

  private static final String jsonItemsPath = "$._embedded.items";
  @Autowired
  private MockMvc mvc;
  @Autowired
  private TestEntityManager entityManager;

  @Test
  void getItems_noResults() throws Exception {
    mvc.perform(get("/items"))
        .andExpect(status().isOk())
        .andExpect(jsonPath(jsonItemsPath, hasSize(0)));
  }

  @Test
  void getItems_singleResult() throws Exception {
    entityManager.persist(BlogItemMother.randomBlogItem());

    mvc.perform(get("/items"))
        .andExpect(status().isOk())
        .andExpect(jsonPath(jsonItemsPath, hasSize(1)))
        .andExpect(jsonPath(jsonItemsPath + ".[0]").exists())
        .andExpect(jsonPath("$._links").exists())
        .andExpect(jsonPath("$.page").exists())
        .andExpect(jsonPath("$.page.totalPages", is(1)));
  }

  @Test
  void getItems_singleResultAfterUpdate() throws Exception {
    BlogItem item = entityManager.persist(BlogItemMother.randomBlogItem());
    item.setContent("new content");
    entityManager.persist(item);

    mvc.perform(get("/items"))
        .andExpect(status().isOk())
        .andExpect(jsonPath(jsonItemsPath, hasSize(1)))
        .andExpect(jsonPath(jsonItemsPath + ".[0].content", is("new content")))
        .andExpect(jsonPath("$.page.totalPages", is(1)))
        .andExpect(jsonPath("$.page.totalElements", is(1)));
  }

  @Test
  void getItems_multipleResults() throws Exception {
    IntStream.range(0, 100)
        .forEach(i -> entityManager.persist(BlogItemMother.randomBlogItem()));

    mvc.perform(get("/items?size=20"))
        .andExpect(status().isOk())
        .andExpect(jsonPath(jsonItemsPath, hasSize(20)))
        .andExpect(jsonPath("$.page.totalElements", is(100)));
  }

}
