package eu.qwsome.sql.api;

/**
 * @author Lukáš Kvídera
 */
public interface Appendable {

  /**
   * Appends this entity to predefined builder.
   *
   * @param builder to be filled with this entity
   */
  void appendTo(StringBuilder builder);
}
