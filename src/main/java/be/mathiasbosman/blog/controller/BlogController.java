package be.mathiasbosman.blog.controller;

import be.mathiasbosman.blog.domain.BlogItemRecord;
import be.mathiasbosman.blog.domain.BlogItemService;
import be.mathiasbosman.blog.domain.BlogItemsRecord;
import be.mathiasbosman.blog.domain.OAuth2UserService;
import be.mathiasbosman.blog.security.SecurityContext;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@Slf4j
@RestController
@RequiredArgsConstructor
public class BlogController {

  private final BlogItemService itemService;
  private final OAuth2UserService userService;

  @GetMapping("/rest/items/{page}/{amountPerPage}")
  public ResponseEntity<BlogItemsRecord> getBlogItems(@PathVariable int page,
      @PathVariable int amountPerPage) {
    List<BlogItemRecord> blogItems = itemService.getItems(false, page, amountPerPage).stream()
        .map(BlogItemRecord::fromEntity)
        .toList();
    long totalCount = itemService.countItems(false);
    return ResponseEntity.ok(new BlogItemsRecord(blogItems, page, amountPerPage, totalCount));
  }

  @GetMapping("/rest/item/{uuid}")
  public ResponseEntity<BlogItemRecord> getBlogItem(@PathVariable UUID uuid) {
    return itemService.getItem(uuid)
        .map(BlogItemRecord::fromEntity)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.noContent().build());
  }

  @PostMapping("/rest/item/create")
  public ResponseEntity<BlogItemRecord> postItem(@RequestBody BlogItemRecord itemRecord) {
    UUID userId = userService.getUuid(SecurityContext.getClientId(), SecurityContext.getUserId());
    log.trace("User {} is posting item {}", userId, itemRecord);
    return ResponseEntity.ok(
        BlogItemRecord.fromEntity(itemService.saveNewItem(itemRecord.title(), itemRecord.content(),
            userId)));
  }

  @PutMapping("/rest/item/update")
  public ResponseEntity<BlogItemRecord> updateItem(@RequestBody BlogItemRecord itemRecord) {
    return ResponseEntity.ok(BlogItemRecord.fromEntity(itemService.updateItem(
        itemRecord.uuid(), itemRecord.title(), itemRecord.content()
    )));
  }

  @DeleteMapping("/rest/item/{uuid}")
  public ResponseEntity<BlogItemRecord> deleteItem(@PathVariable UUID uuid) {
    return ResponseEntity.ok(BlogItemRecord.fromEntity(itemService.deleteItem(uuid)));
  }

}

