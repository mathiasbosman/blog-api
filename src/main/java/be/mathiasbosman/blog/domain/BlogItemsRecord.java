package be.mathiasbosman.blog.domain;

import be.mathiasbosman.blog.controller.BlogController;
import java.util.List;

/**
 * Record that holds multiple {@link BlogItemRecord}s. Used in conjunction with the controller.
 *
 * @see BlogController
 */
public record BlogItemsRecord(List<BlogItemRecord> itemsOnPage, int page, int amountPerPage,
                              long totalAmount) {

}
