package be.mathiasbosman.blog.controller;

import be.mathiasbosman.blog.domain.BlogItemRecord;
import be.mathiasbosman.blog.domain.BlogItemService;
import be.mathiasbosman.blog.domain.BlogItemsRecord;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic controller for the blog.
 */
@RestController
@RequiredArgsConstructor
public class BlogController {

  private final BlogItemService service;

  @GetMapping("/rest/items/{page}/{amountPerPage}")
  public BlogItemsRecord getBlogItems(@PathVariable int page, @PathVariable int amountPerPage) {
    List<BlogItemRecord> blogItems = service.getItems(false, page, amountPerPage)
        .stream().map(BlogItemRecord::fromEntity).toList();
    long totalCount = service.countItems(false);
    return new BlogItemsRecord(blogItems, page, amountPerPage, totalCount);
  }

  @GetMapping("/rest/item/{uuid}")
  public BlogItemRecord getBlogItem(@PathVariable @NonNull UUID uuid) {
    return BlogItemRecord.fromEntity(service.getItem(uuid));
  }

  @PostMapping("/rest/item/create")
  public BlogItemRecord postItem(@RequestBody @NonNull BlogItemRecord itemRecord) {
    return BlogItemRecord.fromEntity(
        service.saveNewItem(itemRecord.title(), itemRecord.content(), null));
  }

  @PutMapping("/rest/item/update")
  public BlogItemRecord updateItem(@RequestBody @NonNull BlogItemRecord itemRecord) {
    return BlogItemRecord.fromEntity(service.updateItem(
        itemRecord.uuid(), itemRecord.title(), itemRecord.content()
    ));
  }

  @DeleteMapping("/rest/item/{uuid}")
  public BlogItemRecord deleteItem(@PathVariable @NonNull UUID uuid) {
    return BlogItemRecord.fromEntity(service.deleteItem(uuid));
  }

}

