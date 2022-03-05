package be.mathiasbosman.blog.domain;

/**
 * Identifiable entity with a given identifier type.
 *
 * @param <K> the type of the entity's unique identifier
 */
public interface Identifiable<K> {

  /**
   * Returns the entity's identifier.
   *
   * @return the unique identifier
   */
  K getId();
}
