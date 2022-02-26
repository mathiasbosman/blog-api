package be.mathiasbosman.blog.domain;

import java.util.List;

public record BlogItemsRecord(List<BlogItemRecord> itemsOnPage, int page, int amountPerPage, long totalAmount) {

}
