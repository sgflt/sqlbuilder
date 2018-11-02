package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public interface ValueHolder {

  /**
   * Returns column name or question martk as a wildcard.
   *
   * @return string representation of value in SQL
   */
  String getSql();

  /**
   * Returns valou that can be bount into wildcard, or null for column name.
   *
   * @return value to be bound in prepared statement
   */
  Object getValue();
}
