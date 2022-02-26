package be.mathiasbosman.blog.domain;

import org.slf4j.event.Level;

/**
 * Exception used for validations.
 */
public class BlogException extends RuntimeException {

  private final Level logLevel;

  public BlogException(Level logLevel, String message, Object... args) {
    super(String.format(message, args));
    this.logLevel = logLevel;
  }

  public Level getLogLevel() {
    return logLevel;
  }
}
