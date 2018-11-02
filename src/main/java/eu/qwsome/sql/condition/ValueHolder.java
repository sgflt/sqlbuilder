package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public interface ValueHolder {

  /**
   * Returnc column name or question martk as a wildcard.
   *
   * @return string representation of value in SQL
   */
  String get();
}
