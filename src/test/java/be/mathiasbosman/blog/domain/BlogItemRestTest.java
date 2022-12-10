package be.mathiasbosman.blog.domain;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

class BlogItemRestTest extends AbstractMvcTest<BlogItem> {

  private static final String jsonItemsPath = "$._embedded.items";

  @Override
  String getCollectionName() {
    return "items";
  }

  @Override
  BlogItem createRandomEntity() {
    BlogUser user = entityManager.persist(BlogUserMother.randomBlogUser());
    return entityManager.persist(BlogItemMother.randomBlogItem(user));
  }

  @Test
  void getItems_singleResultAfterUpdate() throws Exception {
    BlogItem item = createRandomEntity();
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
  void getItem_allFields() throws Exception {
    BlogItem item = entityManager.persist(createRandomEntity());

    mvc.perform(get("/items/" + item.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.created").exists())
        .andExpect(jsonPath("$.updated").exists())
        .andExpect(jsonPath("$.title").exists())
        .andExpect(jsonPath("$.excerpt").exists())
        .andExpect(jsonPath("$.content").exists())
        .andExpect(jsonPath("$.deleted").exists())
        .andExpect(jsonPath("$._links.poster").exists());
  }

  @Test
  void getItem_nullableFields() throws Exception {
    BlogItem item = entityManager.persist(createRandomEntity().toBuilder().excerpt(null).build());

    mvc.perform(get("/items/" + item.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.excerpt").isEmpty());
  }

}
