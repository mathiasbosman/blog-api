package be.mathiasbosman.blog.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.mathiasbosman.blog.domain.BlogItem;
import be.mathiasbosman.blog.domain.BlogItemRecord;
import be.mathiasbosman.blog.domain.BlogItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


class BlogControllerTest extends AbstractControllerTest {

  @MockBean
  private BlogItemRepository repository;

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void getBlogItems() throws Exception {
    when(repository.findAllByDeleted(anyBoolean(), any())).thenReturn(List.of(
        new BlogItem("foo", "bar", false),
        new BlogItem("bar", "foo", false)
    ));
    when(repository.countAllByDeleted(false)).thenReturn(2L);

    mvc.perform(get("/rest/items/10/10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.itemsOnPage[0].title", equalTo("foo")))
        .andExpect(jsonPath("$.itemsOnPage[1].content", equalTo("foo")))
        .andExpect(jsonPath("$.itemsOnPage", hasSize(2)))
        .andExpect(jsonPath("$.page", equalTo(10)))
        .andExpect(jsonPath("$.amountPerPage", equalTo(10)))
        .andExpect(jsonPath("$.totalAmount", equalTo(2)));
  }

  @Test
  void getBlogItem() throws Exception {
    when(repository.findById(any())).thenReturn(Optional.of(new BlogItem("foo", "bar", false)));

    mvc.perform(get("/rest/item/" + UUID.randomUUID()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(hasKey("created")))
        .andExpect(jsonPath("$").value(hasKey("updated")))
        .andExpect(jsonPath("$.title", equalTo("foo")))
        .andExpect(jsonPath("$.content", equalTo("bar")))
        .andExpect(jsonPath("$.isDeleted", equalTo(false)));
  }

  @Test
  void postItem() throws Exception {

    when(repository.save(any())).thenReturn(new BlogItem("foo", "bar", false));

    BlogItemRecord itemRecord = new BlogItemRecord("foo", "bar");
    mvc.perform(post("/rest/item/create")
            .content(mapper.writeValueAsString(itemRecord))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void updateItem() throws Exception {
    when(repository.findById(any())).thenReturn(Optional.of(new BlogItem("foo", "bar", false)));
    when(repository.save(any())).thenReturn(new BlogItem("foo", "bar", false));

    BlogItemRecord itemRecord = new BlogItemRecord("foo", "bar");
    mvc.perform(put("/rest/item/update")
            .content(mapper.writeValueAsString(itemRecord))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void deleteItem() throws Exception {

    when(repository.findById(any())).thenReturn(Optional.of(new BlogItem("foo", "bar", false)));
    when(repository.save(any())).thenReturn(new BlogItem("foo", "bar", false));

    UUID uuid = UUID.randomUUID();
    mvc.perform(delete("/rest/item/" + uuid))
        .andExpect(status().isOk());
  }
}
